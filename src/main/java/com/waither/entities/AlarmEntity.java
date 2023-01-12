package com.waither.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Table(name = "Alarm")
@Entity(name = "Alarm")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlarmEntity {

    @Id
    @SequenceGenerator(name = "alarm_sequence", sequenceName = "alarm_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "alarm_sequence")
    private Long alarmIdx;

    @Column(name = "userIdx", nullable = false)
    private Long userIdx;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "createdAt", nullable = false)
    private Timestamp createdAt;


}
