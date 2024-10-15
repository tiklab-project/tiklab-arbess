package io.tiklab.arbess.ws.config;

import io.tiklab.arbess.ws.server.SocketServerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
public class MatflowWebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler(), "/arbess/handler")
                .addInterceptors()
                .setAllowedOrigins("*");
    }

    @Bean
    public SocketServerHandler socketHandler() {
        return new SocketServerHandler();
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(5120 * 5120); // 设置文本消息缓冲区大小
        container.setMaxBinaryMessageBufferSize(5120 * 5120); // 设置二进制消息缓冲区大小
        return container;
    }


}

