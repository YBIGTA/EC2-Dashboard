package kafka.consumer;

import kafka.consumer.AvgUsage.AvgUsage;
import kafka.consumer.HardWareUsage.HardWareUsageDAO;
import kafka.consumer.HardWareUsage.TotalCpuDetail;
import kafka.consumer.commonSerialization.CustomSerdes;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonSerializer;


import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootApplication
public class ConsumerApplication {

    private static String TOPIC_NAME = "fooo";
    private static String GROUP_ID = "1foo";
    private static String BOOTSTRAP_SERVERS = "43.201.62.117:9092";

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
        /*
        Properties setting
        for using JsonDeserializer & JsonSerializer we need to take "Default key & value String Serdes"
         */
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "OverUsageResource");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        /*
        Make StreamBuilder
        this will take the HardWareData form "TOPIC FOO (where we make)" and set the CustomSerdes for Serializer and Deserializer
         */
        final StreamsBuilder builder = new StreamsBuilder();

        /*
        This part is pull the source data from "TOPIC FOO (where we make)" using customSerdes
         */
        KStream<String, HardWareUsageDAO> source = builder.stream("fooo",
                Consumed.with(Serdes.String(), CustomSerdes.HardWareUsageDAO()));

        /*
        This part is make the DAG using Streams
        we want to convert HardWareUsage(raw data) to AvgUsage(processed data)
        Using lambda map function and parsing String -> Double for processing
        and Return new Key Value
         */

        KStream<String, AvgUsage> target = source.map((k,v) ->{
            System.out.println(k);
            System.out.println(v);
            AvgUsage avg = new AvgUsage();
            avg.parser(v);
            System.out.println(avg.toString());

            return KeyValue.pair(k, avg);
        });

        /*
        This will make new TOPIC fooo2 (where we want to put)
         */
        target.to("fooo2", Produced.with(Serdes.String(), CustomSerdes.AvgUsage()));



        final Topology topology = builder.build();
        final KafkaStreams streams = new KafkaStreams(topology,props);
        final CountDownLatch latch = new CountDownLatch(1);

        /*
        Make Thread for stream and graceful exception
         */
        Runtime.getRuntime().addShutdownHook(new Thread("streams-Thread"){
            public void run(){
                streams.close();
                latch.countDown();
            }
        });

        try{
            streams.start();
            latch.await();
        }catch (Throwable e){
            System.exit(1);
        }
        System.exit(0);


    }



}
