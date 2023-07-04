package com.example.oxygen.viewer;

import com.example.oxygen.storage.ItemStable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductControllerStable {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String url = "spring.datasource.url";
    private String username = "spring.datasource.username";
    private String password = "spring.datasource.password";
    @GetMapping("/get-items")
    public List<ItemStable> getData() {
        String sql = "SELECT * FROM items";
        List<ItemStable> items = jdbcTemplate.query(sql, (rs, rowNum) -> {
            ItemStable item = new ItemStable();
            item.setId(rs.getInt("id"));
            item.setName(rs.getString("name"));
            item.setSize(rs.getString("size"));
            item.setWeight(rs.getFloat("weight"));
            item.setYear(rs.getInt("year"));
            item.setDescription(rs.getString("description"));
            item.setPrice(rs.getFloat("price"));
            item.setPhotoUrl("photoUrl");

            return item;
        });

        return items;
    }

    @PostMapping("/set-items")
    public String setUser(@RequestBody @Validated ItemStable postData) {
        String name = postData.getName();
        String size = postData.getSize();
        Float weight = postData.getWeight();
        Integer year = postData.getYear();
        String description = postData.getDescription();
        Float price = postData.getPrice();
        String photoUrl = postData.getPhotoUrl();

        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            String sql = "INSERT INTO items (name, size, weight, year, description, price, photoUrl) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, size);
            statement.setFloat(3, weight);
            statement.setInt(4, year);
            statement.setString(5, description);
            statement.setFloat(6, price);
            statement.setString(7, photoUrl);
            statement.executeUpdate(); // Выполнение запроса на добавление записи
            System.out.println("Данные успешно записаны в таблицу MySQL.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Запрос успешно обработан";
    }
}

