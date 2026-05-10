package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.dto.room.RoomResponse;
import com.yo.day1.dto.room.RoomUpsertRequest;
import com.yo.day1.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.yo.day1.common.ApiResponse.error;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/rooms")
public class RoomController {
    private final RoomService roomService;
    @GetMapping
    public ApiResponse<List<RoomResponse>> findAll(){
        return ApiResponse.success(roomService.findAll());
    }
    @GetMapping("/{id}")
    public ApiResponse<RoomResponse> findById(@PathVariable long id){
//      return  roomService.findById(id).map(ApiResponse::success)
//                .orElseThrow(ApiResponse.error("NOT FOUND"));
        Optional<RoomResponse> roomResponse = roomService.findById(id);
        if (roomResponse.isPresent()){
            return  ApiResponse.success(roomResponse.get());
        } else {
            return ApiResponse.error("Phòng học không tìm thấy", new RoomResponse());
        }
    }

    @PostMapping
    public ApiResponse<RoomResponse> save(@RequestBody RoomUpsertRequest req){
        return ApiResponse.success("Tạo phòng học thành công",roomService.save(req));

    }

    @PutMapping("/{id}")
    public ApiResponse<RoomResponse> update(@PathVariable long id, RoomUpsertRequest req){
        return ApiResponse.success(roomService.update(id,req));
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        roomService.delete(id);
        return ApiResponse.successMessage("Xóa phòng học thành công");
    }

}
