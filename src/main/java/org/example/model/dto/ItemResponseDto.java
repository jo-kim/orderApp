package org.example.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDto {
    private long itemNo;
    private String itemName;
    private int price;
    private int quantity;


}
