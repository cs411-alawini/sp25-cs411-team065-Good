# 🧳 TravelMate – Backend

## 📦 Project Structure

This backend follows a **multi-module** design:
<pre>
travel-mate/
├── travel-mate-app         # Application entrypoint (main class)
├── travel-mate-interface   # Controller + API layer
├── travel-mate-service     # Business logic (Service layer)
├── travel-mate-repository  # Data access (MyBatis mapper, PO)
├── travel-mate-common      # Shared classes (VO, enums, etc.)
</pre>

---

## 🚀 Tech Stack

- **Java 17**
- **Spring Boot**
- **MyBatis**
- **MySQL**
- **Maven**

---

## ⚙️ How to Run

> Requires Java 17+ and MySQL running.

### 1. Clone the repo

```bash
git clone https://github.com/cs411-alawini/sp25-cs411-team065-Good.git
cd sp25-cs411-team065-Good/travel-mate
```
### 2. Configure database
Edit `travel-mate-app/src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/TravelMate
    username: root
    password: your_password
```
> You can also point to your GCP Cloud SQL or Docker container if applicable.
### 3. Run the app
```bash
cd travel-mate-app
mvn spring-boot:run
```
App will start on: [http://localhost:8080](http://localhost:8080)

---
## 📚 API Example
```bash
GET http://localhost:8080/api/attractions/state?state=Illinois
```
Returns a list of top-rated attractions in that state.

---
## ✍️ Authors
- **Team 65** @ UIUC