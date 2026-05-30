package com.yo.day1.services.impl;

import com.yo.day1.common.exception.NotFoundException;
import com.yo.day1.domain.entity.Promotion;
import com.yo.day1.dto.promotion.PromotionResponse;
import com.yo.day1.dto.promotion.PromotionUpsertRequest;
import com.yo.day1.repository.PromotionRepository;
import com.yo.day1.services.PromotionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final ModelMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponse> findAll() {
        return promotionRepository.findAll().stream()
                .map(p -> mapper.map(p, PromotionResponse.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PromotionResponse> findById(Long id) {
        return promotionRepository.findById(id)
                .map(p -> mapper.map(p, PromotionResponse.class));
    }

    @Override
    @Transactional
    public PromotionResponse create(PromotionUpsertRequest req) {
        Promotion promotion = mapper.map(req, Promotion.class);
        promotion.setId(null);
        Promotion saved = promotionRepository.save(promotion);
        return mapper.map(saved, PromotionResponse.class);
    }

    @Override
    @Transactional
    public PromotionResponse update(Long id, PromotionUpsertRequest req) throws NotFoundException {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy khuyến mãi với id: " + id));
        mapper.map(req, promotion);
        Promotion updated = promotionRepository.save(promotion);
        return mapper.map(updated, PromotionResponse.class);
    }

    @Override
    @Transactional
    public void delete(Long id) throws NotFoundException {
        promotionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy khuyến mãi với id: " + id));
        promotionRepository.deleteById(id);
    }
}
