package xyz.egor_pletnev.todo_list.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.egor_pletnev.todo_list.dto.*;
import xyz.egor_pletnev.todo_list.models.TaskStatus;
import xyz.egor_pletnev.todo_list.service.TaskService;
import xyz.egor_pletnev.todo_list.service.WeatherService;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final WeatherService weatherService;

    @Override
    public TaskCreateResponse createTask(TaskCreateRequest request) {
        // TODO: реализовать запись в бд
        if (request.isOutdoor()) { // TODO: сделать пакет manager, создать класс WeatherManager, добавить получение погоды
            WeatherResponse weatherResponse = weatherService.getWeather(request.latitude(), request.longitude(), request.dueDate());
            return new TaskCreateResponse(
                    request.title(),
                    request.description(),
                    TaskStatus.NEW,
                    LocalDate.now(),
                    true,
                    weatherResponse.condition(),
                    weatherResponse.temperature(),
                    request.minTemp(),
                    request.maxTemp(),
                    request.requiredWeather()
                    );
        }
        return new TaskCreateResponse(
                request.title(),
                request.description(),
                TaskStatus.NEW,
                LocalDate.now(),
                false,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public TaskEditResponse editTask(TaskEditRequest request) {
        // TODO: получение текущих данных задачи по request.id() изменение данных и редактирование задачи в бд
        return new TaskEditResponse(
                request.title(),
                request.description(),
                request.isOutdoor(),
                request.latitude(),
                request.longitude(),
                request.minTemp(),
                request.maxTemp(),
                request.requiredWeather(),
                request.dueDate()
        );
    }

    @Override
    public TaskDeleteResponse deleteTasks(TaskDeleteRequest request) {
        // TODO: удаление по ids
        return new TaskDeleteResponse(
                request.ids()
        );
    }

    @Override
    public GetTasksResponse getTasks(TaskFilterRequest request) {
        // TODO: фильтрация задач
        return new GetTasksResponse();
    }
}
