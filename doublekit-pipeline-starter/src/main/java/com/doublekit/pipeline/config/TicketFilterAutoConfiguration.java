package com.doublekit.pipeline.config;

import com.doublekit.eam.client.auth.Authenticator;
import com.doublekit.eam.client.config.TicketConfig;
import com.doublekit.eam.client.config.TicketConfigBuilder;
import com.doublekit.eam.client.filter.TicketFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketFilterAutoConfiguration {

    @Autowired
    Authenticator authenticator;

    @Bean
    public FilterRegistrationBean ticketFilterRegistration(TicketConfig ticketConfig) {
        TicketFilter ticketFilter = new TicketFilter();
        ticketFilter.setTicketConfig(ticketConfig);
        ticketFilter.setAuthenticator(authenticator);

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(ticketFilter);
        registration.setName("ticketFilter");
        registration.addUrlPatterns("/*");
        registration.setOrder(2);
        return registration;
    }

    @Bean
    public TicketConfig ticketConfig(){
        return TicketConfigBuilder.instance()
                .ignoreTypes(new String[]{
                        ".ico",
                        ".jpg",
                        ".jpeg",
                        ".png",
                        ".gif",
                        ".html",
                        ".js",
                        ".css",
                        ".json",
                        ".xml",
                        ".ftl"
                })
                .ignoreUrls(new String[]{
                        "/",
                        "/passport/login",
                        "/passport/logout",
                        "/passport/valid",
                        "/auth/valid",
                        "/document/view",
                        "/comment/view",
                        "/share/verifyAuthCode",
                        "/share/judgeAuthCode"
                })
                .ignorePreUrls(new String[]{
                        "/apis/list",
                        "/apis/detail",
                        "/file",
                        "/plugin",
                        "/authConfig",
                        "/app"
                })
                .get();
    }
}
