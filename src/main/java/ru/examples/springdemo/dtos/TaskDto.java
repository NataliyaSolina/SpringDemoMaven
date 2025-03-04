package ru.examples.springdemo.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import ru.examples.springdemo.models.User;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TaskDto {

    @Schema(description = "Уникальный идентификатор задачи", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Дата (дедлайн) задачи", example = "2025-02-12", accessMode = Schema.AccessMode.READ_WRITE)
    private LocalDate date;

    @Schema(description = "Описание задачи", example = "Закончить курс", accessMode = Schema.AccessMode.READ_WRITE)
    private String description;

    @Schema(description = "Логин", example = "Nata", accessMode = Schema.AccessMode.READ_ONLY)
    private String userLogin;

    @Schema(description = "Отметка выполнения задачи", example = "false", accessMode = Schema.AccessMode.READ_ONLY)
    private boolean done;
}
