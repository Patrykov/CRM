package first.project.repository;

import first.project.entity.Project;
import first.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    //pobranie wszystkich pojetkow i sortowanie
    @Query(value = "SELECT * FROM project ORDER BY created DESC LIMIT 5",
            nativeQuery = true)
    List<Project> findbycreatedlimit();


    //pobranie wszystkich projektow danego uzytkownika
    List<Project> findAllByUsersEquals(User user);


    //pobranie ostatnich 5 projektow danego uzytkownika i sortowanie
    @Query(value = "SELECT * FROM project join project_users on project_users.project_id = project.id\n" +
            "WHERE project_users.users_id = ? ORDER BY created DESC LIMIT 5", nativeQuery = true)
    List<Project> findbyUserscreatedlimit(long id);
}
