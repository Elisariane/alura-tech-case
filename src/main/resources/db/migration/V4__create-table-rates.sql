CREATE TABLE rates (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    score INT NOT NULL CHECK (score >= 1 AND score <= 10),
    rate_description VARCHAR(255),
    course_id BIGINT NOT NULL,
    FOREIGN KEY (course_id) REFERENCES courses(id)
);