package com.ai.translation.entity;

import com.ai.translation.unit.Constant;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "User_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = Constant.REQUIRED_FIELDS)
    @Email(message = Constant.INVALID_EMAIL)
    private String username;

    @NotBlank(message = Constant.REQUIRED_FIELDS)
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Role> roles;



}
