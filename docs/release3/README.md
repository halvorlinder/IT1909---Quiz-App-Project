# Release 3
This is the documentation for the third release

The gitlab sprint can be found [here](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/milestones/3)  
For a more detailed description of the project, please consult the project [README](../../README.md)

## Functionality
This release was split into two main parts: revamping the UI and adding new functionality,
and the development of a REST API and a way to interact with it.

### New functionality
We added the ability to view and play multiple quizzes. 
You can now edit existing questions in a quiz, delete questions or create new ones. 
You can also create and delete entire quizzes. With this change, we decided to change our UI to better fit our new functionality. 
This release also saw the implementation of the leaderboard feature discussed in 
[release 2](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/blob/main/docs/release2). Furthermore, we 
have improved the error messages that are displayed to the user. They now cover more error cases. They are also more descriptive.

### REST API  

The implementation of the API was an important element of this sprint., 
because it allowed us to completely separate the user interface from the persistence logic.
The API handles saving, loading, editing, deletion and authorization. The API can be found [here](../API_Documentation.md).

### User Stories

### Create and edit quiz(us-3)

As a user, I would like to create a new quiz, and be able to edit this by adding, removing and editing questions

#### Important to see

- A button to create a new quiz and specify quiz name
- An edit button for the quizzes the user has created
- List of questions with buttons for removing and editing for each question
- Button to add question to quiz

#### Important to do

- Create new quiz with a custom name
- Ability to edit this quiz, aka add questions and edit all its questions

### Multiple quizzes (us-4)

As a user, I would like to select between different quizzes to play.

#### Important to see

- A dropdown menu containing all quizzes
    - Names of quizzes
    - Total number of questions
- Ability to view selected quiz

#### Important to do

- Select quiz among list of all quizzes

### Username and leaderboard (us-5)

As a user I would like to see my score on a leaderboard after I am done taking the quiz. It should be easy to see which score was mine.

#### Important to see

- Scoreboard with names and their respective scores
    - Board should contain current and previous games

#### Important to do

- Exit from scoreboard to main page (or play quiz again)




## Workflow 

This release saw us continuing our commitment to an agile workflow.
Our goal is small, short-lived branches that address one specific issue. 
This way we avoid unnecessary merge conflicts that need to be resolved manually. 
We improved our work efficiency by shortening the time used to pass (or fail) a pipeline, 
by optimizing our tests.
We have also created issue templates for more clear communication. 
In addition to this we have started using a shared commit format that revolves around providing a short header and a 
more expansive description.  
All commits, except for documentation, are linked to a branch, which itself linkes to a single issue. This is not visible in the history section, but the issue can be found by clicking on the issue and then clicking on the merge request it is linked to


## Design

The designs for this release are the new page to view quizzes, the leaderboard page and the edit quiz page. We also wrote CSS for the entire app, making it look more like the figma sketches.
The designs are made using figma and can found [here](https://www.figma.com/file/fIa83jzzjFGX31jdjN8C2o/Untitled?node-id=12%3A2)

## Meetings

We have met up two times a week for a scrum meeting. We try to meet physically, if possible, as this made it easier to collaborate. In these meetings we have discussed our progress since last time, blockers as well as what tasks each developer plans to complete before next meeting. During this sprint we have also had multiple work sessions where the group has worked on the app together.


## Structure
The project is seperated in three modules; core, rest and ui.
 - The core module handles internal logic and computations
 - The rest module handles everything related to the REST API
 - The ui module handles the user interfaces, and user input

The rest module is new and contains the rest-api and the backend server. In addition to this 
there is also a module named ReportAggregator. The sole goal of this module is to combine test coverage files
into a single file. 

## Frameworks

The project is built with maven. 
 - Javafx is used for handling the GUI.
 - Jacoco is used for measuring test coverage.
 - Checkstyle is used for checking code style and code quality.
 - Spotbugs is used to catch potential bugs.
 - Jackson is used for handling JSON data.
 - Spring is used for the REST services.
 - HTTPUrlConnection is used for communicating with the API.
