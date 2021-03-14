package fon.master.nst.shoppingcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableResourceServer
public class ShoppingCartApplication {

	@Bean
	//@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}	
	
	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApplication.class, args);
	}

}
