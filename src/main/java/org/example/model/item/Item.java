package org.example.model.item;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private long itemNo;
    private String itemName;
    private int price;
    private int quantity;

}
