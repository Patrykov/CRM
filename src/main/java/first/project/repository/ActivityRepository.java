package first.project.repository;

import first.project.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    //wyswietlenie ostatnich aktywnosci wszsytkich uzytkownikow
    @Query(value = "SELECT * FROM activity ORDER BY created DESC LIMIT 20",
            nativeQuery = true)
    List<Activity> findbycreatedlimit();


    //wyswietlenie ostatnich aktywnosci danego uzytkownika
    @Query(value = "SELECT * FROM activity WHERE USER_Id = ? ORDER BY created DESC LIMIT 20",
            nativeQuery = true)
    List<Activity> findAllByUserId(long id);
}
