package com.example.oxygen.model.mapper;

import com.example.oxygen.entity.Item;
import com.example.oxygen.model.ItemDTO;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item toEntity(ItemDTO itemDTO);
    ItemDTO toDto(Item item);
}
