package xyz.egor_pletnev.todo_list.service;

import xyz.egor_pletnev.todo_list.dto.*;

public interface TaskService {
    TaskCreateResponse createTask(TaskCreateRequest request);
    TaskEditResponse editTask(TaskEditRequest request);
    TaskDeleteResponse deleteTasks(TaskDeleteRequest request);
    GetTasksResponse getTasks(TaskFilterRequest request);
}
