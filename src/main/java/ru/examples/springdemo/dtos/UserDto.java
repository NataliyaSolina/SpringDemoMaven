package ru.examples.springdemo.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Schema(description = "Уникальный логин пользователя", example = "Nata", accessMode = Schema.AccessMode.READ_WRITE)
    private String login;

    @Schema(description = "Пароль пользователя (храниться в зашифрованном виде)", example = "Spring3", accessMode = Schema.AccessMode.READ_WRITE)
    private String password;

    @Schema(description = "Является администратором", example = "Spring3", accessMode = Schema.AccessMode.READ_ONLY)
    private Boolean isAdmin;
}
