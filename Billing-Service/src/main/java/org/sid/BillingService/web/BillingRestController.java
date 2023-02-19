package org.sid.BillingService.web;

import org.sid.BillingService.entities.Bill;
import org.sid.BillingService.feign.CustomerRestClient;
import org.sid.BillingService.feign.ProductItemRestClient;
import org.sid.BillingService.model.Customer;
import org.sid.BillingService.model.Product;
import org.sid.BillingService.repositories.BillRepository;
import org.sid.BillingService.repositories.ProductItemRepository;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingRestController {
    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private ProductItemRestClient productItemRestClient;

    public BillingRestController(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productItemRestClient = productItemRestClient;
    }

    @GetMapping(path = "/fullBill/{id}")
    public Bill getBill(@PathVariable(name = "id") Long id){
        Bill bill = billRepository.findById(id).get();
        Customer customer = customerRestClient.getCustomerById(bill.getCustomerId());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(p->{
            Product product=productItemRestClient.getProductById(p.getProductId());
            p.setProduct(product);
        });
        return bill;
    }
}
