package mahaveer.reactive.common;

/**
 * Generic marker interface for service inputs. If needed more context information methods can be added at a later point.
 *
 * This promotes a uniform interface for "Micro POJO services"
 *
 * Created by qxw121 on 12/2/15.
 */

public interface ServiceInput {
    // base interface for all inputs
    // Generally the attributes that go in this base are RequestContext that has context information like user details, authorizations, calling application details etc).
    // This information is generally setup by the filters that intercept the request and set on ThreadLocal. The REST entry points
    // can grab them using util methods to pass on to create the input
    //

    // At this point I am having this class as a marker interface to communicate the idea. If we proceed in this direction, we could create a base class that has such
    // attributes.
}
