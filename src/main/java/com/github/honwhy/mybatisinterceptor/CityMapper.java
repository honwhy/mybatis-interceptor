package com.github.honwhy.mybatisinterceptor;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityMapper {

    /**xml
     */
    City selectCityById(int city_id);

    int insert(City city);

    int insertCityDynamic(@Param("country") String country, @Param("city")City city);
}