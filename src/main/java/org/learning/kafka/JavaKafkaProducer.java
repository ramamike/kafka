package org.learning.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
public class JavaKafkaProducer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String server = "localhost:9092";
        String topicName = "theFirstTopic";

        final Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                server);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());

        final Producer<Long, String> producer = new KafkaProducer<>(props);

        for (int i = 0; i < 10; i++) {
            String message = "message " + i;
            try {
                producer.send(new ProducerRecord(topicName, "example message " + i)).get();
            } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
                log.error(message + ", is mot send, producer is closed", e.getCause().toString());
                producer.close();
            } catch (KafkaException e) {
                log.error(message + ", transaction is aborted and try again", e.getCause().toString());
                producer.abortTransaction();
            }
        }
        producer.close();
//        RecordMetadata recordMetadata = (RecordMetadata) producer.send(new ProducerRecord(topicName, "example message")).get();
//        if (recordMetadata.hasOffset())
//            System.out.println("Message sent successfully");

//        producer.close();
    }
}
