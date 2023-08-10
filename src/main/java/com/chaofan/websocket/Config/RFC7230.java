package com.chaofan.websocket.Config;


import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RFC7230 {

    /**
     * 解决Tomcat RFC7230以及Header状态码加上描述字段的问题
     *
     * @return
     */
    @Bean
    public ServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            //设置解析描述符为true,例如Header 状态码为200,那么解析出来就是200 ok
            connector.setProperty("sendReasonPhrase", "true");
            //这个属性在tomcat为8.5的时候不生效
            connector.setProperty("rejectIllegalHeader", "false");
        });
        return factory;
    }
}
