# User stories

This document contains all user stories related to the project. Each story contains concrete features that need to be implemented.

## Answer questions (us-1)

As a user, I would like to answer multiple-choice questions, to check my knowledge.

The user needs to be able to start a new quiz, and answer all the questions related to the quiz.

### Important to see

- A button to start the quiz, and view the first question
- A window for each question where the user can see:
    - Four radio buttons mapping to each of the four answers
    - A button to submit answer and move on to the next question
    - Progress in the quiz, which question the user is on and how many are left (format: 11/20)

### Important to do

- Check off multiple-choice questions
- Sumbit answers

## Submit new questions (us-2)

As a user, I would like to create and sumbit new questions to be added to the quiz, so that me and my friends can share questions with each other.

### Important to see

- A button that sends the user to the Create Question-page
- Create Question-page should contain:
    - A text field where you can write the question
    - Four text fields for the different answers
    - Four radio buttons to indicate the correct answer
    - A button to submit the question

### Important to do

- Push button that creates new empty question
- Ability to write question text
- Ability to mark correct answer
- Push button to submit question

## Create and edit quiz(us-3)

As a user, I would like to create a new quiz, and be able to edit this by adding, removing and editing questions, as well as being able to delete any quiz I have created

### Important to see

- A button to create a new quiz and specify quiz name
- An edit button for the quizzes the user has created
- The edit quiz page should contain:
    - List of questions
    - Buttons for removing and editing for each question
    - Button to add question to quiz
    - Button for deleting the entire quiz

### Important to do

- Create new quiz with a custom name
- Ability to edit all quizes created by the user, aka add questions and edit all its questions
- Ability to delete all quizes created by the user


## Multiple quizzes (us-4)

As a user, I would like to select between different quizzes to play.

### Important to see

- A dropdown menu containing all quizzes
    - Names of quizzes
    - Total number of questions
- Ability to view selected quiz

### Important to do

- Select quiz among list of all quizzes

## Username and leaderboard (us-5)

As a user I would like to see my score on a leaderboard after I am done taking the quiz. It should be easy to see which score was mine.

### Important to see

- Scoreboard with names and their respective scores
    - Board should contain current and previous games

### Important to do

- Exit from scoreboard to main page (or play quiz again)

