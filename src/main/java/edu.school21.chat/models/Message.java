package edu.school21.chat.models;
import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long id;
    private User sender;
    private Chatroom room;
    private String message;
    private LocalDateTime dateTime;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public Message(Long id, User sender, Chatroom room, String message,
                   LocalDateTime dateTime) {
        this.id = id;
        this.sender = sender;
        this.room = room;
        this.message = message;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Chatroom getRoom() {
        return room;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        String dateTime;
        if (this.dateTime == null) {
            dateTime = "null";
        } else {
            dateTime = this.dateTime.format(FORMATTER);
        }
        return "id: "+ id +"\n"+"Sender: " + sender + "\n" +"Room: " + room+ "\n" +
                "Message: " + message + "\n" + "Date = " + dateTime;
    }
}