package com.cndinuo.websocket;

import com.cndinuo.common.SessionUtils;
import com.cndinuo.domain.UserManager;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;


/**
 * Socket 建立连接（握手）和断开
 * @author dgb
 */
public class CustomHandShakeInterceptor implements HandshakeInterceptor {

    private static final Logger log = LoggerFactory.getLogger("websocket");

    @Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        UserManager user = (UserManager) SecurityUtils.getSubject().getSession().getAttribute(SessionUtils.SESSION_USER);
        if(user != null){
            log.info("用户【"+user.getRealName()+"】已经建立websocket连接");
            attributes.put("uid", user.getId());
        }else{
            return false;
        }

		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
	}

}
