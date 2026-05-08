package com.yo.day1.services;

import com.yo.day1.dto.parent.ParentResponse;
import com.yo.day1.dto.parent.ParentUpsertRequest;

import java.util.List;
import java.util.Optional;

public interface ParentService {
    List<ParentResponse> findAll();

    Optional<ParentResponse> findById(Long id);

    ParentResponse create(ParentUpsertRequest req);

    ParentResponse update(Long id, ParentUpsertRequest req);

    void delete(Long id);
}
