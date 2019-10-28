package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.AppUserRepository;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;

public class ItemControllerTest {
    private ItemController itemController;

    private ItemRepository itemRepository;

    private AppUserRepository userRepo = mock(AppUserRepository.class);

    private CartRepository cartRepo = mock(CartRepository.class);

    @Before
    public void setUp() {
        /*cartController = new CartController();
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
        Mockito.when(itemRepo.findById(1L)).thenReturn(java.util.Optional.of(test_item_2));*/

    }

    public void get_items() throws Exception {

    }
}
