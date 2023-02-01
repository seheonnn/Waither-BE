package com.waither.entities;

import com.waither.dto.AlarmDTO;
//import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "Alarm")
@Entity(name = "Alarm")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlarmEntity {

    @Id
    @SequenceGenerator(name = "alarm_sequence", sequenceName = "alarm_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "alarm_sequence")
    private Long alarmIdx;

    @Column(name = "userIdx", nullable = false)
    private Long userIdx;

    @Column(name = "contents", nullable = false)
    @ColumnDefault("")
    private String contents;

    @Column(name = "createdAt", nullable = false)
    private Timestamp createdAt;

    public AlarmDTO toDTO() {
        return AlarmDTO.builder()
                .userIdx(userIdx)
                .contents(contents)
                .createdAt(createdAt)
                .build();
    }


}
