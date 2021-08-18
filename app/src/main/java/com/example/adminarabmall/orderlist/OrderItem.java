package com.example.adminarabmall.orderlist;

import com.example.adminarabmall.item.Item;

public class OrderItem {
    private Item item;
    private double count;
    private String docId;

    public OrderItem(Item item, double count, String docId) {
        this.item = item;
        this.count = count;
        this.docId = docId;
    }

    public OrderItem() {
    }



    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
