package org.example.model.CSV;

import java.util.List;

public interface AccessInterface<T> {

    void create(T t);
    T read(int id);
    void update(T t);
    void delete(T t);
    List<T> readAll();

}
