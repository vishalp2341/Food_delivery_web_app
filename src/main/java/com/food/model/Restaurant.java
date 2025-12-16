package com.food.model;

public class Restaurant  {
    

    private int restaurantId;
    private String name;
    private String address;
    private long phone;
    private Integer rating;
    private String cuisineType;
    private Boolean isActive;
    private Integer eta;
    private String description; 
    private Integer userId;
    private String imagePath;

    // Default constructor
    public Restaurant() {}

    // Parameterized constructor
    public Restaurant(int restaurantId, String name, String address, long phone, Integer rating, String cuisineType,
                      Boolean isActive, Integer eta, String description, Integer userId, String imagePath) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.rating = rating;
        this.cuisineType = cuisineType;
        this.isActive = isActive;
        this.eta = eta;
        this.description = description;
        this.userId = userId;
        this.imagePath = imagePath;
    }

    // Getters and setters
    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getEta() {
        return eta;
    }

    public void setEta(Integer eta) {
        this.eta = eta;
    }

    public String getDescription() {  // Renamed from getRestaurantCol
        return description;
    }

    public void setDescription(String description) {  // Renamed from setRestaurantCol
        this.description = description;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Restaurant [restaurantId=" + restaurantId + ", name=" + name + ", address=" + address + ", phone=" + phone
                + ", rating=" + rating + ", cuisineType=" + cuisineType + ", isActive=" + isActive + ", eta=" + eta
                + ", description=" + description + ", userId=" + userId + ", imagePath=" + imagePath + "]";
    }
}
