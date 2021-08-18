package com.example.adminarabmall.user;

public class User {

    private String name;
    private String phone;
    private String email;
    private String img;
    private String userId;

    public User() {
    }

    public User(String name, String phone, String email, String img, String userId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.img = img;
        this.userId = userId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
