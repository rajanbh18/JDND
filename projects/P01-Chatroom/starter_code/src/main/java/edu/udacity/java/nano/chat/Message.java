package edu.udacity.java.nano.chat;

import javax.validation.constraints.NotBlank;

/**
 * WebSocket message model
 */
public class Message {

    @NotBlank(message = "can not be empty")
    private String userName;
    private String chatMessage;

    private int onlineCount;
    private MessageType messageType;


    public enum MessageType{
        CHAT,JOIN,LEAVE
    }
    public Message(){}


    public Message(@NotBlank(message = "can not be empty") String userName, String chatMessage, MessageType messageType) {
        this.userName = userName;
        this.chatMessage = chatMessage;
        this.messageType = messageType;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
