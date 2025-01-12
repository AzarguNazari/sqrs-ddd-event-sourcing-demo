CREATE TABLE students (
  student_id VARCHAR(36) PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  date_of_birth DATE NOT NULL,
  version BIGINT NOT NULL
);

CREATE TABLE student_events (
    event_id VARCHAR(36) PRIMARY KEY,
    student_id VARCHAR(36) NOT NULL,
    event_type VARCHAR(50) NOT NULL,
    event_data JSONB NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    version BIGINT NOT NULL
);