# Home Page Api Readme

This api was built to power the home-page [website](https://rbrock44.github.io/home-page/)/[repo](https://github.com/rbrock44/home-page)

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
    * searches text file for matches of home media names
* Media
  * handles syncing home media requests
    * has way to sync with database/source


## Deploy
This application is hosted on heroku: https://home-page-api-34607.herokuapp.com

``git push heroku master``