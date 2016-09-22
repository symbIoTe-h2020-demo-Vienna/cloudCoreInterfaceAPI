package eu.h2020.symbiote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CloudCoreInterfaceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudCoreInterfaceApiApplication.class, args);
	}
}
