<?xml version="1.0" encoding="UTF-8"?>  
<beans xmlns="http://www.springframework.org/schema/beans"  
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  
    xmlns:context="http://www.springframework.org/schema/context"
    xmlns:util="http://www.springframework.org/schema/util"  
    xsi:schemaLocation="
    http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd  
    http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd
    http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-3.0.xsd"
    >
    <!-- 引入属性文件 -->
    <bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="locations">
            <list>
                <value>classpath:main.properties</value>
            </list>
        </property>
    </bean>
    
    <util:properties id="main" location="classpath:main.properties"></util:properties>

    <context:component-scan base-package="${packagePath}">
    	<context:exclude-filter expression="${packagePath}.controller.*" type="regex"/>
    </context:component-scan>


    <import resource="spring-jdbc.xml"/>

</beans>