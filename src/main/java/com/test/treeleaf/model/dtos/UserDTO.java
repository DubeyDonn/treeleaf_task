package com.test.treeleaf.model.dtos;

import java.util.List;

import com.test.treeleaf.model.User;
import com.test.treeleaf.model.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String username;
    private Role role;

    public static UserDTO from(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getRole());
    }

    public static List<UserDTO> from(List<User> users) {
        if (users == null) {
            return List.of();
        }
        return users.stream().map(UserDTO::from).toList();
    }
}
