<?xml version="1.0" encoding="UTF-8"?>

<configuration scan="true">
    <property name="APP" value="freemarker_pro"/>
    <property name="LOG_HOME" value="/export/log/${r'${APP}'}"/>

    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yy-MM-dd.HH:mm:ss.SSS} [%-16t] %-5p %-16c{0} - %m%n</pattern>
        </encoder>
    </appender>
    <appender name="DETAIL" class="ch.qos.logback.core.rolling.RollingFileAppender" additivity="false">
        <File>${r'${LOG_HOME}'}/${r'${APP}'}_detail.log</File>
        <encoder>
            <pattern>%d{yy-MM-dd.HH:mm:ss.SSS} [%-16t] %-5p %-16c{0} - %m%n</pattern>
        </encoder>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${r'${LOG_HOME}'}/${r'${APP}'}_detail.log.%d{yyyyMMdd}</fileNamePattern>
        </rollingPolicy>
    </appender>
    <appender name="ACCESS" class="ch.qos.logback.core.rolling.RollingFileAppender" additivity="false">
        <File>${r'${LOG_HOME}'}/${r'${APP}'}_access.log</File>
        <encoder>
            <pattern>%d{yy-MM-dd.HH:mm:ss.SSS},%m%n</pattern>
        </encoder>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${r'${LOG_HOME}'}/${r'${APP}'}_access.log.%d{yyyyMMdd}</fileNamePattern>
        </rollingPolicy>
    </appender>

    <logger name="ACCESS">
        <appender-ref ref="ACCESS"/>
    </logger>
    <logger name="org.springframework" level="INFO"/>
    <logger name="org.apache.httpclient.wire" level="INFO"/>
    <logger name="org.apache.commons.httpclient" level="INFO"/>
    <logger name="org.apache.zookeeper" level="INFO"/>
    <logger name="org.springframework.beans" level="debug"/>
    <logger name="org.springframework.core" level="debug"/>
    <logger name="com.jdjr.crpl.product" level="DEBUG"/>
    <logger name="java.sql.Connection" level="DEBUG"/>
    <logger name="java.sql.Statement" level="DEBUG"/>
    <logger name="java.sql.PreparedStatement" level="DEBUG"/>

    <root level="INFO">
        <appender-ref ref="DETAIL"/>
        <appender-ref ref="CONSOLE"/>
    </root>
</configuration>
