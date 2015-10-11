package com.game.bowling;

import java.util.List;

import com.game.bowling.ui.ScoreCollector;
import static com.game.bowling.Ball.ONE;
import static com.game.bowling.Ball.TWO;
import static com.game.bowling.BowlingGameConstants.MAX_FRAME;

/**
 * Defines particular frame functionality
 * @author abhishek.ghosh
 */
public class Frame {

    private final int frameNumber;
    private final List<Player> players;

    protected final ScoreCollector scoreCollector;

    public Frame(int frameNumber, List<Player> players, ScoreCollector scoreCollector) {
        this.frameNumber = frameNumber;
        this.players = players;
        this.scoreCollector = scoreCollector;
    }

    public int getNumber() {
        return frameNumber;
    }

    public void play() {
        for (Player player : players) {
            scoreCollector.announcePlayersTurn(player);

            takePlayersTurn(player);
        }
    }

    protected void takePlayersTurn(Player player) {
        int score = scoreCollector.takeScore(this, player, ONE);

        int remainingPins = MAX_FRAME - score;

        if (remainingPins > 0) {
            scoreCollector.takeScore(this, player, TWO, remainingPins);
        }
    }

    @Override
    public String toString() {
        return "Frame [" + frameNumber + "]";
    }

}
