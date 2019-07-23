# 动态修改表名插件

如MyBatis的Mapper接口方法
```code
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
### //TODO
优化：对被拦截的target的类缓存是否需要做拦截处理

### 改进实现方案
Mapper接口被autowired到其他bean中，实际上是对Mapper接口的代理实现，对这个动态代理做扩展，
加入我们的Interceptor，当发现调用的方法有注解则修改sql，进而修改mybatis概念中的MappedStatement，
这个修改可以缓存起来。