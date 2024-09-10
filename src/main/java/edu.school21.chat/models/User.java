package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private final Long id;
    private String login;
    private String password;
    private List<Chatroom> createdRooms;
    private List<Chatroom> userRooms;

    public User(Long id, String login, String password, List<Chatroom> createdRooms, List<Chatroom> usedRooms) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdRooms = createdRooms;
        this.userRooms = usedRooms;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Chatroom> getCreatedRooms() {
        return createdRooms;
    }

    public void setCreatedRooms(List<Chatroom> createdRooms) {
        this.createdRooms = createdRooms;
    }

    public List<Chatroom> getRooms() {
        return userRooms;
    }

    public void setRooms(List<Chatroom> userRooms) {
        this.userRooms = userRooms;
    }

    public void addOwnerRoom(Chatroom room) {
        boolean found = false;
        for(Chatroom room1 : createdRooms) {
            if (room1.getId().equals(room.getId()))
                {
                    found = true;
                    break;
                }
        }
        if (!found) {
            createdRooms.add(room);
        }
    }

    public void addUserRoom(Chatroom room) {
        boolean found = false;
        for(Chatroom room1 : userRooms) {
            if (room1.getId().equals(room.getId()))
            {
                found = true;
                break;
            }
        }
        if (!found) {
            userRooms.add(room);
        }
    }

    @Override
    
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            User user = (User) o;
            return Objects.equals(id, user.id);
        }
    

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return this.login + ", id = " + this.id +"\n Owned chatrooms:" + this.getCreatedRooms().toString() +"\n Used chatrooms"+ this.getRooms().toString();
    }
}