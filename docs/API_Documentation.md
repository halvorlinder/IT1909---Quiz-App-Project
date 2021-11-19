| Method | Path | Header | Body | Description | Response |
| ------ | ------ | ------ | ------ | ------ | ------ |
| GET | localhost:8080/api/quizzes | - | - | Responds with a list containing the names of all quizzes | ["quizname1", "quizname2"] |
| GET | localhost:8080/api/quizzes/{quizname} | - | - | Responds with a quiz given its name | {"name" : "quizname", "creator" : "creatorname", "questions" : [{"question" : "questiontext", "answer" : 0, "choices" : ["a","b","c","d"]}]} |
| POST | localhost : 8080/api/quizzes | - | {"name" : "quizname", "creator" : "creatorname", "questions" : [{"question" : "questiontext", "answer" : 0, "choices" : ["a","b","c","d"]}]} | Saves the quiz on the server | {"name" : "quizname", "creator" : "creatorname", "questions" : [{"question" : "questiontext", "answer" : 0, "choices" : ["a","b","c","d"]}]} |
| POST | localhost : 8080/api/quizzes/{quizname} | Authorization : "token" | {"question" : "questiontext", "answer" : 0, "choices" : ["a","b","c","d"]} | Adds a question the specified quiz | {"name" : "quizname", "creator" : "creatorname", "questions" : [{"question" : "questiontext", "answer" : 0, "choices" : ["a","b","c","d"]}]} |
| PUT | localhost : 8080/api/quizzes/{quizname}/{index} | Authorization : "token" | {"question" : "questiontext", "answer" : 0, "choices" : ["a","b","c","d"]} | Updates a question a question at the specified index in the specified quiz | {"name" : "quizname", "creator" : "creatorname", "questions" : [{"question" : "questiontext", "answer" : 0, "choices" : ["a","b","c","d"]}]} |
| DELETE | localhost : 8080/api/quizzes/{quizname} | Authorization : "token" | - | Deletes the quiz with the specified name | - |
| DELETE | localhost : 8080/api/quizzes/{quizname}/{index} | Authorization : "token" | - | Deletes the question at the specified index in the specified quiz | - |
| GET | localhost : 8080/api/leaderboards/{name} | - | - | Responds with the leaderboard of the quiz with the specified name | {"name" : "quizname", "maxScore" : 5, "scores" : [{"name" : "username", "points" : 0}]} |
| POST | localhost : 8080/api/leaderboards/{name} | - | {"name" : "username", "points" : 0} | Submits a score to the scoreboard of the quiz with the specified name | {"name" : "quizname", "maxScore" : 5, "scores" : [{"name" : "username", "points" : 0}]} |
| POST | localhost : 8080/api/users/login | - | {"username" : "username", "password" : 23797} | Responds with an access token if the username and password correlate to a registered user | "wuyygfuwetr7i4trayergfrbgifera" |
| POST | localhost : 8080/api/users/register | - | {"username" : "username", "password" : 23797} | Responds with an access token if the username is not taken | "wuyygfuwetr7i4trayergfrbgifera" |


