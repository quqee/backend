package org.hits.backend.hackaton.core.speech.repository;

import com.example.hackathon.public_.tables.records.VoiceSchedulerRecord;
import org.jooq.RecordMapper;

public class SpeechEntityMapper implements RecordMapper<VoiceSchedulerRecord, SpeechEntity> {
    @Override
    public SpeechEntity map(VoiceSchedulerRecord voiceSchedulerRecord) {
        return new SpeechEntity(
                voiceSchedulerRecord.getApplicationId(),
                voiceSchedulerRecord.getScheduleTime(),
                SpeechStatus.getStatusByName(voiceSchedulerRecord.getStatus()),
                voiceSchedulerRecord.getProcessId()
        );
    }
}
