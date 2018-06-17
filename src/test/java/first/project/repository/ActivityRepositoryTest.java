package first.project.repository;

import first.project.entity.Activity;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@Log
public class ActivityRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ActivityRepository activityRepository;


    @Test
    public void find_by_created_then_order_by_asc_then_limit15() {
        //given
        Activity activity1 = new Activity();
        activity1.setTitle("Activity1");
        activity1.setCreated(LocalDateTime.now());

        Activity activity2 = new Activity();
        activity2.setTitle("Activity2");
        activity2.setCreated(LocalDateTime.now().minusHours(1));


        entityManager.persist(activity1);
        entityManager.persist(activity2);

        log.info(activity1.getCreated().toString());
        log.info(activity2.getCreated().toString());

        //when
        List<Activity> result = activityRepository.findbycreatedlimit();

        //then
        assertEquals(result.get(0).getTitle(), activity1.getTitle());
        assertEquals(result.get(1).getTitle(), activity2.getTitle());
    }
}