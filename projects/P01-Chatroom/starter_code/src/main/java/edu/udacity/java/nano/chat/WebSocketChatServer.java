package edu.udacity.java.nano.chat;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import javax.websocket.*;

import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint("/chat")
public class WebSocketChatServer {

    public static final String LEFT_CHAT_ROOM = "left chat room.";
    public static final String JOINED_THE_CHAT_ROOM = "joined the chat room.";
    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    private static void sendMessageToAll(String msg, Message message) {
        onlineSessions.forEach((user, session) -> {
                    session.getAsyncRemote().sendText(msg);
        });
    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session) {
        String usr = session.getQueryString();
        onlineSessions.put(usr, session);
        Gson gson = new Gson();
        Message message = getMessage(usr, JOINED_THE_CHAT_ROOM, Message.MessageType.JOIN);
        sendMessageToAll(gson.toJson(message), message);
    }



    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        //TODO: add send message.

        Gson gson = new Gson();
        Message newMessage = gson.fromJson(jsonStr, Message.class);
        newMessage.setOnlineCount(onlineSessions.size());
        newMessage.setMessageType(Message.MessageType.CHAT);
        sendMessageToAll(gson.toJson(newMessage), newMessage);
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) {
        //TODO: add close connection.
        onlineSessions.remove(session.getQueryString());

        if (onlineSessions.size()>0){
            Gson gson = new Gson();
            Message message = getMessage(session.getQueryString(), LEFT_CHAT_ROOM, Message.MessageType.LEAVE);
            sendMessageToAll(gson.toJson(message), message);
        }
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }


    private Message getMessage(String userName, String chatMessage, Message.MessageType messageType) {
        Message message = new Message(userName, chatMessage, messageType);
        message.setOnlineCount(onlineSessions.size());
        return message;
    }
}
