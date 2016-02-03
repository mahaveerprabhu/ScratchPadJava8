package mahaveer.reactive.common;

import rx.Observable;

/**
 * Created by qxw121 on 12/2/15.
 */
public interface AsyncService<I extends ServiceInput,O extends ServiceOutput> {

    /**
     * General interface for "Micro POJOs" (single method interface). Single method interfaces provide a few advantages:
     * 1. Promotes and ensures smaller classes that focused on one well defined cohesive piece of functionality
     * 2. Smaller classes encapsulating small unit of logical functions with well defined input / output enable different
     *    classes to be developed in parallel by multiple developers.
     * 2. Enables easily testable classes
     * 3. Aspects can be weaved in easily for monitoring / metrics / logging and other common concerns.
     *    This can also aid in isolating performance bottlenecks
     * @param input List of Transactions with AccountReferenceId, SOR Id and Transaction Reference Ids
     * @return TransactionDetailWithReceipt List of Transactions with transaction details
     * @see ServiceInput For context information that can go in the input to all MICRO POJOs
     * @see ServiceOutput For user alerts information that can go in the output to all MICRO POJOs
     */
    Observable<O> observe(I input);

}
