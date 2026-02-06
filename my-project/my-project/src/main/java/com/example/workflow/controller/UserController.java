package com.example.workflow.controller;
import com.example.workflow.dto.UserCreDTO;
import com.example.workflow.dto.UserResDTO;
import com.example.workflow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final UserService userService;
    private final RuntimeService runtimeService;
    @PostMapping()
    public ResponseEntity<String> register(@RequestBody UserCreDTO dto) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("username", dto.getUsername());
        variables.put("password", dto.getPassword());
        variables.put("roles", dto.getRoles());

        runtimeService.startProcessInstanceByKey("CreateUserProcess", variables);

        return ResponseEntity.ok("Quy trình tạo User đã bắt đầu!");
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResDTO>> getAll() {
        List<UserResDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResDTO> getById(@PathVariable Long id) {
        UserResDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResDTO> update(@PathVariable Long id, @RequestBody UserCreDTO request) {
        UserResDTO updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}