package xyz.egor_pletnev.todo_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.egor_pletnev.todo_list.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
