package com.mykyta.stockapi.rest;

import com.mykyta.stockapi.entity.User;
import com.mykyta.stockapi.exception.ResourceNotFoundException;
import com.mykyta.stockapi.repository.UserRepository;
import com.mykyta.stockapi.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    public User getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @GetMapping("/me/id")
    public Long getCurrentUserId(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userPrincipal.getId();
    }

}