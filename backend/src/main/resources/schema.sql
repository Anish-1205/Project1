CREATE TABLE IF NOT EXISTS quizzes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    duration INT NOT NULL
);

CREATE TABLE IF NOT EXISTS questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quiz_id BIGINT NOT NULL,
    text TEXT NOT NULL,
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE,
    INDEX idx_quiz (quiz_id)
);

CREATE TABLE IF NOT EXISTS options (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_id BIGINT NOT NULL,
    text VARCHAR(500) NOT NULL,
    is_correct BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_attempts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    quiz_id BIGINT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME,
    score INT DEFAULT 0,
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE,
    INDEX idx_user (user_id),
    INDEX idx_quiz (quiz_id)
);

CREATE TABLE IF NOT EXISTS responses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    attempt_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    selected_option_id BIGINT NOT NULL,
    FOREIGN KEY (attempt_id) REFERENCES user_attempts(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE,
    FOREIGN KEY (selected_option_id) REFERENCES options(id) ON DELETE CASCADE,
    UNIQUE KEY unique_response (attempt_id, question_id)
);

INSERT INTO quizzes (title, description, duration) VALUES
('JavaScript Fundamentals', 'Test your JavaScript knowledge', 10),
('React Concepts', 'Learn React hooks', 15),
('Spring Boot', 'Spring Boot microservices', 20);

INSERT INTO questions (quiz_id, text) VALUES
(1, 'What is the correct way to declare a variable in JavaScript?'),
(1, 'Which is NOT a valid JavaScript data type?'),
(2, 'What is the purpose of useEffect hook?'),
(2, 'How do you pass data from parent to child in React?'),
(3, 'What is the main advantage of Spring Boot?'),
(3, 'How do you define a REST endpoint?');

INSERT INTO options (question_id, text, is_correct) VALUES
(1, 'var x = 5;', false),
(1, 'let x = 5;', false),
(1, 'const x = 5;', false),
(1, 'All of the above', true),
(2, 'String', false),
(2, 'Number', false),
(2, 'Alphabet', true),
(2, 'Boolean', false),
(3, 'Handle side effects', true),
(3, 'Manage styling', false),
(4, 'Through Props', true),
(4, 'Through state', false),
(5, 'Reduces boilerplate', true),
(5, 'Faster than Spring', false),
(6, '@RestController', true),
(6, '@Service', false);