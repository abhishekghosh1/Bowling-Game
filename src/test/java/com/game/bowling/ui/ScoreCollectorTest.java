package com.game.bowling.ui;

import com.game.bowling.Ball;
import com.game.bowling.Frame;
import com.game.bowling.Player;
import com.game.bowling.scores.ScoreListener;
import com.game.bowling.testutil.StdinWriter;
import com.game.bowling.testutil.StdoutReader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author abhishek.ghosh
 */
public class ScoreCollectorTest {

    private static final String INVALID_NUMBER_OF_PINS_MESSAGE = "Not a valid number of pins, please enter a number between 1 and %d:";

    private StdoutReader stdout;
    private StdinWriter stdin;

    private ScoreCollector scoreCollector;
    private Player player;
    private Frame frame;

    @Before
    public void setUp() throws IOException {
        stdout = new StdoutReader();
        stdin = new StdinWriter();

        scoreCollector = new ScoreCollector();
        player = new Player("Greg");
        frame = mock(Frame.class);
    }

    @Test
    public void announcePlayersTurnPrintsConsoleMessage() throws Exception {
        // when
        scoreCollector.announcePlayersTurn(player);
        // then
        assertThat(stdout.readLine(), is("Greg's turn!"));
    }

    @Test
    public void takeScoreAsksForPlayerInput() throws Exception {
        // given
        given(frame.getNumber()).willReturn(3);
        // when
        stdin.sendLine("2");
        int score = scoreCollector.takeScore(frame, player, Ball.TWO);
        // then
        assertThat(stdout.readLine(), is("Enter score for Frame 3, Ball 2:"));
        assertThat(score, is(2));
    }

    @Test
    public void takeScoreRejectsNonNumericInput() throws Exception {
        // given
        given(frame.getNumber()).willReturn(3);

        // when
        stdin.sendLine("");
        stdin.sendLine("1-3S1");
        // First valid number
        stdin.sendLine("1");
        scoreCollector.takeScore(frame, player, Ball.TWO);

        // then
        assertThat(stdout.readLines(3), containsString(format(INVALID_NUMBER_OF_PINS_MESSAGE, 10)));
    }

    @Test
    public void takeScoreRejectsInvalidNumberOfPins() throws Exception {
        // given
        given(frame.getNumber()).willReturn(3);

        // when
        stdin.sendLine("-1");
        stdin.sendLine("11");
        // First valid number
        stdin.sendLine("1");
        scoreCollector.takeScore(frame, player, Ball.TWO);

        // then
        assertThat(stdout.readLines(3), containsString(format(INVALID_NUMBER_OF_PINS_MESSAGE, 10)));
    }

    @Test
    public void takeScoreRejectsNumberOfPinsHigherThanPinsRemaining() throws Exception {
        // given
        given(frame.getNumber()).willReturn(3);
        int remainingPins = 3;

        // when
        stdin.sendLine("4");
        // First valid number
        stdin.sendLine("3");
        scoreCollector.takeScore(frame, player, Ball.TWO, remainingPins);

        // then
        assertThat(stdout.readLines(2), containsString(format(INVALID_NUMBER_OF_PINS_MESSAGE, remainingPins)));
    }

    @Test
    public void notifiesScoreListener() throws Exception {
        // given
        ScoreListener listener = mock(ScoreListener.class);
        scoreCollector.addScoreListener(listener);
        Ball ball = Ball.ONE;
        // when
        stdin.sendLine("7");
        scoreCollector.takeScore(frame, player, ball);
        // then
        verify(listener).playerScored(frame, player, ball, 7);
    }

}
