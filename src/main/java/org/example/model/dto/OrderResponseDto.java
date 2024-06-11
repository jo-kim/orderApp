package org.example.model.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderResponseDto {
    private long itemNo;
    private String name;
    private int quantity;

}
