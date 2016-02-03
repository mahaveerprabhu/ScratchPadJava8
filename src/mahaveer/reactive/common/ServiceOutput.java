package mahaveer.reactive.common;

/**
 * Generic marker interface for service outputs. If needed more common information methods can be added at a later point.
 *
 * Capturing and propagating errors is one common scenario where such information methdos will be useful
 *
 * This promotes a uniform interface for "Micro POJO services"
 *
 * Created by qxw121 on 12/2/15.
 */

public interface ServiceOutput {
    // base interface for all outputs
    // Generally this has an Alerts container, to collect alerts (ERROR / WARN / INFO notifications) that needs to be available to the end users / calling app
}
