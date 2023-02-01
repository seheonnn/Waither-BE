package com.waither.dto;

import com.waither.entities.AlarmEntity;
//import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AlarmDTO {

    private Long alarmIdx;

    private Long userIdx;

    private String contents;

    private Timestamp createdAt;

    public AlarmEntity toEntity() {
        return AlarmEntity.builder()
                .userIdx(userIdx)
                .contents(contents)
                .createdAt(createdAt)
                .build();
    }
}
