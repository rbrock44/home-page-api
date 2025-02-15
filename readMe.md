# Home Page Api Readme

This api was built to power the [Home Page website](https://home-page.ryan-brock.com/)/[repo](https://github.com/rbrock44/home-page) <br/>

## Controllers/Handlers

* Cleaning Schedule
  * handles meeting requests and in memory h2 database with initial seeder
* Fight Card
  * handles fight card requests
    * scraps mma website for fights
* GamesPerDate
  * handles basketball and football requests
    * scraps espn website for games
* Gdq (Games Done Quick)
  * handles gdq requests
    * scraps gdq website for sessions
* Home Media Search
  * handles home media search requests
    * searches h2 database for matches of home media names
* Media
  * handles syncing home media requests
    * has way to sync with database/source

<br/>
Scripts used for some syncing data with source - (Scripts repo)[https://github.com/rbrock44/scripts]

## Deploy
This application is hosted on heroku: https://home-page-api.ryan-brock.com
``git push heroku master``

Heroku automatically manages SSL (that was turned on 2025/02/15)
