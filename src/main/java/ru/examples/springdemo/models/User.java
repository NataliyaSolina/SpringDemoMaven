package ru.examples.springdemo.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;

    @Schema(description = "Уникальный логин пользователя", example = "Nata", accessMode = Schema.AccessMode.READ_WRITE)
    private String login;

    @Schema(description = "Уникальный пароль пользователя (храниться в зашифрованном виде)", example = "Spring3", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;
}

