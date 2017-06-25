package selantoapps.soccerleaguesimulator.model;

/**
 * Created by antoniocappiello on 24/06/17.
 */

public class Match {

    private Team homeTeam;
    private Team awayTeam;
    private int homeTeamGoals;
    private int awayTeamGoals;

    public Match(Team homeTeam, Team awayTeam, int homeTeamGoals, int awayTeamGoals) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
    }

    public Match(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public void setHomeTeamGoals(int homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
    }

    public int getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public void setAwayTeamGoals(int awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
    }

    @Override
    public String toString() {
        return homeTeam.name() + " - " + awayTeam.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Match match = (Match) o;

        if (homeTeamGoals != match.homeTeamGoals) return false;
        if (awayTeamGoals != match.awayTeamGoals) return false;
        if (homeTeam != null ? !homeTeam.equals(match.homeTeam) : match.homeTeam != null)
            return false;
        return awayTeam != null ? awayTeam.equals(match.awayTeam) : match.awayTeam == null;
    }

    @Override
    public int hashCode() {
        int result = homeTeam != null ? homeTeam.hashCode() : 0;
        result = 31 * result + (awayTeam != null ? awayTeam.hashCode() : 0);
        result = 31 * result + homeTeamGoals;
        result = 31 * result + awayTeamGoals;
        return result;
    }
}
