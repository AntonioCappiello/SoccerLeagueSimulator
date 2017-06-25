package selantoapps.soccerleaguesimulator.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import selantoapps.soccerleaguesimulator.model.Match;
import selantoapps.soccerleaguesimulator.model.Team;
import selantoapps.soccerleaguesimulator.model.TeamModel;
import selantoapps.soccerleaguesimulator.model.TeamResult;

/**
 * Created by antoniocappiello on 25/06/17.
 */

public class TeamResultGenerator {

    private TeamResultGenerator() {
        //no instance, utility class
    }

    public static List<TeamResult> fromMatches(List<Match> matches) {
        List<Team> teams = TeamModel.getInstance().getTeams();

        // Initialize map of teams and associated result
        Map<Team, TeamResult> map = new HashMap<>(teams.size());
        for (Team team : teams) {
            TeamResult teamResult = new TeamResult();
            teamResult.setTeam(team);
            map.put(team, teamResult);
        }

        // Loop through the matches to update each team with the match result
        for (Match match : matches) {

            // Update scored goals
            map.get(match.getHomeTeam())
                    .addScored(match.getHomeTeamGoals());
            map.get(match.getHomeTeam())
                    .addConceded(match.getAwayTeamGoals());

            map.get(match.getAwayTeam())
                    .addScored(match.getAwayTeamGoals());
            map.get(match.getAwayTeam())
                    .addConceded(match.getHomeTeamGoals());

            // Update match final result
            switch (match.getResultType()) {
                case HOME_TEAM_WIN:
                    map.get(match.getHomeTeam()).addWin();
                    map.get(match.getAwayTeam()).addLost();
                    break;
                case AWAY_TEAM_WIN:
                    map.get(match.getHomeTeam()).addLost();
                    map.get(match.getAwayTeam()).addWin();
                    break;
                case DRAW:
                    map.get(match.getHomeTeam()).addDraw();
                    map.get(match.getAwayTeam()).addDraw();
                    break;
            }
        }

        return new ArrayList<>(map.values());
    }
}
