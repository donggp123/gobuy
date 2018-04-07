package com.cndinuo.websocket;

import com.cndinuo.domain.Message;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Socket 处理器
 * @author dgb
 */
@Component
public class CustomWSHandler implements WebSocketHandler {

	private static final Logger log = LoggerFactory.getLogger("websocket");

	public static final Map<Integer, WebSocketSession> userSocketSessionMap;

	static {
		userSocketSessionMap = new HashMap<Integer, WebSocketSession>();
	}

	/**
	 * 建立连接后
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Integer uid = (Integer) session.getAttributes().get("uid");
		if (userSocketSessionMap.get(uid) == null) {
			userSocketSessionMap.put(uid, session);
		}
	}

	/**
	 * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
	 */
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
			if(message.getPayloadLength()==0)return;
			Message msg = new Gson().fromJson(message.getPayload().toString(),Message.class);
			msg.setSendTime(new Date());
			sendMessageToUser(msg.getTo(), msg);
	}

	/**
	 * 消息传输错误处理
	 */
	@Override
	public void handleTransportError(WebSocketSession session,Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		Iterator<Entry<Integer, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// 移除Socket会话
		while (it.hasNext()) {
			Entry<Integer, WebSocketSession> entry = it.next();
			if (entry.getValue().getId().equals(session.getId())) {
				userSocketSessionMap.remove(entry.getKey());
				log.info("websocket移除用户ID:" + entry.getKey());
				break;
			}
		}
	}

	/**
	 * 关闭连接后
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		log.info("用户关闭连接");
		Iterator<Entry<Integer, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// 移除Socket会话
		while (it.hasNext()) {
			Entry<Integer, WebSocketSession> entry = it.next();
			if (entry.getValue().getId().equals(session.getId())) {
				userSocketSessionMap.remove(entry.getKey());
				log.info("用户关闭连接用户ID:" + entry.getKey());
				break;
			}
		}
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 给所有在线用户发送消息
	 * @param message
	 * @throws IOException
	 */
	public void broadcast(final TextMessage message) throws IOException {
		Iterator<Entry<Integer, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// 多线程群发
		while (it.hasNext()) {
			final Entry<Integer, WebSocketSession> entry = it.next();
			if (entry.getValue().isOpen()) {
				// entry.getValue().sendMessage(message);
				new Thread(new Runnable() {

					public void run() {
						try {
							if (entry.getValue().isOpen()) {
								entry.getValue().sendMessage(message);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		}
	}

	/**
	 * 给某个用户发送消息
	 * @param message
	 * @throws IOException
	 */
	public void sendMessageToUser(Integer uid,Message message) {
		TextMessage msg = new TextMessage(new Gson().toJson(message));
		WebSocketSession session = userSocketSessionMap.get(uid);
		try {
			if (session != null && session.isOpen()) {
					session.sendMessage(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
