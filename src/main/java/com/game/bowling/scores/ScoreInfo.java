package com.game.bowling.scores;

import com.game.bowling.Ball;
import java.util.Map;
import static com.google.common.collect.Maps.newHashMap;

/**
 * This class transform per Ball scores and corresponding bonus
 * @author abhishek
 */
public class ScoreInfo {

    private final Map<Ball, Integer> ballScores = newHashMap();
    private int bonus;

    public void addScore(Ball ball, int pins) {
        ballScores.put(ball, pins);
    }

    public void addBonus(int score) {
        bonus += score;
    }

    public int getScore(Ball ball) {
        Integer score = ballScores.get(ball);
        return score == null ? 0 : score;
    }

    public int getTotalScore() {
        int total = bonus;
        for (int score : ballScores.values()) {
            total += score;
        }
        return total;
    }

}
