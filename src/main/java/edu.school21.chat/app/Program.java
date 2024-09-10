package edu.school21.chat.app;

import java.io.FileNotFoundException;
import java.sql.*;

import edu.school21.chat.models.User;
import edu.school21.chat.repositories.DBSource;
import edu.school21.chat.repositories.UsersRepository;
import edu.school21.chat.repositories.UsersRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class Program {
    static Connection connection;
    static DataSource dataSource;
    public static void main(String[] args) throws SQLException, FileNotFoundException {
        dataSource = new DBSource().getDataSource();
        try{
            connection = dataSource.getConnection();
            UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource);
            List<User> users = usersRepository.findAll(1,2);
            for (User user : users) {
                System.out.println(user);
            }


        }
         catch (SQLException e){
            e.printStackTrace();
        }
    }}
