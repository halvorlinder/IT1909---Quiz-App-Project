| Method | Path | Header | Body | Description | Response |
| ------ | ------ | ------ | ------ | ------ | ----- |
| GET | localhost:8080/api/quizzes | - | - | Responds with a list containing the names of all quizzes | ["quizname1", "quizname2"] |
| GET | localhost:8080/api/quizzes/{quizname} | - | - | Responds with a quiz given its name | ;;; {"name":"quizname", "creator":"creatorname", "questions":[{"question":"questiontext", "answer":0, "choices":["a","b","c","d"]}]} ;;; |
| POST | localhost:8080/api/quizzes | - | {"name":"quizname", "creator":"creatorname", "questions":[{"question":"questiontext", "answer":0, "choices":["a","b","c","d"]}]} | Saves the quiz on the server | {"name":"quizname", "creator":"creatorname", "questions":[{"question":"questiontext", "answer":0, "choices":["a","b","c","d"]}]} |
| POST | localhost:8080/api/quizzes/{quizname} | Authorization:"token" | {"question":"questiontext", "answer":0, "choices":["a","b","c","d"]} | Adds a question to the server | {"name":"quizname", "creator":"creatorname", "questions":[{"question":"questiontext", "answer":0, "choices":["a","b","c","d"]}]} |
