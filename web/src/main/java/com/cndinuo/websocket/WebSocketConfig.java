package com.cndinuo.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * WebScoket 配置处理器
 * @author dgb
 */
@Component
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

	@Resource
	CustomWSHandler handler;

	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(handler, "/ws").addInterceptors(new CustomHandShakeInterceptor());

		registry.addHandler(handler, "/ws/sockjs").addInterceptors(new CustomHandShakeInterceptor()).withSockJS();
	}

}