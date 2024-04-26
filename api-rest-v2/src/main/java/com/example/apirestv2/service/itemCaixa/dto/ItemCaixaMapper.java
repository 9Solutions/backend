package com.example.apirestv2.service.itemCaixa.dto;

import com.example.apirestv2.domain.itemCaixa.ItemCaixa;
import java.util.List;

public class ItemCaixaMapper {
    public static ItemsCaixaDTO toDTO(ItemCaixa item){
        ItemsCaixaDTO dto = new ItemsCaixaDTO();
        dto.setId(item.getId());
        dto.setIdCaixa(item.getIdCaixa());
        dto.setIdProduto(item.getIdProduto());
        return dto;
    }

    public static List<ItemsCaixaDTO> toDTO(List<ItemCaixa> items){
        return items.stream().map(ItemCaixaMapper::toDTO).toList();
    }
}
