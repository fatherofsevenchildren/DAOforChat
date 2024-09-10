package edu.school21.chat.repositories;

import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.File;

public class loadDate {
    DataSource dataSource;
    public loadDate(@NotNull DataSource dataSource) throws SQLException, FileNotFoundException {
        this.dataSource=dataSource;
        loadSql("C:\\Users\\FROST\\Desktop\\JAVA\\Java_Bootcamp.Day05-1\\src\\ex04\\Chat-folder\\src\\main\\resources\\schema.sql");
        loadSql("C:\\Users\\FROST\\Desktop\\JAVA\\Java_Bootcamp.Day05-1\\src\\ex04\\Chat-folder\\src\\main\\resources\\data.sql");

    }

    private void loadSql(String fileName) throws SQLException, FileNotFoundException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        Scanner scanner = new Scanner(new File(fileName));
        scanner.useDelimiter(";");
        while (scanner.hasNext()) {
            statement.executeUpdate(scanner.next());
//            System.out.println(scanner.next());
        }
        scanner.close();
    }
}