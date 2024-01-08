package com.fastcampus.sns.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 컬럼을 지정할 경우 null 이 많이 들어가게 된다.
// why? -> 좋아요 알람, 코멘트 좋아요 알람, ~님 외의 2명이 좋아요 . . 이런식으로 무수히 타입을 만들 수 있기 때문에
// 그래서 argument로 유연하게 받기
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmArgs {
    // 알람을 발생시킨 사람
    private Integer fromUserId;
    private Integer targetId;
}
