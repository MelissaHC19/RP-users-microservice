package com.pragma.users_microservice.infrastructure.feign;

import com.pragma.users_microservice.application.dto.request.GetRestaurantRequest;
import com.pragma.users_microservice.infrastructure.constants.FeignConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = FeignConstants.FEIGN_CLIENT_NAME, url = FeignConstants.FEIGN_CLIENT_URL)
public interface IRestaurantFeign {
    @GetMapping("/{id}/{ownerId}")
    GetRestaurantRequest getRestaurantById(@PathVariable Long id, @PathVariable Long ownerId);
}
