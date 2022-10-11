package kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

@SpringBootApplication
public class ProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
		final String TOPIC_NAME = "fooo";
		String bootstrapServer = args[0] + ":9092";
//		System.out.println("bootstrap server : " + bootstrapServer);

		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
		while (true) {

			String sendOutStr = ""; // the output string to be sent to kafka broker
			String temp;
			Process p;
			int count = 0;
			try {
				p = Runtime.getRuntime().exec("top -l 1");
				BufferedReader br = new BufferedReader(new InputStreamReader((p.getInputStream())));
				while ((temp = br.readLine()) != null) {

					if(count++ >= 11){ // 11 이상이면 PID, COMMAND, ... 이런 거 포함, 12 이상이면 첫 번쟤 processor부터
						sendOutStr += temp + '\n';
					}

				}
				p.waitFor();
				p.destroy();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
//			System.out.println(sendOutStr);

			// sending kafka
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC_NAME, sendOutStr);
			try {
				producer.send(record);
				System.out.println(sendOutStr);
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
