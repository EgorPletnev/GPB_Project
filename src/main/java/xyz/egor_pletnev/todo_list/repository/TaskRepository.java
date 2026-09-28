package xyz.egor_pletnev.todo_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.egor_pletnev.todo_list.models.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> { // TODO: сделать запрос к бд который будет возвращать все task без статуса deleted
    @Query("""
        SELECT t FROM Task t
        ORDER BY
            (CASE WHEN :byStatus = true THEN t.status END) ASC,
            (CASE WHEN :byPriority = true THEN t.priority END) DESC,
            (CASE WHEN :byDate = true THEN t.dueDate END) ASC,
            t.createdAt DESC
    """)
    List<Task> findTasksWithOrder(
            @Param("byStatus") boolean byStatus,
            @Param("byPriority") boolean byPriority,
            @Param("byDate") boolean byDate
    );
}
