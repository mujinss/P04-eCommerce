package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.AppUser;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.AppUserRepository;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;

import javax.persistence.criteria.Order;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class OrderControllerTest {
    private OrderController orderController;

    private AppUserRepository appUserRepo = mock(AppUserRepository.class);

    private OrderRepository orderRepo = mock(OrderRepository.class);


    @Before
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "appUserRepository", appUserRepo);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepo);

        AppUser test_user = new AppUser();
        test_user.setUsername("test_user");
        test_user.setPassword("test_user_password");
        test_user.setConfirmPassword("test_user_confirmed_password");

        Cart cart = new Cart();

        Item test_item_1 = new Item();
        test_item_1.setName("test_item");
        test_item_1.setDescription("This is a test item.");
        test_item_1.setPrice(new BigDecimal(50.00));
        test_item_1.setId(0L);
        Item test_item_2 = new Item();
        test_item_2.setId(1L);
        test_item_2.setName("another_item");
        test_item_2.setDescription("This is another test item.");
        test_item_2.setPrice(new BigDecimal(30.00));

        cart.addItem(test_item_1);
        cart.addItem(test_item_2);
        test_user.setCart(cart);

        Mockito.when(appUserRepo.findByUsername("test_user")).thenReturn(test_user);

    }

    @Test
    public void submit() {
        String username = "test_user";
        final ResponseEntity<UserOrder> r = orderController.submit(username);
        assertNotNull(r);
        assertEquals(200, r.getStatusCodeValue());
        UserOrder userOrder = r.getBody();

        assertEquals(new BigDecimal(80.00), userOrder.getTotal());
        assertEquals("test_item", userOrder.getItems().get(0).getName());
        assertEquals("another_item", userOrder.getItems().get(1).getName());


    }

}
