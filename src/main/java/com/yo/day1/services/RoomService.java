package com.yo.day1.services;

import com.yo.day1.dto.room.RoomResponse;
import com.yo.day1.dto.room.RoomUpsertRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public interface RoomService {
    List<RoomResponse> findAll();
    Optional<RoomResponse> findById(long id);
    RoomResponse save(RoomUpsertRequest req);
    RoomResponse update(long id, RoomUpsertRequest req);
    void delete(long id);
}
