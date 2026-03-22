package com.project.currency_converter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Column(name = "api_key", unique = true)
    private String apiKey;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<History> history = new ArrayList<>();
}
