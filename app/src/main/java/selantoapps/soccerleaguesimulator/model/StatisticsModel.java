package selantoapps.soccerleaguesimulator.model;

/**
 * Created by antoniocappiello on 25/06/17.
 */

public class StatisticsModel {

    int gamesCount;

    int matchesCount;

    int goalsCount;

    private static StatisticsModel INSTANCE;

    public static StatisticsModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StatisticsModel();
        }
        return INSTANCE;
    }

    private StatisticsModel() {
        matchesCount = goalsCount = 0;
    }

    public int getGamesCount() {
        return gamesCount;
    }

    public int getMatchesCount() {
        return matchesCount;
    }

    public int getGoalsCount() {
        return goalsCount;
    }

    public void addMatch() {
        matchesCount++;
    }

    public void addGoals(int goals) {
        goalsCount += goals;
    }

    public void addGame() {
        gamesCount++;
    }
}
