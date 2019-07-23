package com.github.honwhy.mybatisinterceptor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisInterceptorApplicationTests {

    @Autowired
    private CityMapper cityMapper;
    @Test
    public void contextLoads() {
    }

    @Test
    public void test_malicious() {
        City city = new City();
        city.setCountry("china(name)values(\'bad\');drop table city_china;insert into city");
        city.setName("xxx");
        city.setState("yyy");
        cityMapper.insertCityDynamic(city.getCountry(), city);
    }
    @Test
    public void test_ok() {
        City city = new City();
        city.setCountry("china");
        city.setName("xxx");
        city.setState("yyy");
        cityMapper.insertCityDynamic(city.getCountry(), city);
    }
}
