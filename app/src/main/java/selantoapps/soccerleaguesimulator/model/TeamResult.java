package selantoapps.soccerleaguesimulator.model;

import selantoapps.soccerleaguesimulator.control.Config;

/**
 * Created by antoniocappiello on 25/06/17.
 */

public class TeamResult {

    private Team team;

    private int won;

    private int lost;

    private int draw;

    private int scored;

    private int conceded;

    private int overall;

    private int points;

    public TeamResult() {
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getScored() {
        return scored;
    }

    public void setScored(int scored) {
        this.scored = scored;
    }

    public int getConceded() {
        return conceded;
    }

    public void setConceded(int conceded) {
        this.conceded = conceded;
    }

    public int getOverall() {
        return overall;
    }

    public void setOverall(int overall) {
        this.overall = overall;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Increment won matches by one and update points.
     */
    public void addWin() {
        won++;
        points += Config.WINNER_POINTS;
    }

    /**
     * Increment lost matches by one.
     */
    public void addLost() {
        lost++;
    }

    /**
     * Increment draw matches by one and update points.
     */
    public void addDraw() {
        draw++;
        points += Config.DRAW_POINTS;
    }

    public void addScored(int goals) {
        scored += goals;
        updateOverallGoals();
    }

    public void addConceded(int goals) {
        conceded += goals;
        updateOverallGoals();
    }

    private void updateOverallGoals() {
        overall = scored - conceded;
    }

    @Override
    public String toString() {
        return team.name() +
                ", won=" + won +
                ", lost=" + lost +
                ", draw=" + draw +
                ", scored=" + scored +
                ", conceded=" + conceded +
                ", overall=" + overall +
                ", points=" + points;
    }
}
