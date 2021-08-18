package com.example.adminarabmall.orderlist;

import java.util.List;
public class Order {
    private String docId;
    private String userId;
    private String userName;
    private String userPhone;
    private String userLocation;
    private List<OrderItem> orderItems;
    private String time;
    private String date;
    private String day;
    private double total;
    private String status;
    private int indexStatus;


    public Order() {
    }

    public Order(String docId, String userId, String userName, String userPhone, String userLocation, List<OrderItem> orderItems, String time, String date, String day, double total, String status, int indexStatus) {
        this.docId = docId;
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userLocation = userLocation;
        this.orderItems = orderItems;
        this.time = time;
        this.date = date;
        this.day = day;
        this.total = total;
        this.status = status;
        this.indexStatus = indexStatus;
    }

    public int getIndexStatus() {
        return indexStatus;
    }

    public void setIndexStatus(int indexStatus) {
        this.indexStatus = indexStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}