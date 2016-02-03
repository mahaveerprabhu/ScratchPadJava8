package mahaveer.reactive.common;

/**
 * Created by qxw121 on 1/1/16.
 */
public interface Service<I,O> {
    O execute(I input);
}
