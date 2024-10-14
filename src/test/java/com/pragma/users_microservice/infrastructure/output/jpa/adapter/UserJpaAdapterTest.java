package com.pragma.users_microservice.infrastructure.output.jpa.adapter;

import com.pragma.users_microservice.domain.model.Role;
import com.pragma.users_microservice.domain.model.User;
import com.pragma.users_microservice.domain.exception.UserNotFoundException;
import com.pragma.users_microservice.infrastructure.output.jpa.entity.RoleEntity;
import com.pragma.users_microservice.infrastructure.output.jpa.entity.UserEntity;
import com.pragma.users_microservice.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.pragma.users_microservice.infrastructure.output.jpa.repository.IUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserJpaAdapterTest {
    @Mock
    private IUserEntityMapper userEntityMapper;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserJpaAdapter userJpaAdapter;

    @Test
    @DisplayName("Validation that it returns user when found by id")
    void getUserById() {
        Long ownerId = 1L;
        UserEntity userEntity = new UserEntity(ownerId, "Melissa", "Henao",
                "1004738846", "+573205898802", LocalDate.parse("2001-05-19"),
                "melissahenao19@gmail.com", "owner123", new RoleEntity(2L, null, null));
        User user = new User(ownerId, "Melissa", "Henao", "1004738846",
                "+573205898802", LocalDate.parse("2001-05-19"),
                "melissahenao19@gmail.com", "owner123",
                new Role(2L, "Owner", "The owner role manages dishes, creates employee accounts, and tracks order times."));

        Mockito.when(userRepository.findById(ownerId)).thenReturn(Optional.of(userEntity));
        Mockito.when(userEntityMapper.entityToUser(userEntity)).thenReturn(user);

        User result = userJpaAdapter.getUserById(ownerId);

        assertNotNull(result);
        assertEquals(user, result);
        Mockito.verify(userRepository, Mockito.times(1)).findById(ownerId);
        Mockito.verify(userEntityMapper, Mockito.times(1)).entityToUser(userEntity);
    }

    @Test
    @DisplayName("Validation exception when no user found by the id provided")
    void getUserByIdShouldThrowValidationExceptionWhenUserNotFound() {
        Long ownerId = 1L;

        Mockito.when(userRepository.findById(ownerId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userJpaAdapter.getUserById(ownerId);
        });

        assertEquals(String.format(ExceptionConstants.USER_NOT_FOUND_MESSAGE, ownerId), exception.getMessage());

        Mockito.verify(userRepository, Mockito.times(1)).findById(ownerId);
        Mockito.verify(userEntityMapper, Mockito.never()).entityToUser(Mockito.any());
    }
}