package com.fastcampus.sns.controller.response;

import com.fastcampus.sns.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class AlarmResponse {
    private Integer id;
    private String text;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static AlarmResponse fromAlarm(Alarm alarm) {
        return new AlarmResponse(
                alarm.getId(),
                alarm.getAlarmType().getAlarmText(),
                alarm.getAlarmType(),
                alarm.getArgs(),
                alarm.getRegisteredAt(),
                alarm.getUpdatedAt(),
                alarm.getDeleteAt()
        );
    }
}