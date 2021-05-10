package com.example.demo.service.mapper;

import com.example.demo.model.Item;
import com.example.demo.model.ItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    ItemDTO toDTO(Item item);

    @Mapping(target = "stock", ignore = true)
    Item toEntity(ItemDTO itemDTO);

    List<ItemDTO> toDTO(List<Item> items);

    List<Item> toEntity(List<ItemDTO> items);

}
