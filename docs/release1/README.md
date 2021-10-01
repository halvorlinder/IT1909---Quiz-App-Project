#Release 1
This is the documentation for the first release

The gitlab sprint can be found [here](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2114/gr2114/-/milestones/1)

##Functionality

The main goal of the app is to let users create and answer quiz questions. The functionality in this release confines 
itself to letting users add questions to a local pool of questions, as well as allowing the user to answer these 
questions. Feedback is also given after all questions have been completed. 

##User Stories

### Svare på spørsmål (us-1)

Som bruker vil jeg svare på quiz-spørsmål med flervalg, slik at jeg kan teste kunnskapen min.

Brukeren har behov for å kunne starte en ny quiz og deretter svare på alle spørsmålene i quizen.

#### Viktig å kunne se

- En knapp for å starte quizen og gå til første spørsmål
- Et vindu for hvert spørsmål der bruker kan se:
    - Fire radio-buttons som mapper til de fire svaralternativene
    - En knapp for å sende inn svar og gå til neste spørsmål
    - Hvilket spørsmål brukeren er på og hvor mange som er igjen (format: 11/20)

#### Viktig å kunne gjøre

- Huke av på flervalgsspørsmål
- Sende inn svar

### Sende inn nye spørsmål (us-2)

Som bruker ønsker jeg å opprette og legge til nye spørsmål i quizen, slik at jeg og vennene mine kan dele spørsmål 
med hverandre

#### Viktig å kunne se

- En knapp for å gå til opprett spørsmål-siden
- På denne skal man kunne se
    - Et tekstfelt til spørsmålsteksten
    - Fire tekstfelter for svaralternativer
    - Fire radio buttons for å markere riktig svar
    - En knapp for å sende inn spørsmål

#### Viktig å kunne gjøre

- Trykke på knappen for å opprette et nytt spørsmål
- Skrive inn spørsmålsteksten
- Markere riktig svar
- Trykke på knappen for å sende inn spørsmål


##Workflow 

The first thing we did after defining the user stories, was to create a set of issues that would have to 
be completed in order to resolve the user stories. The issues have labels to indicate the following:
- The urgency of completion
- The domain of work (frontend/backend)
- The type of task (feature/fix...)

Each issue has during the sprint been assigned to a single developer for completion.
When an issue is finished, the developer creates a merge request in gitlab, which is then reviewed by a 
different developer. The reviewer can then accept the changes, or request changes if some part of the code 
is unsatisfactory. 
In addition to code review, we have also utilized pair programming as a development technique in some cases.


##Design

Issues that require the creation or alteration of GUI-elements, have images attached to them. Design has
not been our main priority in this sprint, and there is therefore not a one-to-one correspondence between
the app and the designs. 
The designs are made using figma and can found [here](designs.md)

##Meetings

We have met up two times a week for a scrum meeting. In these meetings we have discussed our progress since last time,
blockers as well as what tasks each developer plans to complete before next meeting.


##Structure

The project is seperated in two modules; core and ui.
The ui module handles the user interface, while the core module handles computations and logic. It also
has a package for handling file storage.

##Frameworks

The project is built with maven. 
We use javafx for handling the GUI.
Jacoco is used for measuring test coverage.