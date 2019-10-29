package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.AppUser;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.AppUserRepository;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class ItemControllerTest {
    private ItemController itemController;
    private ItemRepository itemRepo = mock(ItemRepository.class);

    private AppUserRepository userRepo = mock(AppUserRepository.class);

    private CartRepository cartRepo = mock(CartRepository.class);

    @Before
    public void setUp() {
        itemController = new ItemController();
        Item test_item_1 = new Item();
        Item test_item_2 = new Item();
        Item test_item_3 = new Item();

        test_item_1.setName("test_item");
        test_item_1.setDescription("This is a test item.");
        test_item_1.setPrice(new BigDecimal(50.00));
        test_item_1.setId(0L);

        test_item_2.setId(1L);
        test_item_2.setName("another_item");
        test_item_2.setDescription("This is another test item.");
        test_item_2.setPrice(new BigDecimal(30.00));

        test_item_3.setName("test_item");
        test_item_3.setDescription("This is a third test item with duplicate name.");
        test_item_3.setPrice(new BigDecimal(20.00));
        test_item_3.setId(3L);

        TestUtils.injectObjects(itemController, "itemRepository", itemRepo);

        List<Item> test_item_list = new ArrayList<>();
        test_item_list.add(test_item_1);
        test_item_list.add(test_item_2);
        test_item_list.add(test_item_3);

        List<Item> test_items = new ArrayList<>();
        test_items.add(test_item_1);
        test_items.add(test_item_3);

        Mockito.when(itemRepo.findAll()).thenReturn(test_item_list);
        Mockito.when(itemRepo.findById(1L)).thenReturn(java.util.Optional.of(test_item_2));
        Mockito.when(itemRepo.findByName("test_item")).thenReturn(test_items);
    }
    @Test
    public void get_items() throws Exception {
        final ResponseEntity<List<Item>> response = itemController.getItems();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> itemList = response.getBody();
        assertNotNull(itemList);
        assertEquals("test_item", itemList.get(0).getName());
        assertEquals("This is a test item.", itemList.get(0).getDescription());
        assertEquals(new BigDecimal(50.00), itemList.get(0).getPrice());

        assertEquals("another_item", itemList.get(1).getName());
        assertEquals("This is another test item.", itemList.get(1).getDescription());
        assertEquals(new BigDecimal(30.00), itemList.get(1).getPrice());

    }

    @Test
    public void get_item_by_id() throws Exception {
        Long id = 1L;

        final ResponseEntity<Item> response = itemController.getItemById(id);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Item item = response.getBody();

        assertEquals("another_item", item.getName());
        assertEquals("This is another test item.", item.getDescription());
        assertEquals(new BigDecimal(30.00), item.getPrice());

    }

    @Test
    public void get_item_by_name() throws Exception {
        String name = "test_item";

        final ResponseEntity<List<Item>> response = itemController.getItemsByName(name);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> items = response.getBody();

        assertEquals("test_item", items.get(0).getName());
        assertEquals("This is a test item.", items.get(0).getDescription());
        assertEquals(new BigDecimal(50.00), items.get(0).getPrice());

        assertEquals("test_item", items.get(1).getName());
        assertEquals("This is a third test item with duplicate name.", items.get(1).getDescription());
        assertEquals(new BigDecimal(20.00), items.get(1).getPrice());
    }
}
