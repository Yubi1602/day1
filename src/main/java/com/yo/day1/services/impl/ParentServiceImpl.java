package com.yo.day1.services.impl;

import com.yo.day1.common.exception.NotFoundException;
import com.yo.day1.domain.entity.Parent;
import com.yo.day1.dto.parent.ParentResponse;
import com.yo.day1.dto.parent.ParentUpsertRequest;
import com.yo.day1.repository.ParentRepository;
import com.yo.day1.services.ParentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {
    private final ParentRepository parentRepository;
    private final ModelMapper mapper;

    private ParentResponse map(Parent parent) {
        return mapper.map(parent, ParentResponse.class);
    }

    @Override
    public List<ParentResponse> findAll() {
        return parentRepository.findAll().stream()
                .map(this::map)
                .toList();
    }

    @Override
    public Optional<ParentResponse> findById(Long id) {
        return parentRepository.findById(id)
                .map(this::map);
    }

    @Override
    public ParentResponse create(ParentUpsertRequest req) {
        Parent parent = mapper.map(req, Parent.class);
        Parent result = parentRepository.save(parent);
        return map(result);
    }

    @Override
    public ParentResponse update(Long id, ParentUpsertRequest req) {
        Parent existing = parentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay phu huynh voi id: " + id));
        existing.setFullName(req.getFullName());
        existing.setPhone(req.getPhone());
        existing.setEmail(req.getEmail());
        existing.setAddress(req.getAddress());
        existing.setRelationship(req.getRelationship());
        existing.setGender(req.getGender());
        Parent result = parentRepository.save(existing);
        return map(result);
    }

    @Override
    public void delete(Long id) {
        if (parentRepository.existsById(id)) {
            parentRepository.deleteById(id);
        } else {
            throw new NotFoundException("Khong tim thay phu huynh voi id: " + id);
        }
    }
}
