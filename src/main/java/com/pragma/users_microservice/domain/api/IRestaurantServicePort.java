package com.pragma.users_microservice.domain.api;

public interface IRestaurantServicePort {
    boolean getRestaurantById(Long restaurantId, Long ownerId);
}
