package org.sid.BillingService;

import org.sid.BillingService.entities.Bill;
import org.sid.BillingService.entities.ProductItem;
import org.sid.BillingService.feign.CustomerRestClient;
import org.sid.BillingService.feign.ProductItemRestClient;
import org.sid.BillingService.model.Customer;
import org.sid.BillingService.model.Product;
import org.sid.BillingService.repositories.BillRepository;
import org.sid.BillingService.repositories.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(BillRepository billRepository,
							ProductItemRepository productItemRepository,
							CustomerRestClient customerRestClient,
							ProductItemRestClient productItemRestClient){
		return args -> {
			Customer customer= customerRestClient.getCustomerById(1L);
			Bill bill1=billRepository.save(new Bill(null,new Date(),null,customer.getId(),customer));
			PagedModel<Product> productPagedModel=productItemRestClient.pageProducts();
			productPagedModel.forEach(p->{
				ProductItem productItem = new ProductItem();
				productItem.setPrice(p.getPrice());
				productItem.setProductId(p.getId());
				productItem.setQuantity(1+new Random().nextInt(100));
				productItem.setBill(bill1);
				productItemRepository.save(productItem);
			});
		};
	}
}
