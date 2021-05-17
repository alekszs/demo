package com.example.demo.controller;

import com.example.demo.DemoApplication;

import com.example.demo.model.Item;
import com.example.demo.model.ItemDTO;
import com.example.demo.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {DemoApplication.class})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ItemRepository itemRepository;

    private static Item createItemWithStock(int id, String name, String category, double price, String description, int stock) {
        Item temp = new Item();
        temp.setId(id);
        temp.setName(name);
        temp.setCategory(category);
        temp.setPrice(price);
        temp.setDescription(description);
        temp.setStock(stock);
        return temp;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createNewItem() throws Exception {
        //given
        ItemDTO newItem = new ItemDTO("Food", "Fruit", 0.99, "Banana");
        //when
        mvc.perform(MockMvcRequestBuilders.post("/api/createItem")
                .content(asJsonString(newItem))
                .contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(newItem.getName()))
                .andExpect(jsonPath("$.category").value(newItem.getCategory()))
                .andExpect(jsonPath("$.price").value(newItem.getPrice()))
                .andExpect(jsonPath("$.description").value(newItem.getDescription()));

        assertEquals(1, itemRepository.findAllByCategory("Fruit").size());
    }

    @Test
    void getAllWithSameCategoryAndWithStock() throws Exception {
        //given
        Item itemOne = createItemWithStock(1, "X", "C", 32, "D", 1);
        Item itemTwo = createItemWithStock(2, "Y", "C", 22, "D", 2);
        Item itemThree = createItemWithStock(3, "Z", "C", 7, "D", 0);
        itemRepository.saveAll(Arrays.asList(itemOne, itemTwo, itemThree));
        //when
        mvc.perform(MockMvcRequestBuilders.get("/api/getAllItemsByCategory")
                .param("itemCategory", "C")
                .param("inStock", "true")
                .contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(2)));
    }

    @Test
    void updateItemStock() throws Exception {
        //given
        Item temp = createItemWithStock(1, "X", "C", 32, "D", 1);
        itemRepository.save(temp);
        assertEquals(1, itemRepository.findById(1).get().getStock());
        //when
        mvc.perform(MockMvcRequestBuilders.put("/api/updateItemStock")
                .param("itemId", "1")
                .param("stockSize", "5")
                .contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isOk());

        assertEquals(5, itemRepository.findById(1).get().getStock());
    }
}