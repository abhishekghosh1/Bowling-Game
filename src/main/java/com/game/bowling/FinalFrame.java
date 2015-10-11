package com.game.bowling;

import java.util.List;

import com.game.bowling.ui.ScoreCollector;
import static com.game.bowling.Ball.ONE;
import static com.game.bowling.Ball.THREE;
import static com.game.bowling.Ball.TWO;
import static com.game.bowling.BowlingGameConstants.MAX_FRAME;

/**
 * Defines final frame functionality including extra balls for bonus strike
 * @author abhishek.ghosh
 */
public class FinalFrame extends Frame {

    public FinalFrame(List<Player> players, ScoreCollector scoreCollector) {
        super(MAX_FRAME, players, scoreCollector);
    }

    @Override
    protected void takePlayersTurn(Player player) {
        int score = scoreCollector.takeScore(this, player, ONE);

        int remainingPins = MAX_FRAME - score;

        if (remainingPins == 0) {
            playExtraTwoBallsForAStrike(player);
        } else {
            playAnExtraBallIfPlayerGetsASpare(player, remainingPins);
        }
    }

    private void playAnExtraBallIfPlayerGetsASpare(Player player, int remainingPins) {
        int score = scoreCollector.takeScore(this, player, TWO, remainingPins);

        boolean spare = (remainingPins - score == 0);

        if (spare) {
            scoreCollector.takeScore(this, player, THREE);
        }
    }

    private void playExtraTwoBallsForAStrike(Player player) {
        int score = scoreCollector.takeScore(this, player, TWO);

        int remainingPins = MAX_FRAME - score;

        if (remainingPins == 0) {
            scoreCollector.takeScore(this, player, THREE);
        } else {
            scoreCollector.takeScore(this, player, THREE, remainingPins);
        }
    }

}
