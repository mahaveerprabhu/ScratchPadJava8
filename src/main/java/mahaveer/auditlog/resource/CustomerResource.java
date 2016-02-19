package mahaveer.auditlog.resource;

import mahaveer.auditlog.Audit;
import mahaveer.auditlog.model.AuditLogPayload;
import mahaveer.data.entity.Customer;
import mahaveer.data.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by qxw121 on 2/10/16.
 */
@RestController
@RequestMapping(value = "/user")
@Audit
public class CustomerResource {


    @Autowired
    CustomerRepository customerRepository;
    //@Audit
    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public
    @ResponseBody
    Customer create(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public
    @ResponseBody
    Customer getCustomer(@RequestParam String id) {
        return customerRepository.findOne(id);
    }

    @RequestMapping(value = "/customer-name", method = RequestMethod.GET)
    public
    @ResponseBody
    Customer getCustomerByName(@RequestParam String name) {
        return customerRepository.findByFirstName(name);
    }

}
