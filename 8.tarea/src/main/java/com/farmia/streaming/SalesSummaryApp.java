package com.farmia.streaming;

import com.farmia.sales.SalesSummary;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class SalesSummaryApp {

    public static void main(String[] args) throws IOException {

        // Cargamos la configuración
        Properties props = new Properties();
        String config = "stream.properties";
        try (InputStream fis = SalesSummaryApp.class.getClassLoader().getResourceAsStream(config)) {
            props.load(fis);
        }
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "sales_summary-app");

        final String inputTopic = "sales-transactions";
        final String outputTopic = "sales-summary";

        //Creamos un Serde de tipo Avro ya que el productor produce <String,TemperatureTelemetry>
        final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url", "http://localhost:8081");
        Serde<SalesSummary> salesSummarySerde = new SpecificAvroSerde<>();
        salesSummarySerde.configure(serdeConfig, false);

        Serde<GenericRecord>  genericSerde = new GenericAvroSerde();
        genericSerde.configure(serdeConfig, false);

        //Creamos el KStream mediante el builder
        StreamsBuilder builder = new StreamsBuilder();

        builder.stream(inputTopic, Consumed.with(Serdes.String(), genericSerde))
                .groupBy((key,value) -> value.get("category").toString(), Grouped.with(Serdes.String(), genericSerde))
                .windowedBy(TimeWindows.of(Duration.ofMinutes(1)))
                .aggregate(
                        () -> new SalesSummary("", 0, (float) 0.0, Instant.EPOCH,Instant.EPOCH),
                        (key, transaction, summary) -> {
                            int quantity = (int)   transaction.get("quantity");
                            float price  = (float) transaction.get("price");

                            SalesSummary updatedSummary = new SalesSummary(
                                    key,
                                    summary.getTotalQuantity() + quantity,
                                    summary.getTotalRevenue() + (quantity * price),
                                    summary.getWindowStart(),
                                    summary.getWindowEnd()
                            );

                            return updatedSummary;
                        },
                        Materialized.with(Serdes.String(), salesSummarySerde)
                )
                .toStream()
                .map((wk, summary) -> {
                    Instant  start = Instant.ofEpochMilli(wk.window().start());
                    Instant  end =  Instant.ofEpochMilli(wk.window().end());
                    summary.setWindowStart(start);
                    summary.setWindowEnd(end);
                    return KeyValue.pair(wk.key(), summary);
                }
                )
                .peek((key, value) -> System.out.println("Outgoing record - key " + key + " value " + value))
                .to(outputTopic, Produced.with(Serdes.String(), salesSummarySerde));

        // Iniciar Kafka Streams
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        // Manejar cierre del programa
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
