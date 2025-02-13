<!-- PROJECT LOGO -->
[![coverage report](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/badges/main/coverage.svg)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/commits/main)
[![pipeline status](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/badges/main/pipeline.svg)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/commits/main)
<h1 align="center">Quiz App</h1>

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of contents</h2></summary>
  <ol>
    <li>
      <a href="#project-structure">Project structure</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
        <li><a href="#checkstyle-settings">Checkstyle settings</a></li>
        <li><a href="#jlink">Jlink</a></li>
        <li><a href="#jpackage">Jpackage</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#terminal">Run from terminal</a></li>
        <li><a href="#create-a-shippable-product">Create a shippable product</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li>
      <a href="#about-the-API">About the API</a>
      <ul>
        <li><a href="#description">Description</a></li>
      </ul>
      <ul>
        <li><a href="#restrictions">Restrictions</a></li>
      </ul>
      <ul>
        <li><a href="#documentation">Documentation</a></li>
      </ul>
    </li>
    <li>
        <a href="#core">Core</a>
        <ul>
            <li><a href="#core">core</a></li>
            <li><a href="#io">io</a></li>
            <li><a href="#internal">Internal</a></li>
            <li><a href="#constants">Constants</a></li>
            <li><a href="#json-format">Json format</a></li>
        </ul>
    </li>
    <li>
        <a href="#ui">UI</a>
        <ul>
            <li><a href="#ui">ui</a></li>
            <li><a href="#constants">Constants</a></li>
            <li><a href="#controller">ui</a></li>
            <li><a href="#resources">Resources</a></li>
        </ul>
    </li>
    <li>
        <a href="#rest">Rest</a>
        <ul>
            <li><a href="#rest">Rest components</a></li>
        </ul>
    </li>
    <li>
        <a href="#repportaggregator">ReportAggregator</a>
        <ul>
            <li><a href="#rest">Rest components</a></li>
        </ul>
    </li>
    <li>
        <a href="#architecture">Architecture</a>
        <ul>
            <li><a href="#package-diagram">Package diagram</a></li>
            <li><a href="#class-diagram">Class diagram</a></li>
            <li><a href="#sequence-diagram">Sequence diagram</a></li>
            <li><a href="#storage-choices">Storage choices</a></li>
        </ul>
    </li>
    <li>
    <a href="#tests">Tests</a>
    <ul>
    <li><a href="#unit-test">Unit test</a></li>
    <li><a href="#integration-test">Integration test</a></li>
    <li><a href="#system-test">System test</a></li>
    </ul>
    </li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->

## Project Structure

<p>
The project is seperated into modules; the core and the ui. The core module contains classes that handle logic and app functionality, and has no direct impact on the users communication with the app. The ui module howewer, contains classes and resources that handle the GUI presented to the user.
</p>

### Built With

* Java
* JavaFX
* Maven
* Json and Jackson
* Jacoco
* Checkstyle
* Spotbugs
* Spring boot
* Jpackage and jlink
* Tomcat

#### Checkstyle settings
We use a default checkstyle template, with minor changes:
- Line length: We think that the line lenght restriction in the template is a bit to strict so we have upped it to 120
- End Of Line Operator: We specified that operators should be at end of line rather than the start, due to IntelliJ formattiing.
- Hidden Field: We disabed this check because we think that the this.field syntax is useful and clean
- Magic number: We changed this from error to warning, because we want them in some cases, but seldomly.
- Javadoc Grammar: We disabled grammar checks in javadoc, because this is unimportant

#### Spotbugs settings
We have edited a few spotbugs settings:
- Disabled unused field in controllers that arent linked to a FXML file, because the fields are used but the compiler doesn't understand it

#### Jlink
The JLink goal is intended to create a Java Run Time Image. Description of a few of the parameters we use
- MainClass: What main needs to be launched by specifying module, package and class. We set it to ui.App since the application is in the UI module. 
- Compress: Enables compression of resources, we set to 2 for compressing to ZIP
- StripDebug: Excludes JRE debug information
- noManPages: Excludes the JDK docs
- Launcher: The executable name
#### JPackage
- Name: Set the name ofthe application
- Destination: Sets the destination folder for the generated package 
- Module: Set the module and the main class of our application since JPackage will generate a new executable for our package 
- Runtimeimage: We set the parameter to target/quizfx to macth the path of the jlink Runtimeimage


<!-- GETTING STARTED -->

## Getting Started

To get a local copy up and running follow these simple steps. Everything needs to be run from the Quiz-app directory


### Terminal
* Run the app from terminal
  ```sh
  mvn clean compile
  mvn javafx:run -pl ui
  ```

* Run tests and test coverage
  ```sh
  mvn clean compile
  mvn verify
  ```
* Run the server
  ```sh
  mvn spring-boot:run -pl rest
  ```
### Create a shippable product
* For gitpod. Remember to run the server before you start the application.
  ```sh
  mvn clean install
  sudo apt update && sudo apt install fakeroot
  mvn javafx:jlink jpackage:jpackage -pl ui
  sudo apt install ./ui/target/dist/quizfx_1.0.0-1_amd64.deb
  ```
  Now the program can be found in /opt/quizfx/

* Run the application created by jpackage 
  ```sh
  /opt/quizfx/bin/QuizFX
  ```

### Installation

* Clone the repo
   ```sh
   git clone https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114.git
   ```


## About the API

### Description

The api has endpoints that allow creation of quizzes, editing of quizzes and deletion of quizzes.
The quizzes can also be fetched.
It also has endpoints for login and user registration as well as score submission and leaderboard fetching.

### Restrictions

To alter a quiz an active access token needs to be supplied.
The token can be retrieved by login in or registering.
It is also worth to mention that only the user that created a quiz has editing
rights.

The name of a quiz acts as a unique key and no two quizzes can share names.

### Documentation

The API documentation can be found [here](docs/API_Documentation.md)


<!-- Core structure -->

## CORE

### core

- [Question](Quiz-app/core/src/main/java/core/Question.java) - Contains the information of a single question with choices and a correct answer

- [Quiz](Quiz-app/core/src/main/java/core/Quiz.java) - Stores a list of questions aswell as a name for the quiz and a creator for the quiz 

- [QuizSession](Quiz-app/core/src/main/java/core/QuizSession.java) - Handles a quiz session. It iterates over the questions in a quiz according to the input from the user

- [UserData](Quiz-app/core/src/main/java/core/UserData.java) - Handles User-objects. Links usernames to hashed passwords. Handles actions like registration and logins from the user 

- [UserRecord](Quiz-app/core/src/main/java/core/UserRecord.java) - Contains the username and hashed password. 

- [Leaderboard](Quiz-app/core/src/main/java/core/Leaderboard.java) - Contains the name of the quiz related to this leaderboard, list of all the scores and the highest score you could get on this quiz 

- [Score](Quiz-app/core/src/main/java/core/Score.java) - Contains the name of a user and the number of points

### constants

- [Constants](Quiz-app/core/src/main/java/io/constants/Constants.java) - A class that contains all magic numbers as contants for the core module. 

### io

- [QuizPersistance](Quiz-app/core/src/main/java/io/QuizPersistence.java) - Handles the saving of quiz(zes) and loading of quiz(zes) 

- [UserPersistance](Quiz-app/core/src/main/java/io/UserPersistence.java) - Handles the saving of users and loading of users

- [LeaderBoardPersistance](Quiz-app/core/src/main/java/io/LeaderBoardPersistence.java) - Handles the saving and loading of leaderboard(s)

- [SavePaths](Quiz-app/core/src/main/java/io/SavePaths.java) - A soulution to select a different directory for filestorage during testing

**Internal:**

- [QuestionDeserializer](Quiz-app/core/src/main/java/io/internal/QuestionDeserializer.java) - Convert data related to Question from String format to Json Object

- [QuestionSerializer](Quiz-app/core/src/main/java/io/internal/QuestionSerializer.java) - Convert Json object of Question to a String format

- [QuizAppModule](Quiz-app/core/src/main/java/io/internal/QuizAppModule.java) - A Jackson module for configuring JSON serialization of QuizAppModule instances

- [QuizDeserializer](Quiz-app/core/src/main/java/io/internal/QuizDeserializer.java) - Convert data related to Quiz from String format to Json Object

- [QuizSerializer](Quiz-app/core/src/main/java/io/internal/QuizSerializer.java) - Convert Json object of Quiz to a String format

- [UserDataDeserializer](Quiz-app/core/src/main/java/io/internal/UserDataDeserializer.java) - Convert data related to UserData from String format to Json Object

- [UserDataSerializer](Quiz-app/core/src/main/java/io/internal/UserDataSerializer.java) - Convert Json object of UserData to a String format

- [LeaderBoardDeserializer](Quiz-app/core/src/main/java/io/internal/UserDataDeserializer.java) - Convert data related to Leaderboard from String format to Json Object

- [LeaderBoardSerializer](Quiz-app/core/src/main/java/io/internal/LeaderBoardSerializer.java) - Convert Json object of UserData to a String format

- [ScoreDeserializer](Quiz-app/core/src/main/java/io/internal/Score.java) - Convert data related to Score from String format to Json Object

- [ScoreSerializer](Quiz-app/core/src/main/java/io/internal/Score.java) - Convert Json object of Score to a String format

- [UserRecordDeserializer](Quiz-app/core/src/main/java/io/internal/UserRecordDeserializer.java) - Convert data related to UserRecord from String format to Json Object

- [UserRecordSerializer](Quiz-app/core/src/main/java/io/internal/UserRecordSerializer.java) - Convert Json object of UserRecord to a String format

### JSON Format 
The JSON schemas for classes that are serialized can be found here:  
- [Quiz](docs/JSON/quiz_schema.md)
- [Question](docs/JSON/question_schema.md)
- [UserData](docs/JSON/user_data_schema.md)
- [UserRecord](docs/JSON/user_record_schema.md)
- [Leaderboard](docs/JSON/leaderboard_schema.md)
- [Score](docs/JSON/score_schema.md)

## UI

### ui

- [User](Quiz-app/ui/src/main/java/ui/User.java) - Contains the information of a single user, with a set- and get-function for the username

- [App](Quiz-app/ui/src/main/java/ui/App.java) - Launches the Quiz App

- [Utilities](Quiz-app/ui/src/main/java/ui/Utilities.java) - A utility class 

- [APIClientService](Quiz-app/ui/src/main/java/ui/APIClientService.java)  - Send request from the client side to the server side. Handles all actions: login, registration, adding a quiz, deleting a quiz, adding a question to the quiz, updating a question, deleting a question and posting score to leaderboard.

**Controllers:**

- [HomePageController](Quiz-app/ui/src/main/java/ui/controllers/HomePageController.java) - Controller for homepage

- [NewQuestionPageController](Quiz-app/ui/src/main/java/ui/controllers/NewQuestionPageController.java) - Controller for new question page

- [QuizPageController](Quiz-app/ui/src/main/java/ui/controllers/QuizPageController.java) - Controller for quiz page

- [ResultPageController](Quiz-app/ui/src/main/java/ui/controllers/ResultPageController.java) - Controller for final score after taking the quiz

- [LogInController](Quiz-app/ui/src/main/java/ui/controllers/LogInController.java) - Controller for creating a profile and logging in

- [BaseController](Quiz-app/ui/src/main/java/ui/controllers/BaseController.java) - Abstract controller extented by all controller classes

- [EditPageController](Quiz-app/ui/src/main/java/ui/controllers/EditPageController.java) - Controller for editing a quiz, where you can delete, change or add a question

- [InitilizableController](Quiz-app/ui/src/main/java/ui/controllers/InitilizableController.java) - Interfaced implemented by all controller-classes, initializes the controller

- [LeaderboardPageController](Quiz-app/ui/src/main/java/ui/controllers/LeaderboardPageController.java) - Controller for showing the leaderboard

- [GoBackController](Quiz-app/ui/src/main/java/ui/controllers/GoBackController.java) - Controller for going back to previous scene

### constants

- [Constants](Quiz-app/core/src/main/java/constants/Constants.java) - A class that contains all magic numbers as contants for the ui module. 

### Resources

- [HomePage fxml](Quiz-app/ui/src/main/resources/ui/HomePage.fxml) - Fxml for the homepage

- [New Question fxml](Quiz-app/ui/src/main/resources/ui/NewQuestion.fxml) - Fxml for the new question-page

- [Quiz fxml](Quiz-app/ui/src/main/resources/ui/QuestionPage.fxml) - Fxml for the quiz-page

- [Result Page fxml](Quiz-app/ui/src/main/resources/ui/ResultPage.fxml) - Fxml for the result-page after the quiz is done

- [Log In fxml](Quiz-app/ui/src/main/resources/ui/ResultPage.fxml) - Fxml for the log in-page


- [Edit Page fxml](Quiz-app/ui/src/main/resources/ui/EditPage.fxml) - Fxml for the edit page

- [Leaderboard Page fxml](Quiz-app/ui/src/main/resources/ui/LeaderboardPage.fxml) - Fxml for the edit page

## REST

### rest

- [AuthHandler](Quiz-app/rest/src/main/java/rest/AuthHandler.java) - Handles authentication of user, so that the user can only change a quiz they have made themselves. We have made our own auth system that works by sending users an access token upon login, which they can later use to verify themselves when accessing endpoints in the api.


- [QuizController](Quiz-app/rest/src/main/java/rest/QuizController.java) - Defines the API endpoints, by mapping requests from the user by taking the request, path and body to a given method in this controller. Returns the server response

- [QuizServerApllication](Quiz-app/rest/src/main/java/rest/QuizServerApllication.java) - initializes the Spring Boot server


## ReportAggregator 
This modules only purpose is to generate a testcoverage file that covers the entire project

<!-- ROADMAP -->

## Architecture

The following diagrams represent the macro architecture of the app:

### Package diagram
 - The following diagrams represent the macro architecture of the app: [package/architecture diagram](Quiz-app/diagrams/architecture.png)


### Class diagram

A class diagram is a diagram that describes the structure of a system by showing the system's classes, their attributes, operations (or methods), and the relationships among objects

 - The following diagram represents a class diagram of the core: [core class diagram](Quiz-app/diagrams/coreClassDiagram.png)

 - The following diagram represents a class  of the rest: [rest class diagram](Quiz-app/diagrams/restClassDiagram.png)

 - The following diagram represents a class diagram of the ui: [ui class diagram](Quiz-app/diagrams/uiClassDiagram.png)

### Sequence diagram

A sequence diagram is a type of interaction diagram because it describes how—and in what order—a group of objects works together.

 - The following diagram represents a sequence diagram of submitting a question in the new question page: [diagram](Quiz-app/diagrams/NewQuestionPageController_submitQuestionSequenceDiagram.png)
 - The following diagram represents a sequence diagram of going to the edit quiz page : [diagram](Quiz-app/diagrams/HomePageController_showEditPageSequenceDiagram.png)


### Storage choices

We have chosen to use implicit file saving. The reasoning behind this is that the user has no use for specifying how and where files are stored. This also contributes to a smoother user experience. 


## Roadmap

See the [open issues](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/issues) for a list of proposed
features (and known issues).


## Tests

The level of testing we are covering in this project are unit test, integration test and system test. The test for are properly documented in the code.

### Unit test

A Unit is the smallest testable portion of system or application which can be compiled, liked, loaded, and executed. This kind of testing helps to test each module separately. 

### Integration test

Integration means combining. For Example, In this testing phase, different software modules are combined and tested as a group to make sure that integrated system is ready for system testing.

### System test

System testing is performed on a complete, integrated system. It allows checking system’s compliance as per the requirements. It tests the overall interaction of components. It involves load, performance, reliability and security testing.
<!-- CONTACT -->

## Contact

Jørgen Sandhaug - [email](joreksa@stud.ntnu.no)

Project
Link: [https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114.git](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114.git)









