# Marvel Catalog [WIP]

Repository of the **Marvel Catalog Android App** (iOS soon). Is a totally functional app build in Kotlin following the SOLID principles and clean architecture, using the android guidelines and development tools in jetpack. Is fully modularized using Gradle as build system.

# Features

As an app is a simple listing of the official Marvel API with two sections, the character listing itself and a specific character search, the items in both lists opens a character detail view with a little description and a brief list of comics, stories, series and events of the heroe or villain.

## Screenshots
Listing                    |  Search                   | Detail
:-------------------------:|:-------------------------:|-------------------------
<img src="https://user-images.githubusercontent.com/6061374/197913546-7246e221-baaa-42f2-a09c-4e4bdb4502d4.png" width="200"> | <img src="https://user-images.githubusercontent.com/6061374/197914294-e7b4de3b-4e4f-44b9-b19c-72b8e92ccb72.png" width="200"> | <img src="https://user-images.githubusercontent.com/6061374/197913589-5cba5341-4ec6-4552-bfe2-0d000f93da5a.png" width="200">

# Development Environment

The repository is ready to import in Android Studio, configured with Java 11 and Gradle. In order to request the Marvel API you should add an `auth.properties` file (ignored by VCS) to the root project directory containing two fields `privateKey` and `publicKey`, both keys provided by the Marvel developer platform. Also the keys can be configured as system enviroment variables, the `env var`s configuration is mandatory for the CI system, Github Actions in this case, to be able to run.

# Modularization

The project modularization has some key characteristics in mind loose coupling and high cohesion, easy to start collaborating, testability and low build times. The app components are splited in 3 main modules, `app`, `feature`s and `core`. 

The `app` module is a simple container with the navigation graph and minimum configurations, it depends on all `feature` modules and in none or more `core` modules.

The `feature` modules represents a unit of functionality for the user that involves a view. These do not know anything about other `feature` modules or the `app` module and can have zero or more `core` modules as dependencies.

Finally `core` modules contains components to resolve common task as network request, analytics or databases.

There is an experimental branch where even the clean architecture layers are independant modules inside the feature module, that bring the posibility of share a functionality without presentation in a _backen-ish_ way.

There is a `buildScr` module to provide gradle dependencies and configurations to the entire project. There are some experimental dependency implementations in the `build.gradle` files.

<img width="500" alt="imagen" src="https://user-images.githubusercontent.com/6061374/197924641-81f15e84-311b-42b5-9aea-1d75e2c34e45.png">

# Architecture

The **Marvel Catalog** app follows the SOLID and ![clean architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) principles. Each `feature` module contains 3 main layers `data`, `domain` and `presentation` that comunicate between them throught `interface`s using manual dependency injection, the module graph is internal and is created and requested in runtime from the `Fragment` to provide the `ViewModelProvider.Factory` to the `viewModels` delegate, all other depedencies are file private and passed by contructor.

<img width="500" alt="imagen" src="https://user-images.githubusercontent.com/6061374/197918031-0ab1ebdb-a8a6-4b1d-8d65-8e23a08cdbf1.png">

## Data layer

The app has only a remote data source by `feature` module that use the `core:network` module. It is represented by a Retrofit `interface` requested by the data source suspended functions implementations where the raw Retrofit `Response` is processed to return it corresponding model or error wrapped in the ![Arrow](https://arrow-kt.io/docs/apidocs/arrow-core/arrow.core/-either/) `Either` data type.

The repository implementation orchestrates the data sources and maps the response model to a domain model with the key properties for the feature still wrapped in `Either`.

This layer is requested by the repository `interface` defined in the `domain` layer.

**This layer do not have any android dependencies**. 

## Domain layer

The domain layer does not depend on any layer. It contains the repository definition that expect to returns domain models, also has the app business actions, like get the characters or search for one, these are simple commands because of that they are kotlin functional interfaces. 

This layer is requested by the use case `interface`.

**This layer do not have any android dependencies**. 

## Presentation layer

The app has a single activity as entry point that launch the `:feature:...` modules. Inside the modules the user interfaces are managed by an Android `Fragment` with a bare minimun logic to change elements visibilitiy reactively based on the state and data returned by the viewmodel in the form of a `StateFlow`. 

From this layer all the requests are executed via Kotlin Coroutines using the android specific `CoroutineScope` finally the `ViewModel` is resposible of wraps the domain model to the expected flow.

Navigation is executed via deeplinks.

The app UI supports a simply dark mode based on device configuration.

**Stub `Activity`s, build and `Manifest` implementations are commented for independent module execution.**

# Linting and Testing

## Linting

Detekt and Ktlint tasks are executed in the CI to ensure the style and good practices.

## Unit Test

For these test the main goal besides making a safe net, is to explicit each action inside of the software components what are being tested, as seen in TDD and BDD, using the basis of the Gherkin language.

To achieve this, the unit tests were leveraged on Junit5 and Mockk, in a mix of mocks and fakes. The component under test is a `spy` that is executed with its real behavior and its dependencies are mocks that return fakes. All the tests have assertions besides exhaustive verifications and confirmations.

The tests run for each push and pull request in the CI.

The `di`, `data`, `domain` and `presentation` layer are 100% covered in each feature module.


### Screenshots
Detail                    |  Listing                   
:-------------------------:|:-------------------------:|
<img src="https://user-images.githubusercontent.com/6061374/198316044-fadca4cc-fe93-467b-a182-38a73a179da5.png" width="500"> | <img src="https://user-images.githubusercontent.com/6061374/198313756-2131f9e1-36da-49de-9886-aa36e143a035.png" width="500"> 

## Instrumented Test

The `Fragment`s happy path in each module are tested independendly using Junit4, androidx fragment testing tools and a Espresso wrapper library called Barista, that simplify the assertions and interactions with the UI elements.

# Next Steps

## Development Environment

- Create product flavors.
- Improve Gihub Actions CI (releases and tags creation).
- Add Benchmarks.

## Features

- Implement favorites view.
- Improve search view with filters.
- Add `SnackBar`s for show connections/server issues and retry.

## Architecture And Modularization

- Add a Compose version of UI and presentation layer.
- Add Dagger/Hilt for dependency injection.
- Review the `:feature:common` module to evaluate its scalability.
- Review the design of complex use cases.
- Creation of missing `:core` modules (navigation, test, desing system).
- Creation of local data sources (Favorites feature).

## Test and linting

- Add Spotless.
- Add Jacoco for full code coverage reports.
- Add app instumented test.
- Add navigation test.
- Add mock web server.
- Add Git hooks.
- Add Danger.

## Technical Debt

- Add internal visibility modifier to `:core` and `:feature` module components.
- Fix inconsistent behavior loading the character list when change of section before being loaded.
- Add network state listeners to avoid false requests
- Clean up gradle dependencies experiments.
- Management of string resources in view models.
- Transform `:feature:common` into a Android library.
- Remove error response model from `core:network`.
- Improve poor test definitions and add unhappy path for instrumented test.
- Add full suit of API error tests.
- Instrumented test runs with real requests.
- Improve resources definitions (themes, string, styles, etc.).
- Add missing docs and reference links.

# Contributions

If you find any flawed spot, please feel free to open an issue with your suggestion or fix.

<img src="https://user-images.githubusercontent.com/6061374/198330707-249e8c61-ebe7-40eb-99ec-094854860b32.gif" width="200">

# License

**Marvel Catalog** is distributed under the terms of the Apache License (Version 2.0). See the
[license](LICENSE) for more information.
