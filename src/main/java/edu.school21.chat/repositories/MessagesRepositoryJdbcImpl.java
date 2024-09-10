package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ArrayList;

public class MessagesRepositoryJdbcImpl implements MessagesRepository{
    DataSource ds;
    public MessagesRepositoryJdbcImpl(DataSource dataSource){
        this.ds = dataSource;
    }

    @Override
    public void save(Message message){

        try(Connection con = ds.getConnection();
            Statement stmt = con.createStatement()){
                if (getUser(message.getSender().getId())==null){
                    throw new NotSavedSubEntityException("User with id "+message.getSender().getId()+" dont exist");
                }
                if (getChatroom(message.getRoom().getId()) == null){
                throw new NotSavedSubEntityException("Chat with id "+message.getSender().getId()+" dont exist");
                }

                String query = "INSERT INTO messages(sender, room, message, date_time) VALUES (" + message.getSender().getId()+ ", " + message.getRoom().getId() + ", '" + message.getMessage() + "', '" + message.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "')";

                if(stmt.executeUpdate(query)==0){
                    throw new NotSavedSubEntityException("User not saved");
                }
                else {
                    ResultSet rs = stmt.executeQuery("SELECT MAX(id) FROM messages");
                    if(rs.next()){
                        message.setId(rs.getLong(1));
                    }

                }
        }
        catch(SQLException e){
            System.out.println(e.getSQLState());
        } catch (NotSavedSubEntityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Message message) throws NotSavedSubEntityException {
        if (message == null || message.getId() == null) {
            throw new NotSavedSubEntityException("No message entered");
        }
        if (message.getSender() == null || message.getRoom() == null) {
            throw new NotSavedSubEntityException("User or Room not entered");
        }
        try(Connection con = ds.getConnection();
            Statement stmt = con.createStatement()){
            LocalDateTime tempLDT = message.getDateTime();
            String ldtUpdate;
            if (tempLDT == null) {
                ldtUpdate = "NULL";
            }
            else {
                ldtUpdate = "'"+ tempLDT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+"'";
            }
            String query = "UPDATE messages SET sender = " + message.getSender().getId() + ", room =" + message.getRoom().getId() + ", message = '" + message.getMessage()+"', date_time = " + ldtUpdate+" WHERE id = " + message.getId();
            if(stmt.executeUpdate(query)==0){
                throw new NotSavedSubEntityException("Message not updated");
            }
        }
        catch(SQLException e){
            System.out.println(e.getSQLState());
        } catch (NotSavedSubEntityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Message> findById(Long id)  {
        try{
        Connection con = ds.getConnection();
        String query = "select * from messages where id = " + id;
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);
        User sender;
        Chatroom room;
        String message;
        LocalDateTime dateTime;
        if (rs.next()) {
           sender = getUser(rs.getLong(2));
           room = getChatroom(rs.getLong(3));
           message = rs.getString(4);
           dateTime = rs.getTimestamp(5).toLocalDateTime();
            return Optional.of(new Message(id, sender, room, message, dateTime));
        } else {
            System.out.println("No messages found");
            return Optional.empty();
        }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public User getUser(Long numberOfUser) throws SQLException {
        Connection con = ds.getConnection();
        String query = "select * from users where id = " + numberOfUser;
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);
        String login;
        String password;
        if (!rs.next()) {
            System.out.println("No user found");
            return null;
        }
        login = rs.getString(2);
        password = rs.getString(3);
        return new User(numberOfUser, login, password, new ArrayList<>(), new ArrayList<>());
    }

    public Chatroom getChatroom(Long numberOfChatroom) throws SQLException {
        Connection con = ds.getConnection();
        String query = "select * from chatrooms where id = " + numberOfChatroom;
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);
        String name;
        User owner;
        if (!rs.next()) {
            System.out.println("No chatroom found");
            return null;
        }
        name = rs.getString(2);
        owner = getUser(rs.getLong(3));
        return new Chatroom(numberOfChatroom, name, owner, new ArrayList<>());
    }
}