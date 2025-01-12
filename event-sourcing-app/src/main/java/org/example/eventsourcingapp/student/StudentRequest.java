package org.example.eventsourcingapp.student;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record StudentRequest(
    String name,
    LocalDate dateOfBirth) {
}
