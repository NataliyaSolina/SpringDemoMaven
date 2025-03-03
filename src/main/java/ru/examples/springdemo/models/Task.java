package ru.examples.springdemo.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор задачи", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Дата (дедлайн) задачи", example = "2025-02-12", accessMode = Schema.AccessMode.READ_WRITE)
    private LocalDate date;

    @Schema(description = "Описание задачи", example = "Закончить курс", accessMode = Schema.AccessMode.READ_WRITE)
    private String description;

    @Schema(description = "Логин", example = "Nata", accessMode = Schema.AccessMode.READ_ONLY)
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    @Schema(description = "Отметка выполнения задачи", example = "false", accessMode = Schema.AccessMode.READ_ONLY)
    private boolean done;
}
