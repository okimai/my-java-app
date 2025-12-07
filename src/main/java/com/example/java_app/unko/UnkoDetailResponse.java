package com.example.java_app.unko;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UnkoDetailResponse(
    Long id,
    String name,
    String description,
    LocalDate expirationDate,
    BigDecimal price,
    BigDecimal discount) {

      public static UnkoDetailResponse from(Unko unko) {
        return new UnkoDetailResponse(
            unko.getId(),
            unko.getName(),
            unko.getDescription(),
            unko.getExpirationDate(),
            unko.getPrice(),
            unko.getDiscount()
          );
      }
}