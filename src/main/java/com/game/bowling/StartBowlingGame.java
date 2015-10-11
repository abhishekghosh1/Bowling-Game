package com.game.bowling;

import com.game.bowling.scores.BonusCalculator;
import com.game.bowling.scores.FrameScoresFactory;
import com.game.bowling.scores.ScoreTable;
import com.game.bowling.ui.EndGameHandler;
import com.game.bowling.ui.PlayerInputCollector;
import com.game.bowling.ui.ScoreCollector;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static com.game.bowling.BowlingGameConstants.FIRST;
import static com.game.bowling.BowlingGameConstants.MAX_FRAME;
import static com.game.bowling.BowlingGameConstants.NINE;
import static java.lang.String.format;

/**
 * BowlingGame Program starts from here. This is the main class which controls the entire Game.
 * @author abhishek.ghosh
 */
public class StartBowlingGame {

    public static void main(String[] args) throws Exception {
        new StartBowlingGame().play();
    }

    private final PlayerInputCollector playerInputCollector;
    private final ScoreCollector scoreCollector;
    private final EndGameHandler endGameHandler;

    /**
     * Default Constructor
     */
    public StartBowlingGame() {
        playerInputCollector = new PlayerInputCollector();
        scoreCollector = new ScoreCollector();
        endGameHandler = new EndGameHandler();
    }

    /**
     * Depending on the number of players this method execute the frame and show current status
     */
    public void play() throws Exception {
        ScoreTable scoreTable = new ScoreTable(new FrameScoresFactory(), new BonusCalculator());
        List<Player> players = playerInputCollector.getPlayers();
        List<Frame> frames = createFrames(players);
        scoreCollector.addScoreListener(scoreTable);
        for (Frame frame : frames) {
            frame.play();
            printCurrentStats(scoreTable, frame);
        }

        endGameHandler.gameOver(scoreTable);
    }

    private void printCurrentStats(final ScoreTable scoreTable, final Frame frame) {
        if (frame.getNumber() < MAX_FRAME) {
            System.out.println("***** Current Stats **********");
            for (Map.Entry<Player, Integer> playerScore : scoreTable.getPlayerScores().entrySet()) {
                Player player = playerScore.getKey();
                Integer points = playerScore.getValue();
                System.out.println(format("%s got %d points", player.getName(), points));
            }
            System.out.println("***** End **********");
        }
    }

    private List<Frame> createFrames(List<Player> players) {
        List<Frame> frames = new ArrayList<Frame>();
        for (int frame = FIRST; frame <= NINE; frame++) {
            frames.add(new Frame(frame, players, scoreCollector));
        }
        frames.add(new FinalFrame(players, scoreCollector));
        return frames;
    }

}
