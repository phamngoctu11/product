package com.example.workflow.delegate;

import com.example.workflow.entity.Cart;
import com.example.workflow.entity.User;
import com.example.workflow.nume.Role;
import com.example.workflow.repository.CartRepository;
import com.example.workflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;
@Component("saveUserDelegate")
@RequiredArgsConstructor
@Transactional
public class SaveUserDelegate implements JavaDelegate {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String username = (String) execution.getVariable("username");
        String password = (String) execution.getVariable("password");
        Set<String> rolesInput = (Set<String>) execution.getVariable("roles");
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        Set<Role> roles = new HashSet<>();
        if (rolesInput == null || rolesInput.isEmpty()) {
            roles.add(Role.USER);
        } else {
            rolesInput.forEach(roleStr -> {
                try {
                    roles.add(Role.valueOf(roleStr.toUpperCase()));
                } catch (IllegalArgumentException ignored) {}
            });
        }
        user.setRoles(roles);
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);
        cartRepository.save(cart);
        userRepository.save(user);
        System.out.println(">>> Camunda: Created User & Cart for: " + username);
    }
}
