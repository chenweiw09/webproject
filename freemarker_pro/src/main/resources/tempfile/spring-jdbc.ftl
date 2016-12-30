<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
	xmlns:tx="http://www.springframework.org/schema/tx" xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd  
    http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd  
    http://www.springframework.org/schema/tx  http://www.springframework.org/schema/tx/spring-tx-3.0.xsd  
    http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-3.0.xsd" >

	
	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="${r'${jdbc_url}'}"/>
        <property name="username" value="${r'${jdbc_username}'}"/>
        <property name="password" value="${r'${jdbc_password}'}"/>
        <property name="testOnBorrow" value="false" />
        <property name="testWhileIdle" value="true" />
        <property name="initialSize" value="10"/>
        <property name="maxActive" value="20"/>
        <property name="maxIdle" value="5"/>
        <property name="minIdle" value="1"/>
      </bean>

    <bean id = "jdbcTemplate" class = "org.springframework.jdbc.core.JdbcTemplate">
        <property name = "dataSource" ref="dataSource"/>
    </bean>

    <bean id="jdbcTemplateTool" class="org.crazycake.jdbcTemplateTool.JdbcTemplateTool">
        <property name = "jdbcTemplate" ref="jdbcTemplate" />
    </bean>

	<!-- 配置事务管理器 -->
	<bean id="transactionManager"
		class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource" />
	</bean>
	
	<!-- 注解方式配置事物 -->
	<tx:annotation-driven transaction-manager="transactionManager" />

</beans>