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
- [How to Contribute](#-how-to-contribute)

---

## 🧠 What's My Purpose?

This is a Kotlin + Spring Boot backend API that powers multiple frontend projects by aggregating data from web sources and local repositories, then exposing that data through REST endpoints.

Primary features include:

- Cleaning schedule CRUD (H2-backed, API key protected on write operations)
- Sports and event scraping (MMA, ESPN, GDQ, auction data)
- Home media search + sync from remote source files
- Flash card and spot price data endpoints
- Pending recipe submission endpoints (API key protected), which trigger a GitHub `repository_dispatch` to [family-recipes](https://github.com/rbrock44/family-recipes) to kick off recipe processing

---

## 🚦 How to Use

Run the API, then call endpoints from your frontend or via cURL/Postman.

Common route groups:

- `/cleaning-schedule`
  - `GET /cleaning-schedule/week?startDate=YYYY-MM-DD`
  - `GET /cleaning-schedule`
  - `POST /cleaning-schedule/add` (requires `X-API-Key` header)
  - `POST /cleaning-schedule/edit` (requires `X-API-Key` header)
  - `DELETE /cleaning-schedule?id={id}` (requires `X-API-Key` header)
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
- `/recipe/pending` (all routes require `X-API-Key` header)
  - `GET /recipe/pending`
  - `POST /recipe/pending`
  - `DELETE /recipe/pending/{id}`

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
- Framework: `Spring Boot 4`
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

Environment variables:

- `API_KEY=your_secret_key` — **required**, even locally; the app fails to start without it. Protects `/recipe/pending` (all routes)
- `CLEANING_SCHEDULE_API_KEY=your_secret_key` — optional. Protects the write routes on `/cleaning-schedule`; if unset, those routes reject every request rather than being open
- `GITHUB_DISPATCH_TOKEN=your_github_token` — optional. Enables the `repository_dispatch` sent to `family-recipes` on new recipe submissions; if unset, the dispatch is skipped (logged, not an error)

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
- Deployment to Heroku happens automatically via GitHub Actions on push to `master`

---

### Build

Run `./gradlew build` to compile and package the project.

Build artifacts are generated under `build/libs/`.

---

### Deploy

This application is hosted on Heroku.

Pushing to `master` on GitHub automatically triggers a [GitHub Action](.github/workflows/deploy.yml) that deploys to Heroku - no manual step needed.

To deploy manually instead:

```bash
git push heroku master
```

Heroku handles SSL for the deployed app.

---

## 🤝 How to Contribute

Found a typo or a small, obvious fix? Open a PR directly.
Want to change behavior or add something bigger? Open an issue first so we can talk it through before you put in the work.

