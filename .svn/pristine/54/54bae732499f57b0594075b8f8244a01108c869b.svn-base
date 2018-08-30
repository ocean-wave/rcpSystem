package cn.com.cdboost.collect.websocket;

import cn.com.cdboost.collect.handler.SpringWebSocketHandler;
import cn.com.cdboost.collect.interceptor.SpringWebSocketHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class SpringWebSocket extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/websocket").setAllowedOrigins("*").addInterceptors(webSocketInterceptor());
    }

    @Bean
    public TextWebSocketHandler webSocketHandler() {
        return new SpringWebSocketHandler();
    }

    @Bean
    public SpringWebSocketHandlerInterceptor webSocketInterceptor() {
        return new SpringWebSocketHandlerInterceptor();
    }
}