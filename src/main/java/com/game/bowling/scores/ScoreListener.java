package com.game.bowling.scores;

import com.game.bowling.Ball;
import com.game.bowling.Frame;
import com.game.bowling.Player;

/**
 * @author abhishek.ghosh
 */
public interface ScoreListener {

    void playerScored(Frame frame, Player player, Ball ball, int pins);

}
