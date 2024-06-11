package org.example.service;

import org.example.model.item.Item;
import org.example.model.item.ItemDAO;
import org.example.model.dto.ItemResponseDto;

import java.util.ArrayList;
import java.util.List;

public class ItemService {
    private final ItemDAO itemDAO = new ItemDAO();
    // 상품 목록 조회
    public List<ItemResponseDto> getItemList() {
        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();
        List<Item> itemList = itemDAO.getItemList();
        for (Item item : itemList) {
            ItemResponseDto itemResponseDto = new ItemResponseDto(
                    item.getItemNo(),
                    item.getItemName(),
                    item.getPrice(),
                    item.getQuantity()
            );

            itemResponseDtoList.add(itemResponseDto);
        }
        return itemResponseDtoList;
    }
    // 상품 단건 조회
    public Item getItem(final long itemNo) {
        return itemDAO.getItem(itemNo);
    }
    // 상품 업데이트
    public void updateItem(final Item item) {
        new ItemDAO().updateItem(item);

    }
}
