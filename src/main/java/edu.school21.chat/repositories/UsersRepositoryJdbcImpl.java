package edu.school21.chat.repositories;

import edu.school21.chat.models.User;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    DataSource dataSource;
    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll(int page, int size){
        List<User> users = new ArrayList<>();
        try(Connection con = dataSource.getConnection();
            Statement stmt = con.createStatement()){
            String query = "SELECT DISTINCT us.id, login, password, cr.id \"ownerRoomId\", cr.name \"ownerRoomName\",  cr.owner \"ownerId\",  ms.room \"msRoom\", cr2.name \"msName\", cr2.owner \"msOwner\"   FROM users us\n"+
            "RIGHT JOIN\n"+
            "chatrooms as cr\n"+ "ON\n"+
            "us.id=cr.owner\n"+
            "RIGHT JOIN\n"+
            "messages as ms\n"+ "ON\n"+
            "us.id=ms.sender\n"+
            "INNER JOIN\n"+
            "chatrooms as cr2\n"+ "ON\n"+
            "ms.room=cr2.id\n"+
            "WHERE us.id > "+(page)*(size)+" AND us.id <= "+(((page+1)*size)+1)+"\n"+
            "ORDER BY us.id\n";
            ResultSet rs = stmt.executeQuery(query);
            int tempId = 0L;
            User user = null;
            ArrayList<Chatroom> roomOwnerList;
            ArrayList<Chatroom> roomUserList;
            while(rs.next()){


                Long id = rs.getLong(1);
                String login = rs.getString(2);
                String password = rs.getString(3);
                Long ownerRoomId = rs.getLong(4);
                String ownerRoomName = rs.getString(5);
                Long ownerId = rs.getLong(6);
                Long msRoom = rs.getLong(7);
                String msName = rs.getString(8);
                Long msOwner = rs.getLong(9);


                if(!tempId.equals(id)){
                    if (tempId != 0){
                    users.add(user);
                    }
                    tempId = id;
                    roomOwnerList = new ArrayList<>();
                    roomUserList = new ArrayList<>();
                    user = new User(id, login, password,roomOwnerList,roomUserList);
                }

                if(user != null) {
                    user.addOwnerRoom(new Chatroom(ownerRoomId, ownerRoomName, new User(ownerId, login, password, new ArrayList<Chatroom>(), new ArrayList<Chatroom>()), new ArrayList<Message>()));
                    user.addUserRoom(new Chatroom(msRoom, msName, new User(msOwner, login, password, new ArrayList<Chatroom>(), new ArrayList<Chatroom>()), new ArrayList<Message>()));
                }

            }

        }catch (SQLException e){
            System.out.println(e.getSQLState());
        }

        return users;
    }

}