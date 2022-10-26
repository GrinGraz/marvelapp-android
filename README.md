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

<img width="700" alt="imagen" src="https://user-images.githubusercontent.com/6061374/197924641-81f15e84-311b-42b5-9aea-1d75e2c34e45.png">

# Architecture

The **Marvel Catalog** app follows the SOLID and ![clean architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) principles. Each `feature` module contains 3 main layers `data`, `domain` and `presentation` that comunicate between them throught `interface`s using manual dependency injection, the module graph is internal and is created and requested in runtime from the `Fragment` by a function that provides the `ViewModel`, all other depedencies are file private and passed by contructor.

<img width="700" alt="imagen" src="https://user-images.githubusercontent.com/6061374/197918031-0ab1ebdb-a8a6-4b1d-8d65-8e23a08cdbf1.png">

## Data layer

The app has only a remote data source by `feature` module that use the `core:network` module. It is represented by a Retrofit `interface` requested by the data source implementation where the raw Retrofit `Response` is processed to return it corresponding model or error wrapped in the `Either` data type.

The repository implementation, defined in the domain, orchestrate the datasources and map the response model to a domain model with the key properties for the feature still wrapped in `Either`.

This layer is requested by the repository interface defined in the domain layer.

**This layer do not have any android dependencies**. 

## Domain layer

The domain layer does not depend on any layer. It contains the repository definition that expect to returns domain models, also has the app business actions, like get the characters or search for one, these are simple commands because of that they are functional interfaces. 

This layer is requested by the use case interface.

**This layer do not have any android dependencies**. 

## Presentation layer
TBC

# License

**Marvel Catalog** is distributed under the terms of the Apache License (Version 2.0). See the
[license](LICENSE) for more information.
