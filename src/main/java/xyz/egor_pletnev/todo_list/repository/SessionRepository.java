package xyz.egor_pletnev.todo_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.egor_pletnev.todo_list.models.Session;
import xyz.egor_pletnev.todo_list.models.User;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {

    Optional<Session> findBySessionToken(String sessionToken);

    void deleteBySessionToken(String sessionToken);

    void deleteByUser(User user);
}