package com.yo.day1.services.impl;

import com.yo.day1.common.exception.NotFoundException;
import com.yo.day1.domain.entity.Room;
import com.yo.day1.dto.room.RoomResponse;
import com.yo.day1.dto.room.RoomUpsertRequest;
import com.yo.day1.repository.RoomRepository;
import com.yo.day1.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final ModelMapper mapper;

    private RoomResponse map(Room room){
        return mapper.map(room, RoomResponse.class);
    }
    @Override
    public List<RoomResponse> findAll(){
        return roomRepository.findAll().stream()
                .map(r->map(r))
                .toList();
    }
    @Override
    public Optional<RoomResponse> findById(long id){
        return roomRepository.findById(id)
                .map(this::map);
    }
    public RoomResponse save(RoomUpsertRequest req){
        Room room = mapper.map(req, Room.class);
        Room response =  roomRepository.save(room);
        return map(response);
    }

    public RoomResponse update(long id, RoomUpsertRequest req){
            Room room =  mapper.map(req, Room.class);
        room.setId(id);
        Room response = roomRepository.save(room);
        return map(response);
    }
    public void delete(long id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
        } else {
            throw new NotFoundException("Khong tim thay phong hoc voi id: " + id);
        }
    }
//    public RoomResponse update(long id, RoomUpsertRequest req) {
//        Room existing = roomRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Room not found: " + id));
//
//        mapper.map(req, existing);
//
//        return map(roomRepository.save(existing));
//    }
}
