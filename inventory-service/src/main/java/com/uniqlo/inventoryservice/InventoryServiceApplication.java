package com.uniqlo.inventoryservice;

import com.uniqlo.inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@AllArgsConstructor
public class InventoryServiceApplication {

    private InventoryRepository inventoryRepository;

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    /*    @Component
    public class insertTable implements CommandLineRunner {

    	@Override
    	public void run(String... args) throws Exception {
    		Inventory red = Inventory.builder()
    				.skuCode("red")
    				.quantity(100)
    				.build();
    		Inventory blue = Inventory.builder()
    				.skuCode("blue")
    				.quantity(100)
    				.build();
    		Inventory yellow = Inventory.builder()
    				.skuCode("yellow")
    				.quantity(100)
    				.build();

    		inventoryRepository.save(red);
    		inventoryRepository.save(blue);
    		inventoryRepository.save(yellow);
    	}
    }*/
}
