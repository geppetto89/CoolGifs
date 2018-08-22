package michele.meninno.coolgifs.core;

/**
 * <h1>Mapper </h1>
 *
 * The aim of this interface is mapping a dto object into a model object
 *
 * @author  Michele Meninno
 */
public interface Mapper<S, T> {
    T map(S elementToMap);
}
