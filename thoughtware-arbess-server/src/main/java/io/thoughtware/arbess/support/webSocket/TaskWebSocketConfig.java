// package io.thoughtware.matflow.support.webSocket;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.socket.WebSocketHandler;
// import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
// import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//
// /**
//  * websocket
//  * 的配置信息
//  */
// // @Configuration
// // @EnableWebSocket
// public class TaskWebSocketConfig implements WebSocketConfigurer {
//
//     /**
//      * 注册handle
//      */
//     @Override
//     public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//         registry.addHandler(pipelineHandler(), "/start").addInterceptors().setAllowedOrigins("*");
//     }
//
//     @Bean
//     public WebSocketHandler pipelineHandler(){
//         return new TaskWebSocketService();
//     }
// }
