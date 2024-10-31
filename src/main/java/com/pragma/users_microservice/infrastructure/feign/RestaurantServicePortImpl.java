package com.pragma.users_microservice.infrastructure.feign;

import com.pragma.users_microservice.application.dto.request.GetRestaurantRequest;
import com.pragma.users_microservice.domain.api.IRestaurantServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RestaurantServicePortImpl implements IRestaurantServicePort {
    private final IRestaurantFeign restaurantFeign;

    @Override
    public boolean getRestaurantById(Long restaurantId, Long ownerId) {
        GetRestaurantRequest restaurantRequest = restaurantFeign.getRestaurantById(restaurantId, ownerId);
        return restaurantRequest.isRestaurantExistence();
    }
}
