package mahaveer.reactive.paginate;

/**
 * Created by qxw121 on 1/1/16.
 */
public interface Paginator<I,O> {
    O execute(I input);
    boolean hasMore(O output);
    I prepareNextInput(I previousINput, O previousOutput);
    O getFinalOutput();
    void collect(O partialOutput);
}
