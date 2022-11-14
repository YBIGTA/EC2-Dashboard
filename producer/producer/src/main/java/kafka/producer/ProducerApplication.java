package kafka.producer;

import kafka.producer.HardWareUsage.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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

		final String TOPIC_NAME = "fooo";
		String bootstrapServer = args[0] + ":9092";
		System.out.println("bootstrap server : " + bootstrapServer);


		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

		KafkaProducer<String, HardWareUsageDAO> producer = new KafkaProducer<>(properties);


		// object to HardWareUsageDAO
		TotalCpuDetail cpuDetail = null;
		TotalMemDetail memDetail = null ;
		TotalDiskDetail diskDetail = null;
		List<TopProcessDetail> topRateProcess = new ArrayList<>();
		HardWareUsageDAO hardWareUsageDAO = new HardWareUsageDAO();

		while (true) {
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
						secondRound = Integer.parseInt(temp_str[1]) + 13;
						continue;
					}

					if (lineNumber < secondRound) {
						continue;
					}

					if (lineNumber == secondRound + 3) { // CPU usage
						String[] temp_str = temp.split(" ");
						cpuDetail = new TotalCpuDetail(temp_str[2], temp_str[4]);


					} else if (lineNumber == secondRound + 6) { // PhysMem
						String[] temp_str = temp.split(" ");
						memDetail = new TotalMemDetail(temp_str[1], temp_str[7]);

					} else if (lineNumber == secondRound + 9) { // Disks
						String[] temp_str = temp.split(" ");
						diskDetail = new TotalDiskDetail(temp_str[1], temp_str[3]);

					} else if (lineNumber >= secondRound + 12) { // Processes
						String[] temp_str = temp.split("\\s+");
						float percentCPU;

						if (isFloat(temp_str[2])) { // process 이름 안에 space 있는 경우 일단 넘어감
							percentCPU = Float.parseFloat(temp_str[2]);

							if (percentCPU >= 1.0){
								sendOutStr += lineNumber + "] " + temp + '\n';

								TopProcessDetail topProcessDetail = new TopProcessDetail(temp_str[0], temp_str[1], temp_str[2], temp_str[4], temp_str[7], temp_str[12]);
								topRateProcess.add(topProcessDetail);
							}
						}


					}

				}
				p.waitFor();
				p.destroy();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			hardWareUsageDAO.setCPU(cpuDetail).setDISK(diskDetail).setMEM(memDetail).setTopRateProcess((ArrayList<TopProcessDetail>) topRateProcess);



			// sending kafka
			ProducerRecord<String, HardWareUsageDAO> record = new ProducerRecord<String, HardWareUsageDAO>(TOPIC_NAME, hardWareUsageDAO);
			try {
				producer.send(record);
//				System.out.println(sendOutStr);
				System.out.println(hardWareUsageDAO);
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}


		}
	}

}
