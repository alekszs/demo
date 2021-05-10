package com.example.demo.controller;

import com.example.demo.DemoApplication;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
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

    @Test
    void getAllWithSameCategoryAndWithStock() throws Exception {
        //given
        Item itemOne = createItem(1,"X","C", 32,"D",1);
        Item itemTwo = createItem(2,"Y","C", 22,"D",2);
        Item itemThree = createItem(3,"Z","C", 7,"D",0);
        itemRepository.saveAll(Arrays.asList(itemOne,itemTwo,itemThree));
        //when
        mvc.perform(MockMvcRequestBuilders.get("/getAllItems")
                .param("itemCategory","C")
                .param("inStock","true")
                .contentType(MediaType.APPLICATION_JSON))
        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(2)));
    }

    @Test
    void setItemStock() throws Exception{
        //given
        Item temp = createItem(1,"X","C", 32,"D",1);
        itemRepository.save(temp);
        assertEquals(1, itemRepository.findById(1).get().getStock());
        //when
        mvc.perform(MockMvcRequestBuilders.get("/updateItemStock")
                .param("itemId","1")
                .param("stockSize","5")
                .contentType(MediaType.APPLICATION_JSON))
        //then
                .andExpect(status().isOk());

        assertEquals(5, itemRepository.findById(1).get().getStock());
    }
}