# Bowling Game with MultiPlayer options #

* Write an application that takes score of a bowling game. It should take..
	* How Many Number of players want to play the game
	* Players Name
	* the number of pins knocked down by each ball as input and gives the score for each frame as output


## Features ##

* A command line interface for entering player names and scores
* Input validation
* Game logic to determine the correct number of balls and frames to play
* Scoring logic that tallies scores for each player and applies bonus points
* At the end it displays the winner, each players score and the team's total score
* Show current stats after every frame. InCase a Strike it updates after next possible frame.
* Acceptance tests that drive the system end-to-end through the command line interface

## How to Run ##

You can build and run tests using Maven:

	$ mvn clean package

The Maven build will produce an executable Jar: `target/bowling-game-0.0.1-SNAPSHOT-jar-with-dependencies.jar`

Alternatively, import into your IDE of choice. The main class is `com.game.bowling.StartBowlingGame`.

