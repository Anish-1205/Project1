# 📚 Quiz Platform

A full-stack web application for creating and taking timed quizzes with instant scoring and detailed result analysis.

---

## 🎯 Features

- ✅ **Quiz Selection** - Browse and select from available quizzes
- ✅ **Timed Attempts** - Each quiz has a duration limit
- ✅ **Multiple Choice Questions** - Single correct answer per question
- ✅ **Real-time Answer Validation** - Instant feedback on submissions
- ✅ **Automatic Scoring** - Percentage-based score calculation
- ✅ **Detailed Results** - View correct/incorrect breakdown
- ✅ **Concurrent Users** - Support for multiple simultaneous users
- ✅ **Responsive UI** - Works on desktop and tablet

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────┐
│              React Frontend (Port 3000)              │
│  ┌─────────────────────────────────────────────┐    │
│  │  QuizListing  →  QuizAttempt  →  Results   │    │
│  └─────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────┘
                        ↕ REST API
┌─────────────────────────────────────────────────────┐
│          Spring Boot Backend (Port 8080)             │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────┐   │
│  │  Controller  │→ │   Service    │→ │Repository│   │
│  └──────────────┘  └──────────────┘  └──────────┘   │
└─────────────────────────────────────────────────────┘
                        ↕ JPA/Hibernate
┌─────────────────────────────────────────────────────┐
│        H2 In-Memory Database (Development)          │
│         (Switch to PostgreSQL for production)        │
└─────────────────────────────────────────────────────┘
```

---

## 🚀 Quick Start

### Prerequisites
- **Java 17+** ([Download](https://www.oracle.com/java/technologies/downloads/#java17))
- **Node.js 16+** ([Download](https://nodejs.org/))
- **Maven 3.6+** ([Download](https://maven.apache.org/))
- **Git** ([Download](https://git-scm.com/))

### Installation

#### 1️⃣ Clone the Repository
```bash
git clone https://github.com/Anish-1205/Quiz_platform.git
cd Quiz_platform
```

#### 2️⃣ Backend Setup

```bash
cd backend

# Build with Maven
mvn clean package

# Run the Spring Boot application
java -jar target/quiz-platform-1.0.0.jar
```

Backend will start on **http://localhost:8080**

✅ Verify it's running:
```bash
curl http://localhost:8080/api/quizzes
# Should return: [{"id":1,"title":"JavaScript Fundamentals",...}]
```

#### 3️⃣ Frontend Setup

```bash
cd frontend

# Install dependencies
npm install

# Start the React development server
npm start
```

Frontend will open at **http://localhost:3000**

---

## 📖 Usage

### 1. View Available Quizzes
- Browser opens to **Quiz Listing** page
- Shows all available quizzes with:
  - Quiz title and description
  - Duration (in minutes)
  - Number of questions

### 2. Start a Quiz
- Click **"Start Quiz"** on any quiz card
- Backend creates an attempt record
- Frontend displays first question

### 3. Answer Questions
- Select one answer per question
- Click **"Next"** to move to next question
- Progress bar shows your position

### 4. Submit Quiz
- After answering all questions, click **"Finish"**
- Backend calculates your score
- View detailed results page

### 5. View Results
- See your final score (percentage)
- Letter grade (A/B/C)
- Breakdown of correct vs incorrect answers
- Click **"Back to Home"** to attempt another quiz

---

## 🗄️ Database Schema

### Tables

**quizzes**
```
id (PK)          | BIGINT
title            | VARCHAR(255)
description      | TEXT
duration         | INT (minutes)
```

**questions**
```
id (PK)          | BIGINT
quiz_id (FK)     | BIGINT → quizzes
text             | TEXT
```

**options**
```
id (PK)          | BIGINT
question_id (FK) | BIGINT → questions
text             | VARCHAR(500)
is_correct       | BOOLEAN
```

**user_attempts**
```
id (PK)          | BIGINT
user_id          | VARCHAR(100)
quiz_id (FK)     | BIGINT → quizzes
start_time       | DATETIME
end_time         | DATETIME
score            | INT (percentage)
```

**responses**
```
id (PK)                    | BIGINT
attempt_id (FK)            | BIGINT → user_attempts
question_id (FK)           | BIGINT → questions
selected_option_id (FK)    | BIGINT → options
UNIQUE(attempt_id, question_id)
```

### Sample Data

The application comes with **3 pre-loaded quizzes**:
1. **JavaScript Fundamentals** (10 min, 2 questions)
2. **React Concepts** (15 min, 2 questions)
3. **Spring Boot** (20 min, 2 questions)

---

## 🔌 API Endpoints

### Base URL
```
http://localhost:8080/api
```

### Endpoints

#### Get All Quizzes
```http
GET /quizzes
```
**Response:**
```json
[
  {
    "id": 1,
    "title": "JavaScript Fundamentals",
    "description": "Test your JavaScript knowledge",
    "duration": 10,
    "questionCount": 2
  }
]
```

#### Get Quiz Details
```http
GET /quizzes/{quizId}
```
**Response:**
```json
{
  "id": 1,
  "title": "JavaScript Fundamentals",
  "duration": 10,
  "questions": [
    {
      "id": 1,
      "text": "What is the correct way to declare a variable?",
      "options": [
        {"id": 1, "text": "var x = 5;", "isCorrect": null},
        {"id": 2, "text": "let x = 5;", "isCorrect": null}
      ]
    }
  ]
}
```

#### Start Quiz Attempt
```http
POST /quizzes/{quizId}/attempt/start?userId={userId}
```
**Response:**
```json
{
  "attemptId": 5,
  "quizId": 1,
  "startTime": "2025-02-16T20:45:00",
  "endTime": "2025-02-16T20:55:00"
}
```

#### Submit Answer
```http
POST /quizzes/attempt/{attemptId}/response
Content-Type: application/json

{
  "questionId": 1,
  "selectedOptionId": 3
}
```
**Response:**
```json
{
  "responseId": 1,
  "isCorrect": true,
  "message": "Recorded"
}
```

#### Submit Quiz
```http
POST /quizzes/attempt/{attemptId}/submit
```
**Response:**
```json
{
  "attemptId": 5,
  "score": 75,
  "correctAnswers": 2,
  "totalQuestions": 3,
  "incorrectAnswers": 1,
  "details": [
    {
      "questionId": 1,
      "questionText": "What is JS?",
      "userAnswer": "A programming language",
      "correctAnswer": "A programming language",
      "isCorrect": true
    }
  ]
}
```

#### Get Results
```http
GET /quizzes/attempt/{attemptId}/result
```
**Response:** (Same as Submit Quiz)

---

## 📦 Technology Stack

### Frontend
| Technology | Version | Purpose |
|------------|---------|---------|
| React | 18.2.0 | UI framework |
| React Router | 6.8.0 | Client-side routing |
| Axios | 1.3.0 | HTTP client |
| React Scripts | 5.0.1 | Build tools |

### Backend
| Technology | Version | Purpose |
|------------|---------|---------|
| Spring Boot | 3.1.5 | Application framework |
| Spring Web | 3.1.5 | REST API support |
| Spring Data JPA | 3.1.5 | Database ORM |
| H2 Database | 2.1.214 | In-memory database |
| Java | 17 | Programming language |

---

## ⚙️ Configuration

### Backend Configuration
Located at: `backend/src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:quiz_db
    driver-class-name: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false

server:
  port: 8080
```

**Key Settings:**
- `ddl-auto: validate` - Validates schema, doesn't modify database
- `port: 8080` - Backend server port
- H2 in-memory database - Reset on each restart

### Frontend Configuration
Located at: `frontend/package.json`

```json
{
  "proxy": "http://localhost:8080"
}
```

**Key Settings:**
- Default port: 3000
- CORS enabled on backend

---

## 🧪 Testing the Application

### 1. Test Backend APIs with cURL

```bash
# Get all quizzes
curl http://localhost:8080/api/quizzes

# Get specific quiz
curl http://localhost:8080/api/quizzes/1

# Start an attempt
curl -X POST "http://localhost:8080/api/quizzes/1/attempt/start?userId=user_test"

# Submit an answer
curl -X POST http://localhost:8080/api/quizzes/attempt/1/response \
  -H "Content-Type: application/json" \
  -d '{"questionId":1,"selectedOptionId":4}'

# Submit quiz
curl -X POST http://localhost:8080/api/quizzes/attempt/1/submit
```

### 2. Test with Browser

1. Open http://localhost:3000
2. Click "Start Quiz"
3. Answer questions
4. Click "Finish"
5. View results

### 3. View Database (H2 Console)

Open: **http://localhost:8080/h2-console**
- JDBC URL: `jdbc:h2:mem:quiz_db`
- Username: `sa`
- Password: (leave empty)

---

## 🐛 Troubleshooting

### Issue: "API returns empty array []"
**Solution:** Change `ddl-auto` in `application.yml` from `create-drop` to `validate`, then rebuild.

### Issue: Frontend shows "Loading..." forever
**Solution:** Check browser console (F12) for errors. Verify backend is running on port 8080.

### Issue: Port 8080 is already in use
**Solution:** Change port in `application.yml`:
```yaml
server:
  port: 8081
```

### Issue: Port 3000 is already in use
**Solution:** Change port in frontend:
```bash
PORT=3001 npm start
```

### Issue: H2 console not loading
**Solution:** Verify H2 is enabled in `application.yml`:
```yaml
h2:
  console:
    enabled: true
```

---

## 🚀 Deployment

### Production Deployment Checklist

#### Backend
- [ ] Change database to PostgreSQL
- [ ] Set `ddl-auto: none` (don't auto-migrate)
- [ ] Enable HTTPS
- [ ] Configure authentication (JWT)
- [ ] Set up logging (SLF4J)
- [ ] Configure error handling
- [ ] Add API documentation (Swagger)
- [ ] Set up monitoring (Prometheus, Grafana)
- [ ] Configure rate limiting
- [ ] Build Docker image

#### Frontend
- [ ] Run production build: `npm run build`
- [ ] Set correct API_URL environment variable
- [ ] Enable gzip compression
- [ ] Set up CDN for static assets
- [ ] Configure service workers
- [ ] Test on mobile devices
- [ ] Deploy to Netlify/Vercel/AWS S3

#### Database
- [ ] Migrate to PostgreSQL
- [ ] Set up backups
- [ ] Configure replication
- [ ] Set up monitoring
- [ ] Optimize indexes

---

## 📈 Performance Optimization

### Current Bottlenecks
1. **No caching** - Quiz list is fetched on every load
2. **No pagination** - All quizzes loaded at once
3. **No indexing optimization** - Basic indexes only
4. **No compression** - API responses not compressed

### Recommended Improvements
```java
// 1. Add caching
@Service
@CacheConfig(cacheNames = "quizzes")
public class QuizService {
  @Cacheable
  public List<QuizListDTO> getAllQuizzes() { ... }
}

// 2. Add pagination
@GetMapping
public Page<QuizListDTO> getAllQuizzes(Pageable pageable) { ... }

// 3. Enable gzip compression
server:
  compression:
    enabled: true
    min-response-size: 1024

// 4. Add database indexes
CREATE INDEX idx_user_quiz ON user_attempts(user_id, quiz_id);
```

---

## 🔒 Security Considerations

### Current Implementation
- ✅ Server-side answer validation
- ✅ Unique constraint on responses
- ✅ CORS enabled for frontend
- ⚠️ No authentication (uses timestamp-based user ID)
- ⚠️ No input validation
- ⚠️ No rate limiting

### Recommended Additions
1. **JWT Authentication** - Replace timestamp-based user ID
2. **Input Validation** - Add @Valid annotations
3. **Rate Limiting** - Prevent brute force
4. **HTTPS** - Encrypt data in transit
5. **SQL Injection Prevention** - Use parameterized queries (already done via JPA)
6. **CSRF Protection** - Add tokens for state-changing operations

---

## 📚 Project Structure

```
Quiz_platform/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/quizapp/
│   │   │   │   ├── controller/
│   │   │   │   │   └── QuizController.java
│   │   │   │   ├── service/
│   │   │   │   │   ├── QuizService.java
│   │   │   │   │   └── AttemptService.java
│   │   │   │   ├── repository/
│   │   │   │   │   ├── QuizRepository.java
│   │   │   │   │   ├── QuestionRepository.java
│   │   │   │   │   ├── OptionRepository.java
│   │   │   │   │   ├── UserAttemptRepository.java
│   │   │   │   │   └── ResponseRepository.java
│   │   │   │   ├── entity/
│   │   │   │   │   ├── Quiz.java
│   │   │   │   │   ├── Question.java
│   │   │   │   │   ├── Option.java
│   │   │   │   │   ├── UserAttempt.java
│   │   │   │   │   └── Response.java
│   │   │   │   ├── dto/
│   │   │   │   │   ├── QuizListDTO.java
│   │   │   │   │   ├── QuizDetailDTO.java
│   │   │   │   │   ├── AttemptStartDTO.java
│   │   │   │   │   ├── ResponseSubmitDTO.java
│   │   │   │   │   └── ResultDTO.java
│   │   │   │   └── QuizApplication.java
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       └── schema.sql
│   │   └── test/
│   ├── pom.xml
│   └── target/
│       └── quiz-platform-1.0.0.jar
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   │   ├── QuizListing.jsx
│   │   │   ├── QuizAttempt.jsx
│   │   │   └── ResultPage.jsx
│   │   ├── App.jsx
│   │   ├── index.js
│   │   └── App.css
│   ├── public/
│   │   └── index.html
│   ├── package.json
│   └── node_modules/
│
└── README.md
```

---

## 🤝 Contributing

Contributions are welcome! Here's how to contribute:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### Areas for Contribution
- [ ] Add timer implementation
- [ ] Improve error handling
- [ ] Add input validation
- [ ] Implement authentication
- [ ] Add more quizzes/questions
- [ ] Improve UI/UX
- [ ] Add unit tests
- [ ] Add integration tests
- [ ] Performance optimization
- [ ] Database migration

---

## 🗺️ Roadmap

### Phase 1: Core Features ✅
- [x] Quiz listing page
- [x] Quiz attempt flow
- [x] Answer submission
- [x] Score calculation
- [x] Results page

### Phase 2: Enhancements (In Progress)
- [ ] Timer implementation
- [ ] Better error handling
- [ ] Input validation
- [ ] Mobile responsiveness
- [ ] UI/UX improvements

### Phase 3: Advanced Features (Planned)
- [ ] User authentication (JWT)
- [ ] User profiles and statistics
- [ ] Quiz creation UI
- [ ] Admin dashboard
- [ ] WebSocket support (real-time updates)
- [ ] Difficulty levels
- [ ] Category filters
- [ ] Leaderboard

### Phase 4: Production Ready (Future)
- [ ] Comprehensive test suite
- [ ] Performance optimization
- [ ] Security hardening
- [ ] Monitoring and logging
- [ ] Docker/Kubernetes support
- [ ] CI/CD pipeline

---

## 📝 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

## 👤 Author

**Anish Keesari**
- GitHub: [@Anish-1205](https://github.com/Anish-1205)
- Email: [keesarianish@gmail.com]

---

## 📊 Statistics

- **Lines of Code (Backend)**: ~500
- **Lines of Code (Frontend)**: ~300
- **Database Tables**: 5
- **API Endpoints**: 6
- **React Components**: 3
- **Pre-loaded Quizzes**: 3
- **Pre-loaded Questions**: 6
- **Pre-loaded Options**: 16

---

## ✨ Acknowledgments

- Spring Boot documentation
- React documentation
- Stack Overflow community
- Contributors and testers

---

**Made with ❤️ by Anish**

⭐ If this project helped you, please give it a star!
