package selantoapps.soccerleaguesimulator.model;

import android.os.Bundle;

import java.util.List;

import selantoapps.soccerleaguesimulator.R;

/**
 * Created by antoniocappiello on 24/06/17.
 */

public class TeamModel {

    private static TeamModel INSTANCE;

    private final Team[] teams;

    public static TeamModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TeamModel();
        }
        return INSTANCE;
    }

    private TeamModel() {
        teams = new Team[4];
        teams[0] = Team.builder()
                .setLogo(R.drawable.team_a)
                .setName("Team A")
                .setStrength(1)
                .build();

        teams[1] = Team.builder()
                .setLogo(R.drawable.team_b)
                .setName("Team B")
                .setStrength(1)
                .build();

        teams[2] = Team.builder()
                .setLogo(R.drawable.team_c)
                .setName("Team C")
                .setStrength(1)
                .build();

        teams[3] = Team.builder()
                .setLogo(R.drawable.team_d)
                .setName("Team D")
                .setStrength(1)
                .build();
    }

    public Team[] getTeams() {
        return teams;
    }
}
