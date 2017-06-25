package selantoapps.soccerleaguesimulator.model;

import java.util.ArrayList;
import java.util.List;

import selantoapps.soccerleaguesimulator.R;

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
                .setStrength(1)
                .build());

        teams.add(Team.builder()
                .setLogo(R.drawable.team_b)
                .setName("Team B")
                .setStrength(1)
                .build());

        teams.add(Team.builder()
                .setLogo(R.drawable.team_c)
                .setName("Team C")
                .setStrength(1)
                .build());

        teams.add(Team.builder()
                .setLogo(R.drawable.team_d)
                .setName("Team D")
                .setStrength(1)
                .build());
    }

    public List<Team> getTeams() {
        return teams;
    }

    public int getTeamsCount() {
        return teams.size();
    }
}
