package com.example.adminarabmall.orderlist;

public class Status {
    private String docId;
    private String name;
    private String image;

    public Status() {
    }

    public Status(String docId, String name, String image) {
        this.docId = docId;
        this.name = name;
        this.image = image;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
