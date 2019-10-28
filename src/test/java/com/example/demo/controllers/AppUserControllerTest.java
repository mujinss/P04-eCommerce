package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.AppUser;
import com.example.demo.model.persistence.repositories.AppUserRepository;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AppUserControllerTest {
    private AppUserController userController;

    private AppUserRepository userRepo = mock(AppUserRepository.class);

    private CartRepository cartRepo = mock(CartRepository.class);

    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new AppUserController();
        TestUtils.injectObjects(userController, "appUserRepository", userRepo);
        TestUtils.injectObjects(userController, "cartRepository", cartRepo);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);

        AppUser test_user = new AppUser();
        test_user.setUsername("test_user");
        test_user.setPassword("test_user_password");
        test_user.setConfirmPassword("test_user_confirmed_password");
        Mockito.when(userRepo.findByUsername("test_user")).thenReturn(test_user);
        Mockito.when(userRepo.findById(0L)).thenReturn(java.util.Optional.of(test_user));
    }
    @Test
    public void create_user_happy_path() throws Exception {
        when(encoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");

        final ResponseEntity<AppUser> response = userController.createUser(r);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        AppUser u = response.getBody();
        assertNotNull(u);
        assertEquals(0, u.getId());
        assertEquals("test", u.getUsername());
        assertEquals("thisIsHashed", u.getPassword());
    }

    @Test
    public void get_user_by_username() throws Exception {
        String user_name = "test_user";


        final ResponseEntity<AppUser> response = userController.findByUserName(user_name);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        AppUser u = response.getBody();
        assertNotNull(u);
        assertEquals(0, u.getId());
        assertEquals("test_user_password", u.getPassword());
    }

    @Test
    public void get_user_by_id() throws Exception {
        Long user_id = 0L;
        final ResponseEntity<AppUser> response = userController.findById(user_id);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        AppUser u = response.getBody();
        assertNotNull(u);
        assertEquals(0, u.getId());
        assertEquals("test_user_password", u.getPassword());
    }

}
