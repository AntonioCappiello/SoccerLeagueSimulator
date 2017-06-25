package selantoapps.soccerleaguesimulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by antoniocappiello on 25/06/17.
 */

public class MatchStatisticsModel {

    private static MatchStatisticsModel INSTANCE;

    private final HashMap<Team, TeamResult> map;

    private int gamePlayedCount;

    public static MatchStatisticsModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MatchStatisticsModel();
        }
        return INSTANCE;
    }

    private MatchStatisticsModel() {
        // Initialize map of teams and associated result
        List<Team> teams = TeamModel.getInstance().getTeams();
        map = new HashMap<>(teams.size());
        for (Team team : teams) {
            TeamResult teamResult = new TeamResult();
            teamResult.setTeam(team);
            map.put(team, teamResult);
        }

        gamePlayedCount = 0;
    }

    public void update(List<TeamResult> teamResults) {
        gamePlayedCount++;
        for (TeamResult newRes : teamResults) {
            TeamResult oldRes = findByTeam(newRes.getTeam());
            oldRes.setWon(oldRes.getWon() + newRes.getWon());
            oldRes.setLost(oldRes.getLost() + newRes.getLost());
            oldRes.setDraw(oldRes.getDraw() + newRes.getDraw());
            oldRes.addScored(newRes.getScored());
            oldRes.addConceded(newRes.getConceded());
            oldRes.setPoints(oldRes.getPoints() + newRes.getPoints());
        }
    }

    public TeamResult findByTeam(Team team) {
        return map.get(team);
    }

    public List<TeamResult> findAll() {
        return new ArrayList<>(map.values());
    }

    public int getGamePlayedCount() {
        return gamePlayedCount;
    }
}
