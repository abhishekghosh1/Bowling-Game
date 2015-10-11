package com.game.bowling.integrationtest;

import com.game.bowling.testutil.StdinWriter;
import com.game.bowling.testutil.StdoutReader;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static com.game.bowling.BowlingGameConstants.MAX_FRAME;
import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author abhishek.ghosh
 */
public class EndToEndTest {

    private StdinWriter gameInput;
    private GameRunner game;
    private StdoutReader gameOutput;

    @Before
    public void setUpGame() throws IOException {
        gameInput = new StdinWriter();
        game = new GameRunner();
        gameOutput = new StdoutReader();
    }

    @After
    public void stopGame() throws Exception {
        game.stop();
    }

    @Test
    public void canPlayBowlingGame() throws Exception {
        game.start();

        assertThat(gameOutput.readLine(), is("With How many players start playing?"));

        gameInput.sendLine("2");

        assertThat(gameOutput.readLine(), is("Enter name for Player 1:"));
        gameInput.sendLine("Sarah");

        assertThat(gameOutput.readLine(), is("Enter name for Player 2:"));
        gameInput.sendLine("Bob");

        for (int frame = 1; frame <= 10; frame++) {
            if (frame > 1) {
                final String readLines = gameOutput.readLines(5);
                assertThat(readLines, containsString(format("Sarah got %d points", (5 + 3) * (frame-1))));
                assertThat(readLines, containsString(format("Bob got %d points", 6 * (frame-1))));
                assertThat(readLines, containsString("Sarah's turn!"));
            } else {
                assertThat(gameOutput.readLines(1), containsString("Sarah's turn!"));
            }
            enterFrameScores(frame, 5, 3);

            assertThat(gameOutput.readLines(1), containsString("Bob's turn!"));
            enterFrameScores(frame, 0, 6);
        }

        assertThat(gameOutput.readLine(), is("Game Over!"));
        assertThat(gameOutput.readLine(), is(format("Sarah won!")));

        int expectedPlayer1Score = (5 + 3) * 10;
        int expectedPlayer2Score = 6 * 10;
        int expectedTeamScore = expectedPlayer1Score + expectedPlayer2Score;
        final String gameOutPutValues = gameOutput.readLines(3);
        assertThat(gameOutPutValues, containsString(format("Sarah got %d points", expectedPlayer1Score)));
        assertThat(gameOutPutValues, containsString(format("Bob got %d points", expectedPlayer2Score)));
        assertThat(gameOutPutValues, containsString(format("The team got %d points", expectedTeamScore)));
    }

    @Test
    public void perfectGameTest() throws Exception {
        game.start();

        gameInput.sendLine("1");
        gameInput.sendLine("Bob");
        // Ignore
        gameOutput.readLines(2);

        for (int frame = 1; frame <= MAX_FRAME; frame++) {
            gameInput.sendLine("10");
            final String gameOutPut = gameOutput.readLines(2);
            assertThat(gameOutPut, is(not("Game Over!")));
        }

        gameInput.sendLine("10");
        gameInput.sendLine("10");

        final String expectedReadLinesForLastTwoBalls = gameOutput.readLines(29);
        assertThat(expectedReadLinesForLastTwoBalls, containsString("Enter score for Frame 10, Ball 2:"));
        assertThat(expectedReadLinesForLastTwoBalls, containsString("Enter score for Frame 10, Ball 3:"));

        final String expectedReadLinesForFourBalls = gameOutput.readLines(3);
        assertThat(expectedReadLinesForFourBalls, containsString("Game Over!"));
        assertThat(expectedReadLinesForFourBalls, containsString("Bob got 300 points"));
    }

    private void enterFrameScores(int frame, int ball1, int ball2) throws Exception {
        assertThat(gameOutput.readLine(), containsString(format("Enter score for Frame %d, Ball 1:", frame)));
        gameInput.sendLine(Integer.toString(ball1));

        assertThat(gameOutput.readLine(), containsString(format("Enter score for Frame %d, Ball 2:", frame)));
        gameInput.sendLine(Integer.toString(ball2));
    }

}
