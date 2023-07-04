package com.example.oxygen.storage;

import com.example.oxygen.viewer.UserControllerStable;

import java.util.List;

public class OrderStable {
    private int id;

    private int userId;

    private List<UserStable> userDetails;
    private float totalCost;
    private String userComment;
    private String createdAt;
    private List<OrderItemStable> orderItems;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public List<UserStable> getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(List<UserStable> userDetails) {
        this.userDetails = userDetails;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItemStable> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemStable> orderItems) {
        this.orderItems = orderItems;
    }

}
