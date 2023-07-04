package com.example.oxygen.model.mapper;

import com.example.oxygen.entity.Item;
import com.example.oxygen.model.ItemDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-26T18:12:51+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class ItemMapperImpl implements ItemMapper {

    @Override
    public Item toEntity(ItemDTO itemDTO) {
        if ( itemDTO == null ) {
            return null;
        }

        Item item = new Item();

        return item;
    }

    @Override
    public ItemDTO toDto(Item item) {
        if ( item == null ) {
            return null;
        }

        ItemDTO.ItemDTOBuilder itemDTO = ItemDTO.builder();

        return itemDTO.build();
    }
}
