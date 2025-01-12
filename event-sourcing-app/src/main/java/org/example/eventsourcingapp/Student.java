package org.example.eventsourcingapp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
public class Student {
    @Id
    @Column(name = "student_id")
    private String studentId;

    private String name;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Version
    private Long version;
}
