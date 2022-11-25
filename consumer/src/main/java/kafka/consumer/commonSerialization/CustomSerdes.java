package kafka.consumer.commonSerialization;

import kafka.consumer.AvgUsage.AvgUsage;
import kafka.consumer.HardWareUsage.HardWareUsageDAO;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public final class CustomSerdes {
    private CustomSerdes(){}

    public static Serde<HardWareUsageDAO> HardWareUsageDAO(){
        JsonSerializer<HardWareUsageDAO> serializer = new JsonSerializer<>();
        JsonDeserializer<HardWareUsageDAO> deserializer = new JsonDeserializer<>(HardWareUsageDAO.class);
        return Serdes.serdeFrom(serializer,deserializer);
    }

    public static Serde<AvgUsage> AvgUsage(){
        JsonSerializer<AvgUsage> serializer = new JsonSerializer<>();
        JsonDeserializer<AvgUsage> deserializer = new JsonDeserializer<>(AvgUsage.class);
        return Serdes.serdeFrom(serializer,deserializer);
    }

}
