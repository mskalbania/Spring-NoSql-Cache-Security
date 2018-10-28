package edu.spring.data.nosql.redis.repository;

import edu.spring.data.nosql.redis.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

import java.util.List;

import static edu.spring.data.nosql.redis.config.SecurityConfig.ADMIN;

@Repository
public class RedisProductRepository implements ProductRepository {

    private RedisOperations<String, Product> redisOperations;

    @Autowired
    public RedisProductRepository(final RedisOperations<String, Product> redisOperations) {
        this.redisOperations = redisOperations;
    }

    @CachePut(key = "#product.id", value = "product")
    public void save(Product product) {
        redisOperations.opsForValue().set(product.getId(), product);
    }

    @Cacheable("product")
    public Product get(String id) throws InterruptedException {
        Thread.sleep(1000); // JUST TO OBSERVE CASHING
        return redisOperations.opsForValue().get(id);
    }

    @Override
    @Secured({"ROLE_ADMIN"})
    public void saveToList(final String listName, final Product product) {
        redisOperations.opsForList().rightPush(listName, product);
    }

    @Override
    public List<Product> getList(final String listName) {
        return redisOperations.opsForList().range(listName, 0, -1);
    }
}
