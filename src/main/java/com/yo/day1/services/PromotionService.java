package com.yo.day1.services;

import com.yo.day1.common.exception.NotFoundException;
import com.yo.day1.dto.promotion.PromotionResponse;
import com.yo.day1.dto.promotion.PromotionUpsertRequest;

import java.util.List;
import java.util.Optional;

public interface PromotionService {
    List<PromotionResponse> findAll();
    Optional<PromotionResponse> findById(Long id);
    PromotionResponse create(PromotionUpsertRequest req);
    PromotionResponse update(Long id, PromotionUpsertRequest req) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
}
