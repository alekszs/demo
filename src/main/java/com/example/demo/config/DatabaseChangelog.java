package com.example.demo.config;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class DatabaseChangelog {

    private final String NAME = "name";
    private final String CATEGORY = "category";
    private final String PRICE = "price";
    private final String DESCRIPTION = "description";

    private static final Logger log = LoggerFactory.getLogger(DatabaseChangelog.class);

    @Autowired
    ItemRepository itemRepository;

    @EventListener(ApplicationReadyEvent.class)
    private void populateDB() {
        log.debug("!!!!!!!!!!STARTUP!!!!!!!!!!");

        List<Item> newItems = new ArrayList<>();




        itemRepository.saveAll(newItems);

    }
}
