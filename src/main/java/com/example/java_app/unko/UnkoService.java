package com.example.java_app.unko;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UnkoService {

  private final UnkoRepository unkoRepository;

  public UnkoService(UnkoRepository unkoRepository) {
    this.unkoRepository = unkoRepository;
  }

  public Page<UnkoSummaryResponse> getUnkos(Pageable pageable) {
    return unkoRepository.findAll(pageable)
        .map(UnkoSummaryResponse::from);
  }

  public UnkoDetailResponse getUnkoById(Long id) {
    Unko unko = unkoRepository.findById(id)
      .orElseThrow(() -> new UnkoNotFoundException("Unko not found with id: " + id));
    return UnkoDetailResponse.from(unko);
  }

  public UnkoDetailResponse updateUnko(Long id, UnkoDetailRequest unkoDetailRequest) {
    Unko unko = unkoRepository.findById(id)
      .orElseThrow(() -> new UnkoNotFoundException("Unko not found with id: " + id));

    unko.setName(unkoDetailRequest.name());
    unko.setDescription(unkoDetailRequest.description());
    unko.setExpirationDate(unkoDetailRequest.expirationDate());
    unko.setPrice(unkoDetailRequest.price());
    unko.setDiscount(unkoDetailRequest.discount());

    Unko updatedUnko = unkoRepository.save(unko);
    return UnkoDetailResponse.from(updatedUnko);
  }
}
