package edu.spring.data.nosql.redis.repository;

import edu.spring.data.nosql.redis.model.Product;

import java.util.List;

public interface ProductRepository {

    void save(Product product);

    Product get(String id) throws InterruptedException;

    void saveToList(String listName, Product product);

    List<Product> getList(String listName);
}
