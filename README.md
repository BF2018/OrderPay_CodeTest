Software Development Approach
=============================

This software was developed using an Agile and TDD methodology

## Architecture Design
The Project follows a MVVM with Repository pattern architecture. This architecture was chosen for:
    • Seperation of Concerns that provides a way to testing the architecture components in isolation and allows for the View classes to be updated without modifying the ViewModel classes.
    • Resilience to configuration changes allows the ViewModel classes to store UI data that would otherwise be lost on screen rotation or      activity lifecycle changes.
    • Communication between fragments using a ViewModel class removes the need for fragments to communicate via an Activity using callbacks.
The View classes use data binding to communicate updates to their respective ViewModel classes. The ViewModel classes communicate with a Repository class using coroutines and receives responses using LiveData. This is then passed back to the View classes observing this LiveData. The Repository class communicates with a RESTful API using Retrofit and caches the response to a local Room database.

## Libraries Used
    • Koin to provide constructor dependency injection to classes in the application
    • Retrofit to provide access to the backend API endpoints
    • WorkManager to retrieve flight data from the API service in the background
    • Coroutines to run API network requests on background threads
    • AndroidX to provide Lifecycle and LiveData functionality to the app
    • Room to store the Flight responses from Retrofit.
    • Data binding to bind the inflated layout files to instances running in the application code.
    • Espresso to perform instrumentation tests on the user interface
    • Mockito to mock the ViewModel and Repository classes

 ## Alternative Libraries
For instrumentation testing Robolectric could be used and for unit testing the MockK would be ideal since it uses Kotlin DSL to support mocking test cases. 
Volley could be used as an alternative networking library for Retrofit and lastly, Dagger Hilt could be used as an alternative dependency injection library.

 ## Further Improvements
    • Refactor project to use Pagination 3 that has been developed with Kotlin coroutines in mind.
 ## Screen shots 
 ![Home Screen](home_screen.png) ![Detail Screen](detail_screen.png)
