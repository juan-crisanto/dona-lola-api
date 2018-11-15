package com.donalola.customer.application;

import com.donalola.CustomerID;
import com.donalola.authentication.AppUser;
import com.donalola.customer.domain.Customer;
import com.donalola.customer.domain.CustomerRepository;
import com.donalola.customer.domain.Identity;
import com.donalola.util.security.SecurityContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping(
        value = "api/customer",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@Api(
        value = "Customer",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class CustomerRestService {

    private final CustomerRepository customerRepository;

    public CustomerRestService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/me")
    public CustomerJson get(Principal principal) {
        return CustomerJson.of(this.customerRepository.get(new CustomerID(principal.getName())));
    }

    @GetMapping("/{customerId}")
    public CustomerJson getByUserID(@PathVariable(value = "customerId") String customerId) {
        return CustomerJson.of(this.customerRepository.get(new CustomerID(customerId)));
    }

    @PostMapping("/save")
    public CustomerJson save(@RequestBody CustomerJson customerJson, Principal principal) {
        AppUser appUser = (AppUser) SecurityContextUtil.getAuthentication().getDetails();
        Customer customer = Customer.builder(new CustomerID(principal.getName()))
                .Email(appUser.getEmail())
                .FirstName(customerJson.getName())
                .LastName(customerJson.getLastName())
                .Phone(customerJson.getPhone())
                .Identity(customerJson.getIdentityType(), customerJson.getIdentity())
                .build();
        return CustomerJson.of(this.customerRepository.save(customer));
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class CustomerJson implements Serializable {

        private static final long serialVersionUID = 3516509211249613311L;

        private String name;
        private String lastName;
        private Identity.Type identityType;
        private String identity;
        private String phone;
        @ApiModelProperty(readOnly = true)
        private String email;

        private CustomerJson(final Customer customer) {
            this.name = customer.getCustomerDetails().getName();
            this.lastName = customer.getLastName();
            this.identityType = customer.getIdentity().getType();
            this.identity = customer.getIdentity().getValue();
            this.phone = customer.getCustomerDetails().getPhone();
            this.email = customer.getCustomerDetails().getEmail();
        }

        public static CustomerJson of(Customer customer) {
            if (customer == null) {
                throw new IllegalArgumentException("Customer must no be null");
            }
            return new CustomerJson(customer);
        }

    }

}
