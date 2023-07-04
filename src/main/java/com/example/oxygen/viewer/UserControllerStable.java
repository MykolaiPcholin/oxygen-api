package com.example.oxygen.viewer;

import com.example.oxygen.storage.UserStable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserControllerStable {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String url = "spring.datasource.url";
    private String username = "spring.datasource.username";
    private String password = "spring.datasource.password";
    @GetMapping("/get-users")
    public List<UserStable> getUser() {
        try {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        String sql = "SELECT * FROM users";
        List<UserStable> users = jdbcTemplate.query(sql, (rs, rowNum) -> {
            UserStable user = new UserStable();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setPhone(rs.getString("phone"));
            user.setEmail(rs.getString("email"));

            return user;
        });
        connection.close();
        return users;
        } catch (SQLException e) {
            System.out.println("Данные таблицы MySQL недоступны.");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    //@RequestMapping("/set-users")
    @PostMapping("/set-users")
    public String setUser(@RequestBody @Validated UserStable postData) {
        String name = postData.getName();
        String phone = postData.getPhone();
        String email = postData.getEmail();

        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            String sql = "INSERT INTO users (name, phone, email) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, email);
            statement.executeUpdate(); // Выполнение запроса на добавление записи
            System.out.println("Данные успешно записаны в таблицу MySQL.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Запрос успешно обработан";
    }
}
