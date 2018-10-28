package edu.spring.data.nosql.redis.repository;

import edu.spring.data.nosql.redis.config.ApplicationConfiguration;
import edu.spring.data.nosql.redis.model.Product;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static edu.spring.data.nosql.redis.config.SecurityConfig.ADMIN;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class RedisProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void singleTest() throws InterruptedException {
        String id = RandomStringUtils.randomAlphanumeric(20);
        Product product = Product.builder()
                .id(id)
                .name("Product")
                .amount(RandomUtils.nextInt())
                .price(new BigDecimal(2.50))
                .build();
        productRepository.save(product);

        Product product1 = productRepository.get(id);

        Assert.assertEquals(product, product1);
    }

    @Test
    @WithMockUser(roles = {ADMIN})
    public void listTest() {

        Product product1 = Product.builder()
                .id(RandomStringUtils.randomAlphanumeric(20))
                .name("Product")
                .amount(RandomUtils.nextInt())
                .price(new BigDecimal(2.50))
                .build();
        Product product2 = Product.builder()
                .id(RandomStringUtils.randomAlphanumeric(20))
                .name("Product")
                .amount(RandomUtils.nextInt())
                .price(new BigDecimal(2.50))
                .build();

        productRepository.saveToList("CART", product1);
        productRepository.saveToList("CART", product2);

        List<Product> cart = productRepository.getList("CART");

        assertThat(cart)
                .containsOnly(product1, product2);
    }

    @Test
    public void cacheTest() throws InterruptedException {
        String ID = RandomStringUtils.randomAlphanumeric(20);
        Product product1 = Product.builder()
                .id(ID)
                .name("Product")
                .amount(RandomUtils.nextInt())
                .price(new BigDecimal(2.50))
                .build();

        productRepository.save(product1);

        StopWatch stopWatch = new LoggingStopWatch();

        stopWatch.start("READING FROM REDIS START");
        productRepository.get(ID);
        stopWatch.stop();

        stopWatch.start("READING FROM CACHE START");
        productRepository.get(ID);
        stopWatch.stop();

        stopWatch.start("READING FROM CACHE START");
        productRepository.get(ID);
        stopWatch.stop();
    }
}