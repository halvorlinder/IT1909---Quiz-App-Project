<!-- PROJECT LOGO -->
[![coverage report](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/badges/main/coverage.svg)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/commits/main)
[![pipeline status](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/badges/main/pipeline.svg)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/commits/main)
<h1 align="center">Quiz App</h1>

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#about-the-project">Project Structure</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li>
        <a href="#Core">Core</a>
        <ul>
            <li><a href="#core">core</a></li>
            <li><a href="#io">io</a></li>
        </ul>
    </li>
    <li>
        <a href="#UI">UI</a>
        <ul>
            <li><a href="#ui">ui</a></li>
            <li><a href="#resources">Resources</a></li>
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

<!-- GETTING STARTED -->

## Getting Started

To get a local copy up and running follow these simple steps.

* Run the app from terminal
  ```sh
  mvn clean install
  ```

* Run tests and test coverage
  ```sh
  mvn verify
  ```

### Installation

* Clone the repo
   ```sh
   git clone https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114.git
   ```

<!-- Core structure -->

## Core

### Core

[Question](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/core/src/main/java/core/Question.java)
- Contains the information of a single question with choices and a correct answer.

[Quiz](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/core/src/main/java/core/Quiz.java)
- Handles a quiz session by keeping track of list of questions.

[User](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/core/src/main/java/core/User.java)
- Contains the information of a single user, with a set- and get-function for the username.

[UserData](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/core/src/main/java/core/UserData.java)
- Handles User-objects. Links usernames to hashed passwords.

### io

[QuizPersistance](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/core/src/main/java/io/QuizPersistence.java) - Take Json Object of quiz(zes) and persist

[UserPersistance](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/core/src/main/java/io/UserPersistence.java) - Take Json Object user(s) and persist

**Internal:**

[QuestionDeserializer](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/core/src/main/java/io/internal/QuestionDeserializer.java) 
- Convert data related to Question from String format to Json Object

[QuestionSerializer](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/core/src/main/java/io/internal/QuestionSerializer.java) 
- Convert Json object of Question to a String format

[QuizAppModule](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/core/src/main/java/io/internal/QuizAppModule.java) 
- A Jackson module for configuring JSON serialization of QuizAppModule instances

[QuizDeserializer](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/core/src/main/java/io/internal/QuizDeserializer.java) 
- Convert data related to Quiz from String format to Json Object

[QuizSerializer](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/core/src/main/java/io/internal/QuizSerializer.java) 
- Convert Json object of Quiz to a String format

[UserDataDeserializer](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/core/src/main/java/io/internal/UserDataDeserializer.java) 
- Convert data related to UserData from String format to Json Object

[UserDataSerializer](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/core/src/main/java/io/internal/UserDataSerializer.java) 
- Convert Json object of UserData to a String format


## UI

### ui

[App](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/ui/src/main/java/ui/App.java)
- Launches the Quiz App

[Utilities](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/ui/src/main/java/ui/Utilities.java)
- A utility class 

**Controllers:**

[HomepageController](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/ui/src/main/java/ui/controllers/HomePageController.java)
- Controller for homepage

[NewQuestionController](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/ui/src/main/java/ui/controllers/NewQuestionController.java)
- Controller for new question page

[QuizController](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/ui/src/main/java/ui/controllers/QuizController.java)
- Controller for quiz page

[ResultPageController](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/ui/src/main/java/ui/controllers/ResultPageController.java)
- Controller for final score after taking the quiz

[LogInController](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/ui/src/main/java/ui/controllers/LogInController.java)
- Controller for creating a profile and logging in

### Resources

[HomePage fxml](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/ui/src/main/resources/ui/HomePage.fxml)
- Fxml for the homepage

[New Question fxml](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/ui/src/main/resources/ui/NewQuestion.fxml)
- Fxml for the new question-page

[Quiz fxml](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/ui/src/main/resources/ui/QuestionPage.fxml)
- Fxml for the quiz-page

[Result Page fxml](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/ui/src/main/resources/ui/ResultPage.fxml)
- Fxml for the result-page after the quiz is done

[Log In fxml](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/Quiz-app/ui/src/main/resources/ui/ResultPage.fxml)
- Fxml for the log in-page
<!-- ROADMAP -->

##Architecture

The following diagrams represent the macro architecture of the app:

![image](docs/Diagrams/CreateQuestion.png)  
Sequence diagram of what happens when a user creates a new question  

![image](docs/Diagrams/SubmitQuestion.png)  
Sequence diagram of what happens when a user submits an answer in an ongoing quiz  

![image](docs/Diagrams/LogInAttempt.png)  
Sequence diagram of what happens when a user attempts to log in 

![image](docs/Diagrams/LogInInit.png)  
Sequence diagram of what happens when the log in page is initialized

### Storage choices

We have chosen to use implicit file saving. The reasoning behind this is that the user has no use for specifying how and where files are stored. This also contributes to a smoother user experience. 


## Roadmap

See the [open issues](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/issues) for a list of proposed
features (and known issues).



<!-- CONTACT -->

## Contact

Jørgen Sandhaug - [email](joreksa@stud.ntnu.no)

Project
Link: [https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114.git](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114.git)









