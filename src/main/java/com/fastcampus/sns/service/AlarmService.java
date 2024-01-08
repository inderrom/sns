package com.fastcampus.sns.service;

import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.model.AlarmArgs;
import com.fastcampus.sns.model.AlarmType;
import com.fastcampus.sns.model.entity.AlarmEntity;
import com.fastcampus.sns.model.entity.UserEntity;
import com.fastcampus.sns.repository.AlarmEntityRepository;
import com.fastcampus.sns.repository.EmitterRepository;
import com.fastcampus.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {
    private final static String ALARM_NAME = "alarm";
    private final EmitterRepository emitterRepository;
    private final UserEntityRepository userEntityRepository;
    private final AlarmEntityRepository alarmEntityRepository;

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    public void send(Integer alarmId, Integer userId) {
//        UserEntity userEntity = userEntityRepository.findById(userId).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND));
//        AlarmEntity entity = AlarmEntity.of(userEntity, type, args);
//        alarmEntityRepository.save(entity);
        emitterRepository.get(userId).ifPresentOrElse(sseEmitter -> {
                    try {
                        sseEmitter.send(SseEmitter.event()
                                .id(alarmId.toString())
                                .name(ALARM_NAME)
                                .data("new alarm"));
                    } catch (IOException exception) {
                        emitterRepository.delete(userId);
                        throw new SnsApplicationException(ErrorCode.ALRARM_CONNECT_ERROR);
                    }
                },
                () -> log.info("No emitter founded")
        );
    }

    public SseEmitter connectAlarm(Integer userId) {
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(userId, sseEmitter);
        sseEmitter.onCompletion(() -> emitterRepository.delete(userId));
        sseEmitter.onTimeout(() -> emitterRepository.delete(userId));

        try {
            log.info("send");
            sseEmitter.send(SseEmitter.event()
                    .id("id")
                    .name(ALARM_NAME)
                    .data("connect completed"));
        } catch (IOException exception) {
            throw new SnsApplicationException(ErrorCode.ALRARM_CONNECT_ERROR);
        }
        return sseEmitter;
    }

}