package xyz.egor_pletnev.todo_list.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private Integer priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Column(name = "is_outdoor")
    private Boolean isOutdoor;

    @Column(name = "required_weather")
    private String requiredWeather;

    @Column(name = "required_min_temp")
    private Integer minTemp;

    @Column(name = "required_max_temp")
    private Integer maxTemp;

    @Column(name = "forecast_weather")
    private String forecastWeather;

    @Column(name = "forecast_temp")
    private Integer forecastTemp;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}