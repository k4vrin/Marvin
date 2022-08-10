# Marvin

a Movie and Tv Show app built completely with Jetpack Compose.

## About

this project is an educational project for me to teach my self android development in general and Compose framework specifically. i did my best to follow Clean Architecture, Solid and MVVM Design patterns but if it is any recommendation or best practices, i will gladly check issues and pull requests.

also this is an ongoing project and it's likely to have daily commits in main branch

## Prerequisite

In order to be able to build the application you'll need to change the api key in `gradle.properties`. First and formost you need to generate your own api key by [creating](https://www.themoviedb.org/signup) a TMDB account and [generating](https://www.themoviedb.org/settings/api) an api key.

## Tech Stack and Libraries

-   [Kotlin](https://kotlinlang.org/) 100% Kotlin

-   [Jetpack Library](https://developer.android.com/jetpack/getting-started) Jetpack encompasses a collection of Android libraries that incorporate best practices and provide backwards compatibility in your Android apps.

    -   [Jetpack Compose](https://developer.android.com/jetpack/compose) Jetpack Compose is Android’s modern toolkit for building native UI

    -   [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation) Navigation is a framework for navigating between 'destinations' within an Android application that provides a consistent API whether destinations are implemented as Fragments, Activities, or other components.

    -   [Paging](https://developer.android.com/jetpack/androidx/releases/paging) The Paging Library makes it easier for you to load data gradually and gracefully

    -   [Room](https://developer.android.com/jetpack/androidx/releases/room) The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.

-   [Retrofit](https://square.github.io/retrofit/) A type-safe HTTP client for Android

-   [OkHttp](https://square.github.io/okhttp/) OkHttp is an HTTP client that’s efficient by default

-   [Dagger-Hilt](https://dagger.dev/hilt/) Hilt provides a standard way to incorporate Dagger dependency injection into an Android application.

-   [Coil](https://github.com/coil-kt/coil) An image loading library for Android backed by Kotlin Coroutines

-   [compose-collapsing-toolbar](https://github.com/onebone/compose-collapsing-toolbar) A simple implementation of CollapsingToolbarLayout for Jetpack Compose

-   [accompanist](https://github.com/google/accompanist) A collection of extension libraries for Jetpack Compose

    -   [System UI Controller](https://github.com/google/accompanist/blob/main/systemuicontroller) A library that provides easy-to-use utilities for recoloring the Android system bars from Jetpack Compose.

    -   [Pager](https://github.com/google/accompanist/blob/main/pager) A library that provides utilities for building paginated layouts in Jetpack Compose, similar to Android's ViewPager.

    -   [Navigation-Animation](https://github.com/google/accompanist/blob/main/navigation-animation) A library which provides Compose Material support, such as modal bottom sheets, for Jetpack Navigation Compose.

    -   [Swipe to Refresh](https://github.com/google/accompanist/blob/main/swiperefresh) A library that provides a layout implementing the swipe-to-refresh UX pattern, similar to Android's SwipeRefreshLayout.

## Screenshots

| <img src="https://i.imgur.com/eIRYn0t.png" width="250"> | <img src="https://i.imgur.com/hD06sJC.png" width="250"> | <img src="https://i.imgur.com/7GbrGwX.png" width="250"> |
| ------------------------------------------------------- | ------------------------------------------------------- | ------------------------------------------------------- |
| <img src="https://i.imgur.com/oRlkvFa.png" width="250"> | <img src="https://i.imgur.com/vcEiWf4.png" width="250"> | <img src="https://i.imgur.com/MVQ3VSs.png" width="250"> |
| <img src="https://i.imgur.com/WJKhtbV.png" width="250"> | <img src="https://i.imgur.com/SOVncaI.png" width="250"> | <img src="https://i.imgur.com/6bjisMy.png" width="250"> |
| <img src="https://i.imgur.com/x4QQ8IO.png" width="250"> | <img src="https://i.imgur.com/mErjwit.png" width="250"> | <img src="https://i.imgur.com/kk3RLj6.jpg" width="250"> |
| <img src="https://i.imgur.com/nSuNLrl.jpg" width="250"> | <img src="https://i.imgur.com/sEOjJzA.png" width="250"> | <img src="https://i.imgur.com/Btl6f9D.png" width="250"> |

## To Do

-   Local Cache
-   Favorite Functionality
-   Authentication
-   Testing
-   UI Improvement

## Disclaimer

This product uses the TMDB API but is not endorsed or certified by TMDB.
<img src="https://www.themoviedb.org/assets/2/v4/logos/v2/blue_long_1-8ba2ac31f354005783fab473602c34c3f4fd207150182061e425d366e4f34596.svg" width="250">

(it will be included in application)