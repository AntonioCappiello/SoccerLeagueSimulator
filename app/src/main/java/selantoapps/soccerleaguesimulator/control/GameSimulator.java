package selantoapps.soccerleaguesimulator.control;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import selantoapps.soccerleaguesimulator.model.Match;
import selantoapps.soccerleaguesimulator.model.StatisticsModel;
import selantoapps.soccerleaguesimulator.model.Team;
import selantoapps.soccerleaguesimulator.model.TeamModel;
import selantoapps.soccerleaguesimulator.model.TeamResult;

/**
 * Created by antoniocappiello on 24/06/17.
 */

public class GameSimulator {

    public static final String TAG = GameSimulator.class.getSimpleName();

    private static GameSimulator INSTANCE;

    private final List<Team> teams;

    private final Random random;

    /**
     * Make this class a singleton to reduce memory usage over multiple game played.
     * Teams will not be instantiated every time, but only once.
     *
     * @return
     */
    public static GameSimulator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameSimulator();
        }
        return INSTANCE;
    }

    private GameSimulator() {
        teams = TeamModel.getInstance().getTeams();
        random = new Random();
    }

    public List<Match> start() {
        logGameStart();
        logMatchingTeams();
        List<Match> matches = createMatches();
        logEnd();
        logPlayingMatches();
        playMatches(matches);
        logEnd();
        return matches;
    }

    private void playMatches(List<Match> matches) {
        for (Match match : matches) {
            Map<Team, TeamResult> tmpResults = TeamResultGenerator.fromMatchesAsMap(matches);
            simulateGoals(
                    match,
                    match.getHomeTeam(),
                    match.getAwayTeam(),
                    tmpResults.get(match.getHomeTeam()), // current league result of home team
                    tmpResults.get(match.getAwayTeam()), // current league result of away team
                    Config.MAX_GOALS_BY_TEAM_PER_MATCH
            );
            StatisticsModel.getInstance().addMatch();
            StatisticsModel.getInstance().addGoals(match.getHomeTeamGoals() + match.getAwayTeamGoals());
        }
        StatisticsModel.getInstance().addGame();
    }

    private void simulateGoals(Match match, Team homeTeam, Team awayTeam, TeamResult homeTeamCurrentResult, TeamResult awayTeamCurrentResult, int maxGoalsByTeamPerMatch) {

        // Calculate chance to score for each team, relative to the opponent

        // Chance to score is based on:

        // 1. team strength
        int homeTeamChance = homeTeam.strength();
        int awayTeamChance = awayTeam.strength();

        // 2. home advantage
        homeTeamChance += Config.HOME_TEAM_ADVANTAGE;

        // 3. best position in current league
        if (homeTeamCurrentResult.getPoints() > awayTeamCurrentResult.getPoints()) {
            homeTeamChance += Config.BEST_LEAGUE_POSITION;
        } else if (awayTeamCurrentResult.getPoints() > homeTeamCurrentResult.getPoints()) {
            awayTeamChance += Config.BEST_LEAGUE_POSITION;
        }

        // 4. most goals
        if (homeTeamCurrentResult.getScored() > awayTeamCurrentResult.getScored()) {
            homeTeamChance += Config.MOST_GOAL_SCORED;
        } else if (awayTeamCurrentResult.getPoints() > homeTeamCurrentResult.getPoints()) {
            awayTeamChance += Config.MOST_GOAL_SCORED;
        }

        // Normalize the chances in a scale from 0 to 100;
        // homeTeamChance (h) + awayTeamChance (a) + noScoreChance(n)= 100%
        // (h+a+n) : h = 100 : xhome  ==> xhome = 100*h/(h+a+n)
        int homeTeamChanceNormalized = 100 * homeTeamChance / (homeTeamChance + awayTeamChance + Config.NO_SCORE_CHANCE);
        // (h+a+n) : a = 100 : xaway  ==> xaway = 100*a/(h+a+n)
        int awayTeamChanceNormalized = 100 * awayTeamChance / (homeTeamChance + awayTeamChance + Config.NO_SCORE_CHANCE);

        int noScoreChanceNormalized = 100 * Config.NO_SCORE_CHANCE / (homeTeamChance + awayTeamChance + Config.NO_SCORE_CHANCE);

        int homeTeamGoals = 0;
        int awayTeamGoals = 0;
        int missedChances = 0;
        // from 0 to homeTeamChanceNormalized-1 is a goal for the home team
        // from awayTeamChanceNormalized - 1 to (homeTeamChanceNormalized + awayTeamChanceNormalized) is a goal for the away team
        // from homeTeamChanceNormalized - 1 to 99 no goal in this iteration
        for (int i = 0; i < maxGoalsByTeamPerMatch; i++) {
            int result = random.nextInt(100);
            if (result < homeTeamChanceNormalized) {
                homeTeamGoals++;
            } else if (result > homeTeamChanceNormalized - 1 && result < homeTeamChanceNormalized + awayTeamChanceNormalized) {
                awayTeamGoals++;
            } else {
                missedChances++;
            }
        }

        match.setHomeTeamGoals(homeTeamGoals);
        match.setAwayTeamGoals(awayTeamGoals);
        logMatchResult(match, homeTeamChanceNormalized, awayTeamChanceNormalized, noScoreChanceNormalized, missedChances);
    }

    /**
     * Randomly choose teams to play home or away, since it can slightly influence the match result.
     * As per requirements, the same two teams will play against each other only once.
     * <p>
     * Each team play against all the other. Since there are 4 teams, there will be 3 rounds of
     * 2 matches each, with a total of 6 matches at the end of all rounds.
     * <p>
     * The following algorithm is optimized for 4 teams, but can be slightly adjusted for handling
     * more teams at once.
     */
    private List<Match> createMatches() {
        int MAX_ROUNDS = 3;

        List<Match> matches = new ArrayList<>(6);
        int round = 1;
        while (round <= MAX_ROUNDS) {
            logRound(round);
            round++;
            ArrayList<Team> teamCopy = new ArrayList<>(teams);
            matches.add(createMatch(matches, teamCopy)); // Loop this method if more then 4 teams
            matches.add(createLastMatch(teamCopy));
        }

        return matches;
    }

    /**
     * Since it is the last match of the round it is enough to choose which team will play home or
     * away.
     *
     * @param teamCopy list of teams containing the last two teams of the round which have not
     *                 played yet
     * @return the last match of the round
     */
    private Match createLastMatch(ArrayList<Team> teamCopy) {
        Team homeTeam, awayTeam;
        if (random.nextBoolean()) {
            homeTeam = teamCopy.get(0);
            awayTeam = teamCopy.get(1);
        } else {
            homeTeam = teamCopy.get(1);
            awayTeam = teamCopy.get(0);
        }
        Match match = new Match(homeTeam, awayTeam);
        logMatch(match);
        return match;
    }

    /**
     * Create a new unique match and update the list of leftover teams.
     *
     * @param matches  the already made matches, used to check that the new random match doesn't
     *                 exists already
     * @param teamCopy the teams which have not been matched yet. The team of the new match will be
     *                 removed from this list.
     * @return An unique match of the League.
     */
    private Match createMatch(List<Match> matches, ArrayList<Team> teamCopy) {
        Match match, matchInverted;
        Team homeTeam, awayTeam;

        // Randomly choose the first team and remove it from the list
        homeTeam = teamCopy.remove(random.nextInt(teamCopy.size()));

        // Randomly choose the second team, but do not remove it yet...
        awayTeam = teamCopy.get(random.nextInt(teamCopy.size()));
        match = new Match(homeTeam, awayTeam);
        matchInverted = new Match(awayTeam, homeTeam);

        // ...because if the two teams have already been matched together, then choose another second team
        while (matches.contains(match) || matches.contains(matchInverted)) {
            Log.e(TAG, "duplicate " + match);
            awayTeam = teamCopy.get(random.nextInt(teamCopy.size()));
            match = new Match(homeTeam, awayTeam);
            matchInverted = new Match(awayTeam, homeTeam);
        }

        // When a suitable second team is found, remove it from the tmp list and store the match
        teamCopy.remove(awayTeam);

        logMatch(match);
        return match;
    }

    /**************************************************
     * Utility methods to print in console debug info
     * with a nicer formatting.
     *************************************************/

    private void logEnd() {
        Log.d(TAG, "================================");
    }

    private void logGameStart() {
        Log.d(TAG, "================================");
        Log.d(TAG, "=========== New game ===========");
    }

    private void logMatch(Match match) {
        Log.d(TAG, "        " + match.toString());
    }

    private void logMatchResult(Match match, int homeTeamChanceNormalized, int awayTeamChanceNormalized, int noScoreChanceNormalized, int missedChances) {
        logMatch(match);
        Log.d(TAG, "         (" + homeTeamChanceNormalized + "%) - (" + awayTeamChanceNormalized + "%)");
        Log.d(TAG, "            " + match.getHomeTeamGoals() + "  -  " + match.getAwayTeamGoals());
        Log.d(TAG, "                        missed chances " + missedChances + " (" + noScoreChanceNormalized + "%)");
    }

    private void logRound(int round) {
        Log.d(TAG, "  --------- Round " + round + " ---------");
    }

    private void logPlayingMatches() {
        Log.d(TAG, "----------- Playing -----------");

    }

    private void logMatchingTeams() {
        Log.d(TAG, "--------- Matching teams -------");
    }
}
