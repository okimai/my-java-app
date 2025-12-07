package com.example.java_app.unko;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/unkos")
@Validated
public class UnkoController {

  private final UnkoService unkoService;

  public UnkoController(UnkoService unkoService) {
    this.unkoService = unkoService;
  }

  @GetMapping("")
  public ResponseEntity<Page<UnkoSummaryResponse>> getUnkos() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<UnkoSummaryResponse> unkos = unkoService.getUnkos(pageable);
        return ResponseEntity.ok(unkos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UnkoDetailResponse> getUnko(@PathVariable Long id) {
    UnkoDetailResponse unko = unkoService.getUnkoById(id);
    return ResponseEntity.ok(unko);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UnkoDetailResponse> updateUnko(@PathVariable Long id, @RequestBody @Valid UnkoDetailRequest unko) {
    UnkoDetailResponse updatedUnko = unkoService.updateUnko(id, unko);
    return ResponseEntity.ok(updatedUnko);
  }

}
