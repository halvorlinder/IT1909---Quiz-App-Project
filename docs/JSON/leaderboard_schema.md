Type: `Leaderboard`

**_Properties_**

- <b id="#/properties/name">name</b> `required`
    - Type: `string`
- <b id="#/properties/maxScore">maxScore</b> `required`
    - Type: `integer`
- <b id="#/properties/questions">scores</b> `required`
    - Type: `array`
        - **_Items_**
        - Type: `Score`
        - **_Properties_**
            - <b id="#/properties/questions/items/properties/name">name</b> `required`
                - Type: `string`
            - <b id="#/properties/questions/items/properties/score">score</b> `required`
                - Type: `integer`