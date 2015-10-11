package com.game.bowling.scores;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;
import java.util.Map.Entry;

import com.game.bowling.Ball;
import com.game.bowling.Player;

/**
 * After a specific frame this class update all the playerScores including bonus.
 * @author abhishek.ghosh
 */
public class FrameScores {

    private final Map<Player, ScoreInfo> playerScores = newHashMap();

    public void addScore(Player player, Ball ball, int pins) {
        ScoreInfo scoreInfo = playerScores.get(player);

        if (scoreInfo == null) {
            scoreInfo = new ScoreInfo();
            playerScores.put(player, scoreInfo);
        }

        scoreInfo.addScore(ball, pins);
    }

    public void addBonus(Player player, int score) {
        playerScores.get(player).addBonus(score);
    }

    public int getScore(Player player, Ball ball) {
        return playerScores.get(player).getScore(ball);
    }

    public int getTotalScore(Player player) {
        return playerScores.get(player).getTotalScore();
    }

    public Map<Player, Integer> getAllPlayerScores() {
        Map<Player, Integer> allPlayerScores = newHashMap();
        for (Entry<Player, ScoreInfo> playerScoreInfo : playerScores.entrySet()) {
            allPlayerScores.put(playerScoreInfo.getKey(), playerScoreInfo.getValue().getTotalScore());
        }
        return allPlayerScores;
    }

}
