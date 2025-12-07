package com.example.java_app.unko;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UnkoDetailRequest(

  @NotBlank
  String name,

  String description,

  @NotNull
  LocalDate expirationDate,

  @NotNull
  @Min(0)
  @Digits(integer = 10, fraction = 0)
  BigDecimal price,

  @NotNull
  @Min(0)
  @Max(100)
  @Digits(integer = 3, fraction = 1)
  BigDecimal discount

){}