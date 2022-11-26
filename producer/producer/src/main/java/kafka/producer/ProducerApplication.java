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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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


//		final String TOPIC_NAME = "fooo";
//		String bootstrapServer = args[0] + ":9092";
//		System.out.println("bootstrap server : " + bootstrapServer);
//
//
//		Properties properties = new Properties();
//		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
//		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
//		/*
//		JSONSERIALIZER ADD_TYPE_INFO_HEADERS can serializer infer type of JSON Object
//		 */
//		properties.put(JsonSerializer.ADD_TYPE_INFO_HEADERS,HardWareUsageDAO.class.getName());
//		KafkaProducer<String, HardWareUsageDAO> producer = new KafkaProducer<>(properties);


		// object to HardWareUsageDAO
		TotalCpuDetail cpuDetail = null;
		TotalMemDetail memDetail = null ;
		TotalDiskDetail diskDetail = null;
		List<TopProcessDetail> topRateProcess = new ArrayList<>();
		HardWareUsageDAO hardWareUsageDAO = new HardWareUsageDAO();
		hardWareUsageDAO.setEC2Number(args[1]);

		// parsing "TOP COMMAND"
		int staringPoint=0;
		while (true) {
			if (staringPoint == 0) {
				staringPoint++;
				continue;
			}

			String sendOutStr = ""; // the output string to be sent to kafka broker
			String temp;
			Process p;
			int lineNumber;


			// TOP COMMAND HANDLER
			try {

				lineNumber = 0;
				p = Runtime.getRuntime().exec("top -b -n 1");
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

				while ((temp = br.readLine()) != null) {
					if (lineNumber < 2) {
						lineNumber++;
						continue;
					}


					String[] temp_str = temp.split("\\s+");

					// PARSING
					if (lineNumber == 2) { // parsing CPU
						System.out.println(Arrays.toString(temp_str));
						cpuDetail = new TotalCpuDetail(Float.parseFloat(temp_str[1]), Float.parseFloat(temp_str[3]));
						System.out.println(cpuDetail.toString());

					} else if (lineNumber == 3) { // parsing PhysMem
						memDetail = new TotalMemDetail(Float.parseFloat(temp_str[7]), Float.parseFloat(temp_str[5]));

					} else if (lineNumber > 6) { // parsing ProcessDetail;
						if (Float.parseFloat(temp_str[9]) >= 1.0 ) {
							TopProcessDetail topProcessDetail = new TopProcessDetail(Integer.parseInt(temp_str[1]), temp_str[12], Float.parseFloat(temp_str[9]), temp_str[11], Float.parseFloat(temp_str[10]), temp_str[8]);
							topRateProcess.add(topProcessDetail);
						}
						
					}

					lineNumber++;

				}

				p.waitFor();
				p.destroy();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}


			// DF COMMAND HANDLER FOR DISK INFO
			try {
				p = Runtime.getRuntime().exec("df -h");

				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				br.readLine(); // firstline like "Filesystem/Size/Used/Avail/Use%/Mounted on"
				String[] tempDisk = br.readLine().split("\\s+");

				String readDisk = tempDisk[2].substring(0, tempDisk[2].length()-1);
				String writeDisk = tempDisk[3].substring(0, tempDisk[3].length()-1);

				diskDetail = new TotalDiskDetail(Float.parseFloat(readDisk), Float.parseFloat(writeDisk));

				p.waitFor();
				p.destroy();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}


			hardWareUsageDAO.setCPU(cpuDetail).setDISK(diskDetail).setMEM(memDetail).setTopRateProcess((ArrayList<TopProcessDetail>) topRateProcess);
			System.out.println(hardWareUsageDAO);

			/*
			// sending kafka
			ProducerRecord<String, HardWareUsageDAO> record = new ProducerRecord<String, HardWareUsageDAO>(TOPIC_NAME, hardWareUsageDAO);
			try {
				producer.send(record);
				System.out.println(hardWareUsageDAO);
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}

			 */

		}
	}

}
