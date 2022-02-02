# HackChange-App

This is a social app for investors. This app was created during Hack&Change hackathon by Changellenge.

## Functionality

- Your profile - upload photo or change your name.
- Your portfolio - see all your blogposts and stats (e.g. how many subscribers you have)
- Topics (e.g. currency, shares, precious metals) and stocks in topics (e.g. Tesla, Gold, Dollar)
- Observe all posts about stock
- Watch and leave comments on posts
- Subscribe to bloggers
- Observe posts by bloggers you are subscribed on

## Technologies

App is written in pure Kotlin (using Android Jetpack toolkit)

### Communication

- Communicates with [server](https://github.com/KennelTeam/HackChange-Server) over http.
- Uses volley to perform requests
- Requests are made only when app asks (no requirements in live data updating) and it simplifies architecture

### UI

- Uses standard NavigationFragment
- Uses standard android ui elements
- [Design in Figma](https://www.figma.com/file/OujJjGNyh0lr7c90OyACiR/Hack-%26-Change?node-id=0%3A1)

## Problems

- Sometimes fails to update user info (especially avatar)

## Ways to improve

- Improve UI
- Add features such as post's views, post's rating
