package com.example.oxygen.controller;

import com.example.oxygen.entity.Item;
import com.example.oxygen.model.ItemDTO;
import com.example.oxygen.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<Item> getItems() {

        return itemService.getItems();
    }

    @PostMapping
    public ResponseEntity<Item> saveItem(@RequestBody @Validated ItemDTO itemDTO) {

        return ResponseEntity.ok(itemService.saveItem(itemDTO));
    }
}