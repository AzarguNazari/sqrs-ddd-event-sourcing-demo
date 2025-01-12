package org.example.eventsourcingapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentEventRepository eventRepository;
    private final ObjectMapper objectMapper;
    private final StudentEventPublisher eventPublisher;

    public Student createStudent(String name, LocalDate dateOfBirth) {
        Student student = new Student();
        student.setStudentId(UUID.randomUUID().toString());
        student.setName(name);
        student.setDateOfBirth(dateOfBirth);

        student = studentRepository.save(student);

        StudentEvent event = new StudentEvent();
        event.setStudentId(student.getStudentId());
        event.setEventType(StudentEventType.CREATED);
        event.setVersion(student.getVersion());
        try {
            event.setEventData(objectMapper.writeValueAsString(student));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize student", e);
        }

        eventRepository.save(event);
        eventPublisher.studentEvents().apply(event);

        return student;
    }

    public Student updateStudent(String studentId, String name, LocalDate dateOfBirth) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setName(name);
        student.setDateOfBirth(dateOfBirth);

        student = studentRepository.save(student);

        StudentEvent event = new StudentEvent();
        event.setStudentId(studentId);
        event.setEventType(StudentEventType.UPDATED);
        event.setVersion(student.getVersion());
        try {
            event.setEventData(objectMapper.writeValueAsString(student));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize student", e);
        }

        eventRepository.save(event);
        eventPublisher.studentEvents().apply(event);

        return student;
    }

    public void deleteStudent(String studentId) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));

        studentRepository.delete(student);

        StudentEvent event = new StudentEvent();
        event.setStudentId(studentId);
        event.setEventType(StudentEventType.DELETED);
        event.setVersion(student.getVersion());
        try {
            event.setEventData(objectMapper.writeValueAsString(student));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize student", e);
        }

        eventRepository.save(event);
        eventPublisher.studentEvents().apply(event);
    }

    public List<StudentEvent> getStudentHistory(String studentId) {
        return eventRepository.findByStudentIdOrderByTimestampAsc(studentId);
    }
}
