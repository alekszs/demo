package com.example.demo.service.impl;

import com.example.demo.model.Item;
import com.example.demo.model.ItemDTO;
import com.example.demo.repository.ItemRepository;
import com.example.demo.service.ItemService;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class ItemServiceImplTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Autowired
    private ItemService itemService;
    @MockBean
    private ItemRepository itemRepository;

    private static Item createItem(int id, String name, String category, double price, String description, int stock) {
        Item temp = new Item();
        temp.setId(id);
        temp.setName(name);
        temp.setCategory(category);
        temp.setPrice(price);
        temp.setDescription(description);
        temp.setStock(stock);
        return temp;
    }

    private static ItemDTO createItemDTO(int id, String name, String category, double price, String description) {
        ItemDTO temp = new ItemDTO();
        temp.setId(id);
        temp.setName(name);
        temp.setCategory(category);
        temp.setPrice(price);
        temp.setDescription(description);
        return temp;
    }

    @Test
    void getItemById() {
        //given
        Item temp = createItem(1, "A", "A", 0, "A", 0);
        given(itemRepository.findById(1)).willReturn(Optional.of(temp));
        //when
        ItemDTO result = itemService.retrieveItemById(1);
        //then
        assertThat(result.getId()).isEqualTo(temp.getId());
        assertThat(result.getName()).isEqualTo(temp.getName());
        assertThat(result.getCategory()).isEqualTo(temp.getCategory());
        assertThat(result.getPrice()).isEqualTo(temp.getPrice());
        assertThat(result.getDescription()).isEqualTo(temp.getDescription());
    }

    @Test
    void getAllItemsThrowsException() {
        //given
        given(itemRepository.findAllByCategory("C")).willReturn(Arrays.asList());
        //when
        Throwable exception = assertThrows(ResponseStatusException.class,
                () -> itemService.retrieveAllItems("C", false));
        //then
        assertEquals("404 NOT_FOUND \"Could not find items\"", exception.getMessage());
    }

    @Test
    void updateItem() {
        //given
        Item temp = createItem(1, "A", "A", 0, "A", 0);
        given(itemRepository.findById(1)).willReturn(Optional.of(temp));
        ItemDTO updateWith = createItemDTO(1,"B","B",22,"D");
        //when
        ItemDTO result = itemService.updateItem(updateWith);
        //then
        assertThat(result.getId()).isEqualTo(updateWith.getId());
        assertThat(result.getName()).isEqualTo(updateWith.getName());
        assertThat(result.getCategory()).isEqualTo(updateWith.getCategory());
        assertThat(result.getPrice()).isEqualTo(updateWith.getPrice());
        assertThat(result.getDescription()).isEqualTo(updateWith.getDescription());
    }

}