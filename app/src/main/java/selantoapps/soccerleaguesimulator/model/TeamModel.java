package selantoapps.soccerleaguesimulator.model;

import java.util.ArrayList;
import java.util.List;

import selantoapps.soccerleaguesimulator.R;
import selantoapps.soccerleaguesimulator.control.Config;

/**
 * Created by antoniocappiello on 24/06/17.
 */

public class TeamModel {

    private static TeamModel INSTANCE;

    private final List<Team> teams;

    public static TeamModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TeamModel();
        }
        return INSTANCE;
    }

    private TeamModel() {
        teams = new ArrayList<>(4);
        teams.add(Team.builder()
                .setLogo(R.drawable.team_a)
                .setName("Team A")
                .setStrength(Config.TEAM_A_STRENGTH)
                .build());

        teams.add(Team.builder()
                .setLogo(R.drawable.team_b)
                .setName("Team B")
                .setStrength(Config.TEAM_B_STRENGTH)
                .build());

        teams.add(Team.builder()
                .setLogo(R.drawable.team_c)
                .setName("Team C")
                .setStrength(Config.TEAM_C_STRENGTH)
                .build());

        teams.add(Team.builder()
                .setLogo(R.drawable.team_d)
                .setName("Team D")
                .setStrength(Config.TEAM_D_STRENGTH)
                .build());
    }

    public List<Team> getTeams() {
        return teams;
    }

    public int getTeamsCount() {
        return teams.size();
    }
}
