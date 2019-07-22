package com.github.honwhy.mybatisinterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private CityMapper cityMapper;

    @PostMapping("/hello/{id}")
    public City hello(@PathVariable Integer id) {
        City city = cityMapper.selectCityById(id);
        return city;
    }

    @PostMapping("/add")
    public Integer addCity(@RequestBody City city) {
        int ret = cityMapper.insertCityDynamic(city.getCountry(), city);
        return ret;
    }
}
