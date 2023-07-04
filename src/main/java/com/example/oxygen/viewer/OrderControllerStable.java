package com.example.oxygen.viewer;
import com.example.oxygen.storage.ItemStable;
import com.example.oxygen.storage.OrderItemStable;
import com.example.oxygen.storage.OrderStable;
import com.example.oxygen.storage.UserStable;
import com.example.oxygen.viewer.UserControllerStable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("/api")
public class OrderControllerStable {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String url = "spring.datasource.url";
    private String username = "spring.datasource.username";
    private String password = "spring.datasource.password";


    @GetMapping("/get-orders")
    public List<OrderStable> getOrders() {
        String sql = "SELECT * FROM orders";
        List<OrderStable> orders = jdbcTemplate.query(sql, (rs, rowNum) -> {
            OrderStable order = new OrderStable();
            order.setId(rs.getInt("id"));
            order.setUserId(rs.getInt("user_id"));
            List<UserStable> userDetails = getUserInfo(order.getUserId());
            order.setUserDetails(userDetails);


            order.setTotalCost(rs.getFloat("total_cost"));
            order.setUserComment(rs.getString("user_comment"));
            order.setCreatedAt(rs.getString("created_at"));

            List<OrderItemStable> orderItems = getOrderItems(order.getId());
            order.setOrderItems(orderItems);

            return order;

        });

        return orders;

    }

    private List<UserStable> getUserInfo(int userId) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<UserStable> userDetails = jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            UserStable user = new UserStable();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setPhone(rs.getString("phone"));
            user.setEmail(rs.getString("email"));

            return user;
        });

        return userDetails;
    }

    private List<OrderItemStable> getOrderItems(int orderId) {
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        List<OrderItemStable> orderItems = jdbcTemplate.query(sql, new Object[]{orderId}, (rs, rowNum) -> {
            OrderItemStable orderItem = new OrderItemStable();
            orderItem.setId(rs.getInt("id"));
            orderItem.setOrderId(rs.getInt("order_id"));
            orderItem.setItemId(rs.getInt("item_id"));
            orderItem.setQuantity(rs.getInt("quantity"));

            return orderItem;
        });

        return orderItems;
    }
    @PostMapping("/set-order")
    public String setOrder(@RequestBody UserStable userData, OrderStable orderData, OrderItemStable orderItems) {
    //Integer userId = orderData.getUserId();
    //user details
    String name = userData.getName();
    String phone = userData.getPhone();
    String email = userData.getEmail();

    Float totalCost = orderData.getTotalCost();
    String userComment = orderData.getUserComment();
    //order details
    Integer id = orderItems.getId();
    Integer orderId = orderItems.getOrderId();
    Integer itemId = orderItems.getItemId();
    Integer quantity = orderItems.getQuantity();

        try {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
//            connection.setAutoCommit(false);

        String sqlInsertUser = "INSERT INTO users (name, phone, email) VALUES (?, ?, ?)";
        PreparedStatement insertUser = connection.prepareStatement(sqlInsertUser);
        insertUser.setString(1, name);
        insertUser.setString(2, phone);
        insertUser.setString(3, email);
        insertUser.executeUpdate();

        String sqlGetLastUserId = "SET @last_user_id = LAST_INSERT_ID()";
        PreparedStatement getLastUserId = connection.prepareStatement(sqlGetLastUserId);
        getLastUserId.executeUpdate();

        String sqlInsertOrder = "INSERT INTO ORDERS (user_id, total_cost, user_comment) VALUES (@last_user_id, ?, ?)";
        PreparedStatement insertOrder = connection.prepareStatement(sqlInsertOrder);
        insertOrder.setFloat(1, totalCost);
        insertOrder.setString(2, userComment);
        insertOrder.executeUpdate();

        String sqlGetLastOrderId = "SELECT @order_id := LAST_INSERT_ID()";
        PreparedStatement getLastOrderId = connection.prepareStatement(sqlGetLastOrderId);
        getLastOrderId.executeUpdate();

        List<OrderItemStable> items = orderData.getOrderItems();
        String sqlInsertOrderDetails = "INSERT INTO order_items (order_id, item_id, quantity) VALUES ";
        for (OrderItemStable item : items) {
            int idItem = item.getItemId();
            int count = item.getQuantity();
            sqlInsertOrderDetails += "(${orderId}, ${idItem}, ${count}), ";
        }
//        (@last_order_id, ?, ?), (@last_order_id, ?, ?), (@last_order_id, ?, ?)";
        PreparedStatement insertOrderDetails = connection.prepareStatement(sqlInsertOrderDetails);
        insertOrderDetails.setInt(1, itemId);
        insertOrderDetails.setInt(2, quantity);

        insertOrderDetails.executeUpdate();
//            connection.setAutoCommit(true);
//            connection.commit();
//            connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

        return "Запрос успешно обработан";
}


//    private void saveOrderItem(OrderItemStable orderItem, Integer orderId) {
//        String sql = "INSERT INTO order_items (order_id, item_id, quantity) VALUES (?, ?, ?)";
//        jdbcTemplate.update(sql, orderItem.getOrderId(), orderItem.getItemId(), orderItem.getQuantity());
//    }

}














/*
public String setOrder(@RequestBody OrderStable postData) {
        try {
            UserControllerStable userController = new UserControllerStable();
            List<UserStable> userDetails = postData.getUserDetails();
            for (UserStable user : userDetails) {
                userController.setUser(user);
            }

            // Сохранение заказа
            int orderId = saveOrder(postData);

            // Сохранение элементов заказа


            // Возврат успешного сообщения
            return "Запрос успешно обработан";
        } catch (Exception e) {
            // Обработка ошибок
            e.printStackTrace();
            return "Ошибка при обработке запроса";
        }
    }

//    @PostMapping("/set-order")
    private Integer saveOrder(OrderStable order) {
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            String sql = "INSERT INTO orders (user_id, total_cost, user_comment) VALUES (?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connect -> {
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, order.getUserId());
                preparedStatement.setFloat(2, order.getTotalCost());
                preparedStatement.setString(3, order.getUserComment());
                return preparedStatement;
            }, keyHolder);


            return keyHolder.getKey().intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // Метод для сохранения элемента заказа
    public void saveOrderItem(int orderId, int itemId, int quantity) {
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            String sql = "INSERT INTO order_items (order_id, item_id, quantity) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, orderId, itemId, quantity);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



 */




















































        /*
        //Integer userId = orderData.getUserId();
        //user details
            String name = userData.getName();
            String phone = userData.getPhone();
            String email = userData.getEmail();

        Float totalCost = orderData.getTotalCost();
        String userComment = orderData.getUserComment();
        //order details
            Integer id = orderItems.getId();
            Integer orderId = orderItems.getOrderId();
            Integer itemId = orderItems.getItemId();
            Integer quantity = orderItems.getQuantity();

        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
//            connection.setAutoCommit(false);

            String sqlInsertUser = "INSERT INTO users (name, phone, email) VALUES (?, ?, ?)";
            PreparedStatement insertUser = connection.prepareStatement(sqlInsertUser);
            insertUser.setString(1, name);
            insertUser.setString(2, phone);
            insertUser.setString(3, email);
            insertUser.executeUpdate();

            String sqlGetLastUserId = "SET @last_user_id = LAST_INSERT_ID()";
            PreparedStatement getLastUserId = connection.prepareStatement(sqlGetLastUserId);
            getLastUserId.executeUpdate();

            String sqlInsertOrder = "INSERT INTO ORDERS (user_id, total_cost, user_comment) VALUES (@last_user_id, ?, ?)";
            PreparedStatement insertOrder = connection.prepareStatement(sqlInsertOrder);
            insertOrder.setFloat(1, totalCost);
            insertOrder.setString(2, userComment);
            insertOrder.executeUpdate();

            String sqlGetLastOrderId = "SELECT @order_id := LAST_INSERT_ID()";
            PreparedStatement getLastOrderId = connection.prepareStatement(sqlGetLastOrderId);
            getLastOrderId.executeUpdate();

            String sqlInsertOrderDetails = "INSERT INTO order_items (order_id, item_id, quantity) VALUES (@last_order_id, ?, ?), (@last_order_id, ?, ?), (@last_order_id, ?, ?)";
            PreparedStatement insertOrderDetails = connection.prepareStatement(sqlInsertOrderDetails);
            insertOrderDetails.setInt(1, itemId);
            insertOrderDetails.setInt(2, quantity);

            insertOrderDetails.executeUpdate();
//            connection.setAutoCommit(true);
//            connection.commit();
//            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Запрос успешно обработан";
    }

         */


/*
            List<UserStable> userDetails = {
                    String name = postData.getName(),
                    String phone = postData.getPhone(),
                    String email = postData.getEmail()
            };


            connection.setAutoCommit(false);

            String sqlInsertOrder = "INSERT INTO ORDERS ( ) VALUES ( )";
            PreparedStatement insertOrder = connection.prepareStatement(sqlInsertOrder);
            insertOrder.executeUpdate();

            String sqlInsertOrderDetails = "SELECT @order_id := LAST_INSERT_ID()";
            PreparedStatement insertOrderDetails = connection.prepareStatement(sqlInsertOrderDetails);
            insertOrderDetails.executeUpdate();

            String sqlGetLastOrderId = "INSERT INTO order_items ( ) VALUES ( )";
            PreparedStatement getLastOrderId = connection.prepareStatement(sqlGetLastOrderId);
            getLastOrderId.executeUpdate();

            String sqlInsertUser = "INSERT INTO users (name, phone, email) VALUES (?, ?, ?)";
            PreparedStatement insertUser = connection.prepareStatement(sqlInsertUser);
            insertUser.setString(1, name);
            insertUser.setString(2, phone);
            insertUser.setString(3, email);
            insertUser.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
 */

