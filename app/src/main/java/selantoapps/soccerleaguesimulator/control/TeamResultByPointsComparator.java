package selantoapps.soccerleaguesimulator.control;

import selantoapps.soccerleaguesimulator.model.TeamResult;

/**
 * Created by antoniocappiello on 25/06/17.
 */

public class TeamResultByPointsComparator implements java.util.Comparator<TeamResult> {

    @Override
    public int compare(TeamResult a, TeamResult b) {
        // Descending order
        return a.getPoints() > b.getPoints() ? -1 :
                a.getPoints() < b.getPoints() ? 1 : 0;
    }
}
