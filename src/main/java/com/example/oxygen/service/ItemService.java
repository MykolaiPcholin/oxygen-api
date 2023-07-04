package com.example.oxygen.service;

import com.example.oxygen.entity.Item;
import com.example.oxygen.model.ItemDTO;
import com.example.oxygen.model.mapper.ItemMapper;
import com.example.oxygen.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public List<Item> getItems() {

        return itemRepository.findAll();
    }

    public Item saveItem(ItemDTO itemDTO) {

        return itemRepository.save(itemMapper.toEntity(itemDTO));
    }
}
