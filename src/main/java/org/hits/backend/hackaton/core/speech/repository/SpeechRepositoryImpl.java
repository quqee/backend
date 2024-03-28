package org.hits.backend.hackaton.core.speech.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static com.example.hackathon.public_.tables.VoiceScheduler.VOICE_SCHEDULER;

@Repository
@RequiredArgsConstructor
public class SpeechRepositoryImpl implements SpeechRepository {
    private static final SpeechEntityMapper SPEECH_ENTITY_MAPPER = new SpeechEntityMapper();

    private final DSLContext create;

    @Override
    public List<SpeechEntity> getSpeechesNotProcessed() {
        return create.selectFrom(VOICE_SCHEDULER)
                .where(VOICE_SCHEDULER.STATUS.eq(SpeechStatus.IN_PROCESS.name()))
                .and(VOICE_SCHEDULER.SCHEDULE_TIME.lt(OffsetDateTime.now()))
                .fetch(SPEECH_ENTITY_MAPPER);
    }

    @Override
    public void updateSpeechStatus(UUID applicationId, SpeechStatus status) {
        create.update(VOICE_SCHEDULER)
                .set(VOICE_SCHEDULER.STATUS, status.name())
                .where(VOICE_SCHEDULER.APPLICATION_ID.eq(applicationId))
                .execute();
    }

    @Override
    public void createSpeech(SpeechEntity speechEntity) {
        create.insertInto(VOICE_SCHEDULER)
                .set(VOICE_SCHEDULER.APPLICATION_ID, speechEntity.applicationId())
                .set(VOICE_SCHEDULER.SCHEDULE_TIME, speechEntity.scheduleTime())
                .set(VOICE_SCHEDULER.STATUS, speechEntity.status().name())
                .set(VOICE_SCHEDULER.PROCESS_ID, speechEntity.processId())
                .execute();
    }
}
