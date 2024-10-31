package com.pragma.users_microservice.domain.model;

public class Employee {
    private Long id;
    private Long userId;
    private Long restaurantId;

    public Employee(Long id, Long userId, Long restaurantId) {
        this.id = id;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
