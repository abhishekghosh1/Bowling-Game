package com.game.bowling.scores;

import com.game.bowling.Player;
import static com.game.bowling.Ball.ONE;
import static com.game.bowling.Ball.TWO;
import static com.game.bowling.BowlingGameConstants.MAX_POINTS_PER_BALL;

/**
 * This class calculate bonus score after every balls
 * @author abhishek.ghosh
 */
public class BonusCalculator {

    public void addBonusPointsIfApplicable(Player player, FrameScores previousFrame, FrameScores currentFrame) {

        if (playerGotStrike(player, previousFrame)) {
            int bonus = currentFrame.getScore(player, ONE) + currentFrame.getScore(player, TWO);
            previousFrame.addBonus(player, bonus);
        }
        if (playerGotSpare(player, previousFrame)) {
            int bonus = currentFrame.getScore(player, ONE);
            previousFrame.addBonus(player, bonus);
        }
    }

    public void addBonusPointsIfApplicable(Player player, FrameScores twoFramesAgo, FrameScores previousFrame, FrameScores currentFrame) {

        if (playerGotStrike(player, twoFramesAgo) && playerGotStrike(player, previousFrame)) {
            int bonus = currentFrame.getScore(player, ONE) + previousFrame.getScore(player, ONE);
            twoFramesAgo.addBonus(player, bonus);
        } else if(playerGotStrike(player, currentFrame)) {
            int bonus = currentFrame.getScore(player, ONE);
            previousFrame.addBonus(player, bonus);
        }
    }

    private boolean playerGotStrike(Player player, FrameScores frameScores) {
        return frameScores.getScore(player, ONE) == MAX_POINTS_PER_BALL;
    }

    private boolean playerGotSpare(Player player, FrameScores frameScores) {
        int one = frameScores.getScore(player, ONE);
        int two = frameScores.getScore(player, TWO);

        return one != MAX_POINTS_PER_BALL && one + two == MAX_POINTS_PER_BALL;
    }

}
