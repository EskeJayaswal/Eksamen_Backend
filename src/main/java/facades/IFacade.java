package facades;

import errorhandling.NotFoundException;
import java.util.List;

public interface IFacade<T> {
    T create(T t) throws NotFoundException;
    T update(T t) throws NotFoundException;
    T delete(Long id) throws NotFoundException;
    T getById(Long id) throws NotFoundException;
    List<T> getAll();
    long getCount();
}
