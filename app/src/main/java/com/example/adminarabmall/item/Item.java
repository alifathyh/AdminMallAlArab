package com.example.adminarabmall.item;

public class Item {

    private String docId;
    private String name;
    private String description;
    private double price;
    private String categoryName;
    private String image;
    private String quantity;
    private String theCondition;
    private int indexQuantity;
    private int indexTheCondition;


    public Item() {
    }

    public Item(String docId, String name, String description, double price, String categoryName, String image, String quantity, String theCondition, int indexQuantity, int indexTheCondition) {
        this.docId = docId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryName = categoryName;
        this.image = image;
        this.quantity = quantity;
        this.theCondition = theCondition;
        this.indexQuantity = indexQuantity;
        this.indexTheCondition = indexTheCondition;
    }

    public int getIndexQuantity() {
        return indexQuantity;
    }

    public void setIndexQuantity(int indexQuantity) {
        this.indexQuantity = indexQuantity;
    }

    public int getIndexTheCondition() {
        return indexTheCondition;
    }

    public void setIndexTheCondition(int indexTheCondition) {
        this.indexTheCondition = indexTheCondition;
    }

    public String getTheCondition() {
        return theCondition;
    }

    public void setTheCondition(String theCondition) {
        this.theCondition = theCondition;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String imageUrl) {
        this.image = imageUrl;
    }
}
