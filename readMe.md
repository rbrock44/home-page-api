# HomePageApi

> Backend API for my personal dashboard ecosystem (Home Page, Cleaning Schedule, Flash Cards, Utilities, and more) <br/>
> [Live - Home Page API](https://home-page-api.ryan-brock.com/)

---

## 📚 Table of Contents

- [What's My Purpose?](#-whats-my-purpose)
- [How to Use](#-how-to-use)
- [Technologies](#-technologies)
- [Getting Started (Local Setup)](#-getting-started-local-setup)
  - [Run Locally](#run-locally)
  - [Test](#test)
  - [GitHub Hooks](#github-hooks)
  - [Build](#build)
  - [Deploy](#deploy)

---

## 🧠 What's My Purpose?

This is a Kotlin + Spring Boot backend API that powers multiple frontend projects by aggregating data from web sources and local repositories, then exposing that data through REST endpoints.

Primary features include:

- Cleaning schedule CRUD (H2-backed)
- Sports and event scraping (MMA, ESPN, GDQ, auction data)
- Home media search + sync from remote source files
- Flash card and spot price data endpoints
- Pending recipe submission endpoints (with API key protection on write operations)

---

## 🚦 How to Use

Run the API, then call endpoints from your frontend or via cURL/Postman.

Common route groups:

- `/cleaning-schedule`
  - `GET /cleaning-schedule/week?startDate=YYYY-MM-DD`
  - `GET /cleaning-schedule`
  - `POST /cleaning-schedule/add`
  - `POST /cleaning-schedule/edit`
  - `DELETE /cleaning-schedule?id={id}`
- `/games-per-date`
  - `GET /games-per-date/basketball/today`
  - `GET /games-per-date/basketball/upcoming`
  - `GET /games-per-date/football/today`
  - `GET /games-per-date/football/upcoming`
- `/fight-card`
  - `GET /fight-card/today`
  - `GET /fight-card/upcoming`
- `/gdq`
  - `GET /gdq/upcoming`
- `/auction`
  - `GET /auction/today`
  - `GET /auction/upcoming`
- `/home-media-search`
  - `GET /home-media-search?criteria=...`
- `/media`
  - `GET /media/update`
- `/flash-cards`
  - `GET /flash-cards`
- `/spot-price`
  - `GET /spot-price`
- `/recipe/pending`
  - `GET /recipe/pending`
  - `POST /recipe/pending` (requires `X-API-Key` header)
  - `DELETE /recipe/pending/{id}` (requires `X-API-Key` header)

Example:

```bash
curl "http://localhost:8090/cleaning-schedule/week?startDate=2026-05-31"
```

Protected write example:

```bash
curl -X POST "http://localhost:8090/recipe/pending" \
  -H "Content-Type: application/json" \
  -H "X-API-Key: your_api_key" \
  -d '{"title":"Recipe Name","url":"https://example.com/recipe"}'
```

---

## 🛠 Technologies

- Language: `Kotlin`
- Framework: `Spring Boot 3`
- Build Tool: `Gradle (Kotlin DSL)`
- Java Version: `21`
- Database: `H2 (in-memory)`
- Parsing/Scraping: `Jsoup`
- Testing: `JUnit 5`, `Spring Boot Test`, `Mockito`
- Deployment: `Heroku`

---

## 🚀 Getting Started (Local Setup)

- Install [Java 21](https://adoptium.net/)
- Clone [repo](https://github.com/rbrock44/home-page-api)

Optional environment variable for protected endpoints:

- `API_KEY=your_secret_key`

---

### Run Locally

```bash
./gradlew bootRun
```

The app runs on `http://localhost:8090` by default.

---

### Test

- Unit/Integration
  - `./gradlew test`
- Single test class
  - `./gradlew test --tests "com.projects.homepageapi.services.CleaningScheduleServiceTest"`

---

### GitHub Hooks

- No GitHub hook deployment is required for local development
- Deployment is currently done through Heroku Git push flow

---

### Build

Run `./gradlew build` to compile and package the project.

Build artifacts are generated under `build/libs/`.

---

### Deploy

This application is hosted on Heroku.

```bash
git push heroku master
```

Heroku handles SSL for the deployed app.

