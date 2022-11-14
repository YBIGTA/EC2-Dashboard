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
	public static boolean isFloat(String str) {
		try {
			Float.parseFloat(str);
			return true;
		} catch(NumberFormatException e){
			return false;
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
//		final String TOPIC_NAME = "fooo";
//		String bootstrapServer = args[0] + ":9092";
//		System.out.println("bootstrap server : " + bootstrapServer);

		/*
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
		 */

		int temp_count = 0;
		while(temp_count-- >= 0) {
//		while (true) {


			String sendOutStr = ""; // the output string to be sent to kafka broker
			String temp;
			Process p;
			int lineNumber = 0;
			try {
				p = Runtime.getRuntime().exec("top -l 2");
				BufferedReader br = new BufferedReader(new InputStreamReader((p.getInputStream())));

				int secondRound = -1;
				while ((temp = br.readLine()) != null) {
					if (lineNumber++ == 0) {
						String[] temp_str = temp.split(" ");
//						System.out.println(temp_str[1]);
						secondRound = Integer.parseInt(temp_str[1]) + 13;
						continue;
					}

					if (lineNumber < secondRound) {
						continue;
					}

//					if(lineNumber >= 0){ // 11 이상이면 PID, COMMAND, ... 이런 거 포함, 12 이상이면 첫 번쟤 processor부터

					if (lineNumber == secondRound + 3) { // CPU usage
						sendOutStr += lineNumber + "] " + temp + '\n';
					} else if (lineNumber == secondRound + 6) { // PhysMem
						sendOutStr += lineNumber + "] " + temp + '\n';
					} else if (lineNumber == secondRound + 9) { // Disks
						sendOutStr += lineNumber + "] " + temp + '\n';
					} else if (lineNumber >= secondRound + 12) {
						String[] temp_str = temp.split("\\s+");
//						System.out.println(temp);
//						System.out.println("percentCPU : " + temp_str[2]);
						float percentCPU;

						if (isFloat(temp_str[2])) { // process 이름 안에 space 있는 경우 일단 넘어감
							percentCPU = Float.parseFloat(temp_str[2]);

							if (percentCPU >= 1.0){
								sendOutStr += lineNumber + "] " + temp + '\n';
							}
						}


					}
//					sendOutStr += lineNumber + "] " + temp + '\n';



				}
				p.waitFor();
				p.destroy();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			System.out.println(sendOutStr);

			// sending kafka
			/*
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC_NAME, sendOutStr);
			try {
				producer.send(record);
				System.out.println(sendOutStr);
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
			 */

		}
	}

}
