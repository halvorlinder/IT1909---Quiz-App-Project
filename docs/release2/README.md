# Release 1
This is the documentation for the second release

The gitlab sprint can be found [here](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/milestones/2)

## Functionality

The second release is based around testing, code quality and streamlining our productivity.
We have also introduced a new user system, which allows users to register, and later log into the app. Error handling has also been improved. A modal window now displays errors whenever something goes wrong in the app (like for example an unsuccessful log in).We had also planned to add the ability to create quizzes and to choose which quiz to play, as well as leaderboards that utilize the user system. We did, however not manage to implement these features during the sprint, and we therefore have to postpone these features to next release. We could probably have rushed these features out, but we chose to instead focus on code quality.

## User Stories

### Multiple quizzes (us-3)

As a user, I would like to select between different quizzes to play depending on genre.

#### Important to see

- A dropdown menu containing all quizzes
    - Names of quizzes
    - Total number of questions
- Ability to view selected quiz

#### Important to do

- Select quiz among list of all quizzes

### Username and leaderboard (us-4)

As a user I would like to see my score on a leaderboard after I am done taking the quiz. It should be easy to see which score was mine.

#### Important to see

- Scoreboard with names and their respective scores
    - Board should contain current and previous games

#### Important to do

- Exit from scoreboard to main page (or play quiz again)




### Workflow 

As previously mentioned we've focused on improving our agile workflow during this sprint. This has been achieved through a number of factors.  
We have continued to ensure code quality through code reviews. The code reviews are now faster and easier due to the introduction of a Gitlab ci pipeline which runs all our core/io tests automatically whenever code is pushed to the remote or a merge request is created. The pipeline also runs Checkstyle and Spotbugs, as well as Jacoco for test coverage. If any of the tests fail, or if there are Checkstyle/Spotbugs errors, the merge request is halted until these issues have been adressed. This allows us to merge code into the main branch with higher confidence that the main branch doesn't break.
The test coverage is also displayed as a badge on the project in addition to on each merge request. This is achieved through a Gitlab merge request template.  
In the future we plan on displaying the test coverage reports in gitlab after the pipeline is done. 
In addition to this we have utilized pair programming to a higher degree. We believe that this has helped us in our effort to write clean reusable and functional code.



## Design

The designs for this release are the new design for the login page and a redesign of the home page.
The designs are made using figma and can found [here](designs.md)

## Meetings

We have met up two times a week for a scrum meeting. In these meetings we have discussed our progress since last time,
blockers as well as what tasks each developer plans to complete before next meeting.
During this sprint we have also had multiple work sessions where the group has worked on the app together.


## Structure

The project is seperated in two modules; core and ui.
The ui module handles the user interface, while the core module handles computations and logic. The core module has also now been made responsible for handling persistence with Jackson.

## Frameworks

The project is built with maven. 
We use javafx for handling the GUI.
Jacoco is used for measuring test coverage.
Checkstyle is used for checking code style and code quality.
Spotbugs is used to catch potential bugs.
Jackson is used for handling JSON data.
