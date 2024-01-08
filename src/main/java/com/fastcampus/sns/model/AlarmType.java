package com.fastcampus.sns.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AlarmType {
    // DB에 이런 알람텍스트들이 쌓이면 무수히 많이 쌓이므로 -> 서버에서 띄워줌
    NEW_COMMENT_ON_POST("new comment!"),
    NEW_LIKE_ON_POST("new like!"),
    ;

    private final String alarmText;
}
