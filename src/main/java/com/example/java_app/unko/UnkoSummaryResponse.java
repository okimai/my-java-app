package com.example.java_app.unko;

import java.math.BigDecimal;

public record UnkoSummaryResponse(
    Long id,
    String name,
    BigDecimal price) {

      public static UnkoSummaryResponse from(Unko unko) {
          return new UnkoSummaryResponse(
              unko.getId(),
              unko.getName(),
              unko.getPrice()
          );
      }
}