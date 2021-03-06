---
geometry: margin=1in
---
# PROJECT Design Documentation

> _The following template provides the headings for your Design
> Documentation.  As you edit each section make sure you remove these
> commentary 'blockquotes'; the lines that start with a > character
> and appear in the generated PDF in italics._

## Team Information
* Team name: Team 5
* Team members
  * William Tietjen
  * Anthony Salvati
  * Nabeel Khan
  * Trevor Cruz
  * Kyle Reddy

## Executive Summary

This is a summary of the project.

### Purpose

This project is a checkers web app split into 3 tiers, UI, model,
and application. The checkers game follows the American rules. The most
important user group is the players with a focus on user goals such as
signin, starting a game, game functionality, and several enhancements.


### Glossary and Acronyms
> _Provide a table of terms and acronyms._

| Term | Definition |
|------|------------|
| VO | Value Object |


## Requirements

The application being built had relatively simple requirements. One major requirement was that the user needed to be
able to sign in to the application in order to be able to play the game. After that, it was required that the user have
access to the list of currently online players so that they could begin a match. Once inside a match, it was necessary
for the players to both be able to play by the American rules of checkers. Lastly, the players needed to know when the 
match was over, either because their opponent conceded or because a player had lost all of their pieces. 

These, however, were just for the core function of the game. In addition to this, enhancement had to be made to separate 
our game of checkers from the rest. To do this, a spectator mode and a replay mode had to be added. Spectating required
that the user be able to see the list of available games so they could join and watch. Alongside this, the player must 
not be able to move the pieces or interact with the game in any sort of way. This idea carried over into the replay mode
as well. 

The replay mode had its own requirements, too. For example, similar to the spectator mode, on the main menu the user 
had to be able to see the games that were available to re-watch move by move. It also required that once the user was in
the replay mode, they could either see the next move in the sequence of the game or the revert to see the last move that
was made. Just as in the spectator mode once again, the user was to be unable to interact with the board in any way 
other than viewing the next and previous moves. 

### Definition of MVP
The MVP must allow users to sign-in in order to play
a game and sign-out when they are done. Fully functioning games
are expected that follow the [American Rules](https://www.se.rit.edu/~swen-261/projects/WebCheckers/American%20Rules.html) 
of checkers. Player's of a game are able to resign at certain
points which will immediately end the game.

### MVP Features
* Start a Game - As a Player I want to start a game so that I can play checkers with an opponent
* Player Sign-in - As a Player I want to sign-in so that I can play a game of checkers
* Making a Move - As a	Player I want to make a move if I have one so that I don???t lose the game
* Signing Out - As a player I want to be able to sign out so that I can retire my username when I am done
* Alternate Turns - As a Player I want my turn to end after I???ve made a move so that my opponent can move
* Resigning - As a Player I want to be able to resign so that I can play another game
* Capture Pieces - As a Player I want to capture a piece after a jump so that I get ahead in the game
* Kinging a Piece - As a Player I want to have my piece turn into a king if I reach the end of the board so that I can move back down the board
* Exiting a Game - As a player I want to be able to exit the game when it is over so that I can start a new one

### Roadmap of Enhancements
* Replay Game - users will have the ability to go back and 
  review their old games turn by turn.
* Spectate Game - other users will have the option to spectate
  ongoing games as they happen.


## Application Domain

This section describes the application domain.

![The WebCheckers Domain Model](Domain-Model.png)

The application starts out with a user interface allowing the player to sign in. The sign in 
requires the user to provide a unique username, which is checked by the PlayerLobby.
The GameManager connects two players,one red and one white, and assigns them to a CheckersGame. 
The game consists of a CheckerBoard which holds all of the CheckerPieces that players move around. 
Each player takes a turn during the CheckersGame, moving a piece until a player has no pieces or resigns. 
A move consists of either a simple move or a jump, which can be done multiple times if available. 




## Architecture and Design

The architecture of this program was set up into a three-tiered system consisting of a UI tier, Model tier, and
Application tier. The UI Tier was what the end user interacted with to play the game. The current implementation of the 
UI tier has quite a few classes, mostly because quite a few routes needed both a POST and a GET route in order for the 
program to function properly. This was kept separate but interacted quite heavily with the Application tier, which was 
the go-between for the UI and Model tiers. The Application tier was a bit light in terms of number of classes in the 
end, largely because there was not a lot of different ways for the user to interact with the UI outside of signing in 
and playing or watching a game. Lastly, the Model tier had no interaction with the UI at all and only communicated 
with the Application tier. This was done to maintain proper high cohesion. That might sound counterintuitive, but the 
design decision came down to the fact that the Application tier handling interaction between the UI and Model would keep
individual classes more concise and allow for them to require less coupling. 

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

As a web application, the user interacts with the system using a
browser.  The client-side of the UI is composed of HTML pages with
some minimal CSS for styling the page.  There is also some JavaScript
that has been provided to the team by the architect.

The server-side tiers include the UI Tier that is composed of UI Controllers and Views.
Controllers are built using the Spark framework and View are built using the FreeMarker framework.  The Application and Model tiers are built using plain-old Java objects (POJOs).

Details of the components within these tiers are supplied below.


### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts
with the WebCheckers application.

![The WebCheckers Web Interface Statechart](WebcheckersStatechartSprint3.png)

Upon reaching the home page, a user who has not signed in will be given a sign-in button. When they click the sign-in
button they will be sent to the sign-in page where they must enter a username in a text box and click the submit button. 
Once they do so successfully, they will be taken back to the homepage, this time in the signed-in state. A list of
currently signed-in players will now be visible to this user. When the user clicks on a player's name in this list they 
will be placed in a new checkers game with them if they are still signed-in and not already in a game, otherwise the 
user will be given an error message, and they will remain on the homepage.

Once in a game, the player will have a board in front of them, a UI display that tells them which pieces they control,
and a set of buttons which will swap between being clickable and non-clickable based on the game state. Once it is their
turn in the game, the player will be given the ability to move a piece on the board to a different space. If the move
they perform is impossible the piece will not be placed in the new location and a message explaining why will be
displayed in the info section. If the player makes a valid move, the space the piece moved from and the new space the
piece is on will appear green. If the player decides that their valid move is not the one they wish to make, they can
press the "Backup" button which will revert the game state to the state it was in when they started their turn. Once the
player is satisfied with their move choice, they must click "Submit turn" to finalize their choice. When the player 
submits their turn, the board will be updated to its new state and control will be passed to the player's opponent. If
at any time during the game the player wishes to leave, they may click "Resign", which will end the game and announce 
their resignation to their opponent.

### UI Tier

The server side of our UI tier is implemented using Spark and Free marker. There are two major components in the UI-tier.
The first component is the routes, and the other is the views. The routes include post and get routes while the views includes
board view, row and space. The UI tier of the game has minimal logic implementation. The logic implemented is mostly for the
view and redirecting the routes. 

The serverside UI starts with a GetHomeRoute. This route is responsible for rendering the home page of the checkers. If a
player is not signed-in, it shows the number of players signed in. It has a sign-in button redirect the home page to the GetSignRoute. 
The GetSignInRoute has a text box that allows users to enter their username. The users can submit their username with the 
"Sign In" button. 

Once the user submits the username, the post SignInRoute requests the input which is the username. The username is returned
to the PostSignInRoute which then requests a session. An httpSession is returned to the postSignInRoute, which then sends
the username to the PlayerLobby (Application tier component) which checks if it's valid and available. The result 
returns to the PostSignInRoute. If the result is valid, the player which is an attribute, is passed to the session and if the result is 
invalid, PostSignInRoute responds with an error message.

If the sign in was successful, then the user will be redirected to the home route where they can select their next 
option. If the user wants to challenge an opponent, the GetHomeRoute displays the users who are signed in. If the other user is 
available and challenged, the home page is redirected to the GetHomeRoute. The GetHomeRoute renders the checkerboard with the 
help of the view components mentioned earlier. With the help of PostBackupMove, PostSubmitTurnRoute, PostValidateMove and PostGameRoute, 
the game view is rendered. Simillary if a user wishes to watch a replay of a game, the PostReplayNext and PostReplayPrevious routes 
help the player to navigate through the turns made by the players. 


![PostSignInRoute Sequence Diagram](PostSignInSD.png)

### Application Tier
Our application tier handled the interactions between the UI and Model tiers. An example of this is the 
GameManager class, which handles the UI's requests for boards and turn submission and makes function calls while passing
data to classes such as CheckersGame and CheckerBoard. This way there is a separation of responsibilities and data
between the UI and Model. 

![GameManager](GameManager.png)

Another example of this is the PlayerLobby component. The PlayerLobby takes care of adding players who sign in through
the UI and provides them to the CheckersGame and Player classes for use. Making a Player creates an object with the name 
gathered in the UI passed through the PlayerLobby class. 

![PlayerLobby](PlayerLobby.png)

### Model Tier
The model tier was where most of the heavy lifting was done within the program. Most of the logic for handling the
interactions passed though the Application Tier from the UI was ultimately handled by the model components. This is 
fairly apparent when looking at the size of some of the classes such as Checkerboard. 

![CheckerBoard](CheckerBoard.png)

As shown in the diagram above, the Checkerboard class handles a lot of different logic for the game itself. Taking care
of functions such as validating a move, checking if a jump is available, creating the initial board to be played on, 
among others is a prime example of how much work the Model tier components are putting in. That being said, not every
Model component was quite so large. For example, the CheckerPiece class was kept simple.

![CheckerPiece](CheckerPiece.png)

The CheckerPiece does not need to perform very many functions, and thus was a pretty slim class. Each checker piece 
object was responsible for keeping track of whether or not it was a king and what color it was, so the board would know 
how it should be manipulated later. We did this to maintain a low amount of coupling, with each class handling only what
it needs to do for itself. 


### Design Improvements

The metric measurements from the Code Metrics excercise revealed several
hotspots on the class level where complexity was abnormally high. The main
classes that this happened in were GetGameRoute, CheckersGame, and CheckerBoard,
which was not surprising since these are large classes that involve
game functionality. The GetGameRoute has many different branches to it depending on
a bunch of different factors such as playmode or the player calling it which gives
it a poor complexity score. A future improvement may involves seperating out some
of the functionality into different routes or putting more work on the
application tier. The CheckersBoard class has methods such as isMoveAvailable and isValidMove
which check for a whole bunch of different scenarios and as a result have a large amount
of execution paths in them. This tanked our complexity score hard. Future design improvements may
involve pushing off some of that work into helper classes and decreasing the amount
of if statement branches in the methods. CheckersGame was guilty by association, since it was
calling these methods in CheckersBoard. The helper classes would improve
our complexity score for both.

## Testing

### Acceptance Testing

Acceptance testing played a huge role in how we found bugs. Not every bug that could be found was able to be caught by
the JUnit tests (see below) that were written to check normal functionality. Examples of this were bugs that would occur
if a page was refreshed in the middle of a move before it was submitted. This would cause the player to not actually
submit a move, but be unable to move a piece at the same time, locking the state of the game. 

Examples of acceptance tests include ensuring that a piece becomes a king when reaching the opposing player's king row, 
verifying that pieces could not jump over their own team's pieces, and double checking that the player could properly 
resign and exit the game. Multiple programmers would perform these tests and try to break various aspects of each 
feature in pursuit of as bug-free an experience as possible. 

### Unit Testing and Code Coverage
 For unit testing Report on the code coverage
 We have an overall code coverage of 100% in the application-tier, 93% in the 
 model-tier and 87% in the UI-tier. Our strategy in unit test was to get maximum 
 coverage possible and focus on multiple possible scenarios.
 Our teams target was to get a 100% coverage on the model class. Getting maximum
 coverage on the model class was important to our team because the model class 
 contained the entire Checkerboard logic. Thus, each component had to be tested. We
 were able to achieve 100% code coverage on most on the classes. The two remaining 
 classes have 87% and 93% coverage. 

