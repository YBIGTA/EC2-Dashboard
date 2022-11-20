package kafka.consumer;

import kafka.consumer.HardWareUsage.HardWareUsageDAO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

@SpringBootApplication
public class ConsumerApplication {

    private static String TOPIC_NAME = "fooo";
    private static String GROUP_ID = "1foo";
    private static String BOOTSTRAP_SERVERS = "3.34.192.110:9092";

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);

        Properties configs = new Properties();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // JsonDeserializer
        // TRUSTED_PACKAGES is trust all
        // VALUE_DEFAULT_TYPE means which object is sent
        configs.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
        configs.put(JsonDeserializer.VALUE_DEFAULT_TYPE,HardWareUsageDAO.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        KafkaConsumer<String, Object> consumer = new KafkaConsumer<String, Object>(configs);

        consumer.subscribe(Arrays.asList(TOPIC_NAME));

        while (true) {
            ConsumerRecords<String, Object> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, Object> record : records) {
                HardWareUsageDAO hardWareUsageDAO  = (HardWareUsageDAO)record.value();
                System.out.println(hardWareUsageDAO);
            }
        }
    }

}
