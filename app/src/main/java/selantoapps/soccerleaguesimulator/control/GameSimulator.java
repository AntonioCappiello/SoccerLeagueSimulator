package selantoapps.soccerleaguesimulator.control;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import selantoapps.soccerleaguesimulator.model.Match;
import selantoapps.soccerleaguesimulator.model.Team;
import selantoapps.soccerleaguesimulator.model.TeamModel;

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
        List<Match> matches = createMatches();
        playMatches(matches);
        logGameEnd();
        return matches;
    }

    private void playMatches(List<Match> matches) {
        for (Match match : matches) {
            match.setHomeTeamGoals(
                    random.nextInt(5)
            );
            match.setAwayTeamGoals(
                    random.nextInt(5)
            );
        }
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

    private void logGameEnd() {
        Log.d(TAG, "================================");
    }

    private void logGameStart() {
        Log.d(TAG, " ");
        Log.d(TAG, "=========== New game ===========");
    }

    private void logMatch(Match match) {
        Log.d(TAG, "        " + match.toString());
    }

    private void logRound(int round) {
        Log.d(TAG, "  --------- Round " + round + " ---------");
    }
}
