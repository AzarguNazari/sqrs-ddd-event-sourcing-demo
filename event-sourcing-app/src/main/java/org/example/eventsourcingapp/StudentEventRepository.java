package org.example.eventsourcingapp;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentEventRepository extends JpaRepository<StudentEvent, String> {
    List<StudentEvent> findByStudentIdOrderByTimestampAsc(String studentId);
}
