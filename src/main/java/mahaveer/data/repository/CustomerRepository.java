package mahaveer.data.repository;


import mahaveer.data.entity.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by xkt676 on 12/2/15.
 */
public interface CustomerRepository extends CrudRepository<Customer, String> {

    Iterable<Customer> findAll();

    Customer save(Customer var1);

    Customer findOne(String id);

    Customer findByFirstName(String firstName);

    void delete(String id);

    boolean exists(String var1);

}
