<?xml version="1.0" encoding="UTF-8"?>
<beans:beans xmlns="http://www.springframework.org/schema/mvc"
             xmlns:aop="http://www.springframework.org/schema/aop"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns:mvc="http://www.springframework.org/schema/mvc"
             xmlns:beans="http://www.springframework.org/schema/beans"
             xmlns:context="http://www.springframework.org/schema/context"
             xmlns:tx="http://www.springframework.org/schema/tx"
             xsi:schemaLocation="http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd  
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
        ">

    <!-- DispatcherServlet Context: defines this servlet's request-processing infrastructure -->

    <!-- Enables the Spring MVC @Controller programming model -->

    <annotation-driven>
        <message-converters register-defaults="true">
            <beans:bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <beans:property name="supportedMediaTypes" value="text/html;charset=UTF-8"/>
            </beans:bean>
        </message-converters>
    </annotation-driven>
    <context:component-scan base-package="${packagePath}.controller"/>
	
	 <!-- Handles HTTP GET requests for /resources/** by efficiently serving up static resources in the webappRoot/resources directory -->
    <resources mapping="/static/css/**" location="/static/css/" />
    <resources mapping="/static/images/**" location="/static/images/" />
    <resources mapping="/static/js/**" location="/static/js/" />
    <resources mapping="/static/**" location="/static/" /> 
    
    <!-- Jackson转换器 -->
    <beans:bean id="mappingJacksonHttpMessageConverter"
                class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter"/>
    <!-- fastjson转换器 -->
    <beans:bean id="fastJsonHttpMessageConverter"
                class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter"/>

    <beans:bean class="org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter">
        <beans:property name="messageConverters">
            <beans:list>
                <beans:ref bean="fastJsonHttpMessageConverter"/>
                <!-- json转换器 -->
            </beans:list>
        </beans:property>
    </beans:bean>

    <!-- Resolves views selected for rendering by @Controllers to .jsp resources in the /WEB-INF/views directory -->
    <beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <beans:property name="prefix" value="/views/"/>
        <beans:property name="suffix" value=".jsp"/>
    </beans:bean>

    <beans:bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
        <beans:property name="maxUploadSize" value="32505856"/>
        <!-- 上传文件大小限制为31M，31*1024*1024 -->
        <beans:property name="maxInMemorySize" value="4096"/>
    </beans:bean>
</beans:beans> 