package xyz.egor_pletnev.todo_list;

import org.mapstruct.Mapper;
import xyz.egor_pletnev.todo_list.dto.TaskResponse;
import xyz.egor_pletnev.todo_list.models.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskResponse taskToTaskResponseDTO(Task task);
}
