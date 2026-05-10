package com.yo.day1.dto.room;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RoomUpsertRequest {
    @Length(min = 1, max = 20)
    @NotNull
    private String roomCode;
    @Length(max = 100)
    private String name;
    @Min(1)
    private int capacity;

    private String description;


}
