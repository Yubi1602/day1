package com.yo.day1.dto.parent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationCard {
    private Long id;
    private String type;
    private String title;
    private String content;
    private Boolean isRead;
    private LocalDateTime createdAt;
}
