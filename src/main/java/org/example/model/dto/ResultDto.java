package org.example.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResultDto {
    private int price;
    private int shipFee;
    private int payPrice;

}
