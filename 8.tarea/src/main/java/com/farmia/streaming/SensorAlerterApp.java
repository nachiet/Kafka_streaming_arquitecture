package com.farmia.streaming;

import java.io.IOException;

import com.farmia.iot.SensorTelemetry;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;


public class SensorAlerterApp {

    public static void main(String[] args) throws IOException {

        // Cargamos la configuración
        Properties props = new Properties();
        String config = "stream.properties";
        try (InputStream fis = SensorAlerterApp.class.getClassLoader().getResourceAsStream(config)) {
            props.load(fis);
        }
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "sensor-alerter-app");

        final String inputTopic = "sensor-telemetry";
        final String outputTopic = "sensor-alerts";

        //Creamos un Serde de tipo Avro ya que el productor produce <String,TemperatureTelemetry>
        final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url", "http://localhost:8081");
        Serde<SensorTelemetry> sensorTelemetrySerde = new SpecificAvroSerde();
        sensorTelemetrySerde.configure(serdeConfig, false);

        //Creamos el KStream mediante el builder
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, SensorTelemetry> alertStream = builder.stream(inputTopic, Consumed.with(Serdes.String(), sensorTelemetrySerde));

        //Filtramos los eventos con temperatura >= 35 grados o humedad menor al 20%
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

                    // Construir mensaje JSON de alerta
                    return String.format(
                            "{\"sensor_id\": \"%s\", \"alert_type\": \"%s\", \"timestamp\": %d, \"details\": \"%s\"}",
                            value.getSensorId(), alertType, System.currentTimeMillis(), details
                    );
                })
                .peek((key, value) -> System.out.println("Outgoing record - key " + key + " value " + value))
                .to(outputTopic, Produced.with(Serdes.String(), Serdes.String()));

        // Iniciar Kafka Streams
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        // Manejar cierre del programa
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
