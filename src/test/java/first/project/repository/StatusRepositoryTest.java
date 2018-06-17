package first.project.repository;

import first.project.entity.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class StatusRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StatusRepository statusRepository;


    @Test
    public void find_by_activity_then_order_by_sort_Asc() {
        //given
        Status s1 = new Status();
        s1.setName("Closed");
        s1.setSort(2);
        s1.setActivity(true);
        Status s2 = new Status();
        s2.setName("New");
        s2.setSort(1);
        s2.setActivity(true);
        entityManager.persist(s1);
        entityManager.persist(s2);
        //when
        List<Status> result = statusRepository.findByActivityIsNotNullOrderBySortAsc();
        //then
        assertEquals(result.get(0).getName(), s2.getName());
        assertEquals(result.get(1).getName(), s1.getName());
    }
}