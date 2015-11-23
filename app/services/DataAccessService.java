package services;

import java.util.List;

/**
 * Created by tito on 21/10/15.
 */
public interface DataAccessService<T, ID> {

    T create(T t);

    List<T> findAll();

    T findById(ID id);

    void update(T t);

    Class<T> getEntityClass();

    List<T> findByBatch(Integer page, Integer size);
}
