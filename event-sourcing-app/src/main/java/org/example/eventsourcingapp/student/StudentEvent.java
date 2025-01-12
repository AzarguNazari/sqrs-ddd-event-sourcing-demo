package org.example.eventsourcingapp.student;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student_events")
@Getter
@Setter
@NoArgsConstructor
public class StudentEvent {
    @Id
    private String eventId = UUID.randomUUID().toString();
    private String studentId;
    @Enumerated(EnumType.STRING)
    private StudentEventType eventType;
    @Column(columnDefinition = "jsonb")
    private String eventData;
    private LocalDateTime timestamp = LocalDateTime.now();
    private Long version;
}
