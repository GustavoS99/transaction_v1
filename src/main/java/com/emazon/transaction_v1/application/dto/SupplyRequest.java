package com.emazon.transaction_v1.application.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplyRequest {

    private Long itemId;

    private Long quantity;
}
