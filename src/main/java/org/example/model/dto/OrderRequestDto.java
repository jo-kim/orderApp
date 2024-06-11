package org.example.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderRequestDto {
    private long itemNo;
    private int quantity;

}
