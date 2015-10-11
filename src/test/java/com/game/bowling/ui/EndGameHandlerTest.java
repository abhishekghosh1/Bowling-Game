package com.game.bowling.ui;

import com.game.bowling.Player;
import com.game.bowling.scores.ScoreTable;
import com.game.bowling.testutil.StdoutReader;
import java.io.IOException;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static com.google.common.collect.Maps.newLinkedHashMap;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


/**
 * @author abhishek.ghosh
 */
public class EndGameHandlerTest {

    private StdoutReader stdout;

    private ScoreTable scoreTable;
    private EndGameHandler handler;
    private Player rob;
    private Player natalie;

    @Before
    public void setUp() throws IOException {
        stdout = new StdoutReader();

        scoreTable = mock(ScoreTable.class);
        handler = new EndGameHandler();
        rob = new Player("Rob");
        natalie = new Player("Natalie");
    }

    @Test
    public void announcesWinner() throws Exception {
        // given
        given(scoreTable.getWinner()).willReturn(rob);
        // when
        handler.gameOver(scoreTable);
        // then
        assertThat(stdout.readLine(), is("Game Over!"));
        assertThat(stdout.readLine(), is("Rob won!"));
    }

    @Test
    public void printsScoresForEachPlayer() throws Exception {
        // given
        Map<Player, Integer> playerScores = newLinkedHashMap();
        playerScores.put(natalie, 6);
        playerScores.put(rob, 5);

        given(scoreTable.getWinner()).willReturn(natalie);
        given(scoreTable.getPlayerScores()).willReturn(playerScores);

        // when
        handler.gameOver(scoreTable);

        // then
        final String output = stdout.readLines(4);
        assertThat(output, containsString("Natalie got 6 points"));
        assertThat(output, containsString("Rob got 5 points"));
    }

    @Test
    public void printsCombinedTeamScore() throws Exception {
        // given
        given(scoreTable.getWinner()).willReturn(natalie);
        given(scoreTable.getTeamScore()).willReturn(11);

        // when
        handler.gameOver(scoreTable);

        // then
        assertThat(stdout.readLines(3), containsString("The team got 11 points"));
    }

}
