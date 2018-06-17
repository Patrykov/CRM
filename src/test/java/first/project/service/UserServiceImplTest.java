package first.project.service;

import first.project.entity.User;
import first.project.repository.RoleRepository;
import first.project.repository.UserRepository;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Log
public class UserServiceImplTest {

    private UserService userService;
    private UserRepository repository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void setUp(){
        repository = mock(UserRepository.class);
        userService = new UserServiceImpl(repository,roleRepository,bCryptPasswordEncoder);
    }

    @Test
    public void when_find_user_then_return_login() {
        //given
        User user1 = new User();
        user1.setFirstName("User1");
        user1.setLogin("first");
        User user2 = new User();
        user2.setFirstName("User2");
        user2.setLogin("second");

        when(repository.findByLogin("first")).thenReturn(user1);
        when(repository.findByLogin("second")).thenReturn(user2);

        //when
        User user3 = userService.findByLogin("first");
        User user4 = userService.findByLogin("second");
        //then
        assertEquals(user3.getFirstName(), "User1");
        assertEquals(user4.getFirstName(), "User2");
    }
}