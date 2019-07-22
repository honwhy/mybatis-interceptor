package com.github.honwhy.mybatisinterceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Intercepts({
        @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class,Integer.class })
})
@Slf4j
public class MyInterceptor implements Interceptor {
    private String regex = "#\\[(.+)\\]#";
    private Pattern pattern = Pattern.compile(regex);
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        Method method = ReflectionUtils.findMethod(target.getClass(), "getBoundSql");
        BoundSql boundSql = (BoundSql) method.invoke(target);
        String sql = boundSql.getSql();
        log.debug(target.getClass().getSimpleName() + "->sql:" + sql);
        Matcher m = pattern.matcher(sql);
        StringBuffer sb = new StringBuffer(sql.length());
        if (!m.find()) {
            return invocation.proceed();
        }
        String key = m.group(1);
        Map<String, Object> map = (Map<String, Object>) boundSql.getParameterObject();
        String value = String.valueOf(map.get(key));
        log.debug(key + "=>" + value);
        m.appendReplacement(sb, value);
        m.appendTail(sb);
        sql = sb.toString();
        log.debug("after alter sql: " + sql);
        //change boundSql
        Field sqlField = ReflectionUtils.findField(BoundSql.class, "sql");
        sqlField.setAccessible(true);
        ReflectionUtils.setField(sqlField, boundSql, sql);

        Field delegateField = ReflectionUtils.findField(target.getClass(), "delegate");
        delegateField.setAccessible(true);
        Object delegate = ReflectionUtils.getField(delegateField, target);
        Field boundSqlField = ReflectionUtils.findField(delegate.getClass(), "boundSql");
        //set back to delegate boundSql
        boundSqlField.setAccessible(true);
        ReflectionUtils.setField(boundSqlField, delegate, boundSql);
        return invocation.proceed();
    }
}
