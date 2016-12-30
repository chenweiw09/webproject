<?xml version="1.0" encoding="UTF-8"?>
<web-app id="WebApp_ID" version="3.0"
	xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd">

	<!-- 区分项目名称，防止默认重名 -->
	<context-param>
		<param-name>webAppRootKey</param-name>
		<param-value>XXXX</param-value>
	</context-param>

	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:applicationContext.xml</param-value>
	</context-param>

	<!-- 字符集 过滤器 -->
	<filter>
		<filter-name>CharacterEncodingFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>UTF-8</param-value>
		</init-param>
		<init-param>
			<param-name>forceEncoding</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>CharacterEncodingFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>


	<!-- 解决xss漏洞 <filter> <filter-name>xssFilter</filter-name> <filter-class>com.wallet.redbag.front.filter.XSSFilter</filter-class> 
		</filter> <filter-mapping> <filter-name>xssFilter</filter-name> <url-pattern>/*</url-pattern> 
		</filter-mapping> -->

	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
	<!-- log4j listener -->
	<listener>
		<listener-class>org.springframework.web.util.Log4jConfigListener</listener-class>
	</listener>

	<!-- Spring view分发器 -->
	<servlet>
		<servlet-name>servletDispatcher</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>classpath:dispatcher-servlet.xml</param-value>
		</init-param>
		<init-param>
			<param-name>dispatchOptionsRequest</param-name>
			<param-value>true</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>
	<servlet-mapping>
		<servlet-name>servletDispatcher</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>
	<jsp-config>
		<jsp-property-group>
			<url-pattern>*.jsp</url-pattern>
			<trim-directive-whitespaces>true</trim-directive-whitespaces>
		</jsp-property-group>
	</jsp-config>
	<welcome-file-list>
		<welcome-file>index.html</welcome-file>
		<welcome-file>index.jsp</welcome-file>
	</welcome-file-list>
</web-app>