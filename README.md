# 动态修改表名插件

如MyBatis的Mapper接口方法
```java
int insertDynamic(@Param("country")String country, @Param("city") City city);
```
对应的sql
```sql
insert into city_#[country]#(name,state,country) values(#{name},#{state},#{country})
```
调用Mapper方法传入的country=china，那么应用插件后，在prepareStatement阶段做了处理，实际调用的sql是
```sql
insert into city_china(name,state,country) values(?,?,?)
```
