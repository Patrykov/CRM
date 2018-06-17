package first.project.repository;


import first.project.entity.Task;
import first.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    //pobranie wszystkich zadan danego uzytkownika
    List<Task> findAllByUserEquals(User user);
}
