# Kafka_streaming_arquitecture

Se trata de un proyecto creado con motivos académicos para FarmIA (empresa ficticia). Esta compañía requería de una arquitectura streaming para integrar sus fuentes datos en tiempo real, entre las que se encuentran **sensores IoT** y **datos de transacciones** de su plataforma de ventas en línea. De esta manera, ahora pueden procesarlos para generar insights en tiempo real. 

Para el propóstio del proyecto se han utilizado las herramientas **Kafka**, **Kafka Connect** y **Kafka Streams**.

## 1. Integración de MySQL con Kafka Connect

Para la integración de MySQL con Kafka Connect se construyó el conector `source-mysql-transactions.json`, tomando como referencia la documentación del **JDBC Source Connector**. Los ajustes más relevantes son los siguientes:

1. Se configuró el modo incremental **timestamp** para la columna `timestamp`, lo que permite trackear los registros que han sido procesados.
2. Se creó la **key** de los mensajes a partir de **SMTs**, extrayendo el valor del campo `transaction_id`.
3. Se convirtió o **casteó** el campo `price` a tipo **float**. En la base de datos, este campo se encuentra correctamente en tipo `float`, pero por defecto, Connect usa su propio tipo lógico `decimal`, que es serializado a `bytes` en **Avro**. Por tanto, es necesario realizar la transformación a `float` para que este campo sea legible.

Los mensajes fueron configurados para publicarse en el topic **sales-transactions**.

El conector creado es el siguiente:

```json
{
  "name": "source-mysql-transactions",
  "config": {
    "connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector",
    "connection.url": "jdbc:mysql://mysql:3306/db?user=user&password=password&useSSL=false",
    "topic.prefix": "sales-",
    "poll.interval.ms" : 10000,
    "mode":"timestamp",
    "timestamp.column.name":"timestamp",
    "transforms":"createKey,extractInt,DecimalToFloat",
    "table.whitelist":"transactions",
    "transforms.createKey.type":"org.apache.kafka.connect.transforms.ValueToKey",
    "transforms.createKey.fields":"transaction_id",
    "transforms.extractInt.type":"org.apache.kafka.connect.transforms.ExtractField$Key",
    "transforms.extractInt.field":"transaction_id",
    "transforms.DecimalToFloat.type": "org.apache.kafka.connect.transforms.Cast$Value",
    "transforms.DecimalToFloat.spec": "price:float32",
    "tasks.max": "1"
  }
}
```

## 2. Generación de Datos Sintéticos con Kafka Connect

Para simular las lecturas de los sensores **IoT** se creó un esquema **avro** (`sensor-telemetry.avsc`) siguiendo las indicaciones del enunciado en cuanto a campos y tipos de datos.
Posteriormente, se configuró el conector **Datagen** `source-datagen-sensor-telemetry.json`.

Se asignó como **key** el campo `sensor_id`, y los mensajes fueron configurados para publicarse en el topic **sensor-telemetry**.

El conector creado es el siguiente:

```json
{
  "name": "source-datagen-sensor-telemetry",
  "config": {
    "connector.class": "io.confluent.kafka.connect.datagen.DatagenConnector",
    "kafka.topic": "sensor-telemetry",
    "schema.filename": "/home/appuser/sensor-telemetry.avsc",
    "schema.keyfield": "sensor_id",
    "max.interval": 1000,
    "iterations": 10000000,
    "tasks.max": "1"
  }
}
```

## 3. Procesamiento en Tiempo Real con Kafka Streams de Datos de Sensores

Se escribió una aplicación de **Kafka Streams** para leer los mensajes del topic **sensor-telemetry** y detectar condiciones climáticas anómalas (**temperatura > 35 °C o humedad < 20 %**).

Para ello, previamente fue necesario crear un **serdes específico de avro** que fuera capaz de deserializar mensajes del topic **sensor-telemetry**. El `SpecificAvroSerde` fue creado a partir de la clase `SensorTelemetry`.
Esta clase fue generada con **avro-maven-plugin**, que permite utilizar esquemas **avro** (`.avsc`) para generar clases de java.

El plugin se encuentra incluido en la sección `<build> <plugins>` del archivo `pom.xml`.

La configuración del **avro-maven-plugin** fue la siguiente:

```xml
<plugin>
    <groupId>org.apache.avro</groupId>
    <artifactId>avro-maven-plugin</artifactId>
    <version>1.10.2</version>
    <executions>
        <execution>
            <phase>generate-sources</phase>
            <goals>
                <goal>schema</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <sourceDirectory>${project.basedir}/datagen</sourceDirectory>
        <outputDirectory>${project.basedir}/src/main/java</outputDirectory>
    </configuration>
</plugin>
```

Una vez creado el `sensorTelemetrySerde`, se procedió a crear el stream para crear las alertas. 
El stream lee del topic **sensor-telemetry** y produce en **sensor-alerts**.

El código principal del procesamiento de la aplicación es el siguiente:

```java
StreamsBuilder builder = new StreamsBuilder();
KStream<String, SensorTelemetry> alertStream = builder.stream(inputTopic, Consumed.with(Serdes.String(), sensorTelemetrySerde));

alertStream
    .filter((key, value) -> value.getTemperature() >= 30 || value.getHumidity() < 20)
    .mapValues(value -> {
        String alertType;
        String details;

        if (value.getTemperature() > 35) {
            alertType = "HIGH_TEMPERATURE";
            details = "Temperature exceeded 35°C";
        } else {
            alertType = "LOW_HUMIDITY";
            details = "Humidity dropped below 20%";
        }

        return String.format(
            "{\"sensor_id\": \"%s\", \"alert_type\": \"%s\", \"timestamp\": %d, \"details\": \"%s\"}",
            value.getSensorId(), alertType, System.currentTimeMillis(), details
        );
    })
    .peek((key, value) -> System.out.println("Outgoing record - key " + key + " value " + value))
    .to(outputTopic, Produced.with(Serdes.String(), Serdes.String()));
```

Cabe destacar el uso de un `Serde.String()` en el value de los mensajes producidos. 

Otra opción posible, como se comprobará en el siguiente apartado, es producir los mensajes con un **serde específico**
creado a partir de una clase customizada que represente al value. Sin embargo, para el objetivo de la tarea, es suficiente y funcional emitir los mensajes en formato de texto.

## 4. Procesamiento en Tiempo Real con Kafka Streams y Creación del Resumen de Ventas por Categoría

Se escribió una aplicación de **Kafka Streams** para leer los mensajes del topic **sales-transactions** y producir un resumen con los datos de ventas e ingresos de cada categoría de producto durante cada minuto.

Aquí existe una dificultad añadida con respecto al apartado anterior. Si utilizamos, de manera análoga al previo apartado, un serde creado a partir de la clase `SalesTransaction` para consumir los mensajes del topic, la aplicación devolverá un error. Esto se debe a que el esquema original de `transactions.avsc` no es el mismo que el de los mensajes del topic `sales-transactions`, ya que se creó un nuevo campo `timestamp` en la base de datos MySQL.

Para solucionar este problema, existen varias opciones:

1. Utilizar un `genericAvroSerde` para consumir los datos desde Kafka Streams, permitiendo flexibilidad en el consumo del esquema.
2. Crear manualmente el esquema Avro actualizado y generar la clase Java correspondiente para construir el **serde específico**.
3. Utilizar el `kafka-schema-registry-maven-plugin` para descargar del **Schema Registry** el esquema del topic, en lugar de definirlo manualmente.

En este caso, se optó por utilizar un **serde genérico** por comodidad, aunque en un entorno de producción sería preferible descargar el esquema desde el **Schema Registry**.

Adicionalmente, se creó un esquema Avro (`sales-summary.avsc`) para generar la clase `SalesSummary` y su **serde** correspondiente (`salesSummarySerde`). Este serde se utilizó tanto para producir los valores de los mensajes en el topic de salida como para materializar los datos en la fase de agregación.

El código principal del procesamiento de la aplicación es el siguiente:

```java
builder.stream(inputTopic, Consumed.with(Serdes.String(), genericSerde))
    .groupBy((key, value) -> value.get("category").toString(), Grouped.with(Serdes.String(), genericSerde))
    .windowedBy(TimeWindows.of(Duration.ofMinutes(1)))
    .aggregate(
        () -> new SalesSummary("", 0, (float) 0.0, Instant.EPOCH, Instant.EPOCH),
        (key, transaction, summary) -> {
            int quantity = (int) transaction.get("quantity");
            float price = (float) transaction.get("price");

            return new SalesSummary(
                key,
                summary.getTotalQuantity() + quantity,
                summary.getTotalRevenue() + (quantity * price),
                summary.getWindowStart(),
                summary.getWindowEnd()
            );
        },
        Materialized.with(Serdes.String(), salesSummarySerde)
    )
    .toStream()
    .map((wk, summary) -> {
        Instant start = Instant.ofEpochMilli(wk.window().start());
        Instant end = Instant.ofEpochMilli(wk.window().end());
        summary.setWindowStart(start);
        summary.setWindowEnd(end);
        return KeyValue.pair(wk.key(), summary);
    })
    .peek((key, value) -> System.out.println("Outgoing record - key " + key + " value " + value))
    .to(outputTopic, Produced.with(Serdes.String(), salesSummarySerde));
```
