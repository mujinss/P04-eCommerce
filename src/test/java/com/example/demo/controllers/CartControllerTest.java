package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.AppUser;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.AppUserRepository;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    private CartController cartController;
    private Cart cart;

    private AppUserRepository appUserRepo = mock(AppUserRepository.class);

    private CartRepository cartRepo = mock(CartRepository.class);

    private ItemRepository itemRepo = mock(ItemRepository.class);

    private AppUser user = mock(AppUser.class);

    @Before
    public void setUp() {
        cartController = new CartController();
        cart = new Cart();
        TestUtils.injectObjects(cartController, "itemRepository", itemRepo);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepo);
        TestUtils.injectObjects(cartController, "appUserRepository", appUserRepo);

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

        // AppUser user = new AppUser();
        user.setUsername("test_user_name");
        user.setPassword("test_user_password");
        user.setConfirmPassword("test_user_password");
        user.setCart(cart);

        // cart.setUser(user);
        cart.addItem(test_item_1);
        cart.addItem(test_item_2);
        cartRepo.save(cart);

        Mockito.when(appUserRepo.findByUsername("test_user_name")).thenReturn(user);
        Mockito.when(user.getCart()).thenReturn(cart);
        Mockito.when(itemRepo.findById(0L)).thenReturn(java.util.Optional.of(test_item_1));
        Mockito.when(itemRepo.findById(1L)).thenReturn(java.util.Optional.of(test_item_2));

    }
    @Test
    public void add_to_cart() throws Exception {
        ModifyCartRequest r = new ModifyCartRequest();
        r.setUsername("test_user_name");
        r.setItemId(1L);
        r.setQuantity(1);

        final ResponseEntity<Cart> response = cartController.addTocart(r);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart cart = response.getBody();

        assertEquals("another_item", cart.getItems().get(2).getName());
        assertEquals("This is another test item.", cart.getItems().get(2).getDescription());
        assertEquals(new BigDecimal(110.00), cart.getTotal());

    }

    @Test
    public void remove_from_cart() throws Exception {
        ModifyCartRequest r = new ModifyCartRequest();
        r.setUsername("test_user_name");
        r.setItemId(1L);
        r.setQuantity(1);


        final ResponseEntity<Cart> response = cartController.removeFromcart(r);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("0", cart.getItems().get(0).getId().toString());
        assertEquals(new BigDecimal(50.00), cart.getTotal());

    }



}
