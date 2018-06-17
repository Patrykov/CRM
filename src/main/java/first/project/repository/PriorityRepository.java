package first.project.repository;

import first.project.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
}
