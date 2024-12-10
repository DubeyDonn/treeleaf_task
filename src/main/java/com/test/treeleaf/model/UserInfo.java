package com.test.treeleaf.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.test.treeleaf.model.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String password;

    @OneToMany(mappedBy = "createdByUser")
    @JsonManagedReference(value = "user-blog")
    private List<Blog> blogs;

    @OneToMany(mappedBy = "modifiedByUser")
    @JsonManagedReference(value = "user-modified-blog")
    private List<Blog> modifiedBlogs;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "user-comment")
    private List<Comment> comments;
}
