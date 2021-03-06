package selantoapps.soccerleaguesimulator.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import selantoapps.soccerleaguesimulator.R;
import selantoapps.soccerleaguesimulator.control.GameSimulator;
import selantoapps.soccerleaguesimulator.control.TeamResultByPointsComparator;
import selantoapps.soccerleaguesimulator.control.TeamResultGenerator;
import selantoapps.soccerleaguesimulator.control.animation.HomeAnimationController;
import selantoapps.soccerleaguesimulator.model.Match;
import selantoapps.soccerleaguesimulator.model.MatchStatisticsModel;
import selantoapps.soccerleaguesimulator.model.TeamResult;
import selantoapps.soccerleaguesimulator.view.widget.MatchView;
import selantoapps.soccerleaguesimulator.view.widget.OverallStandingsTableView;

/**
 * Created by antoniocappiello on 24/06/17.
 */

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    @BindView(R.id.background)
    ViewGroup backgroundView;

    @BindView(R.id.overall_standings_table)
    OverallStandingsTableView overallStandingsTable;

    @BindView(R.id.match_1)
    MatchView match1View;

    @BindView(R.id.match_2)
    MatchView match2View;

    @BindView(R.id.match_3)
    MatchView match3View;

    @BindView(R.id.match_4)
    MatchView match4View;

    @BindView(R.id.match_5)
    MatchView match5View;

    @BindView(R.id.match_6)
    MatchView match6View;

    private MatchView[] matchViews;

    private HomeAnimationController homeAnimationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the views
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        matchViews = new MatchView[]{match1View, match2View, match3View, match4View, match5View, match6View};
        showResultViews(false);

        homeAnimationController = new HomeAnimationController(this, backgroundView, overallStandingsTable, matchViews);

    }

    private void showResultViews(boolean shouldShow) {
        if (shouldShow) {
            overallStandingsTable.setVisibility(View.VISIBLE);
            for (ViewGroup viewGroup : matchViews) {
                viewGroup.setVisibility(View.VISIBLE);
            }
        } else {
            overallStandingsTable.setVisibility(View.INVISIBLE);
            for (ViewGroup viewGroup : matchViews) {
                viewGroup.setVisibility(View.GONE);
            }
        }
    }

    @OnClick(R.id.play_btn)
    public void onPlayButtonClicked() {
        showResultViews(true);

        // Create games
        List<Match> matches = GameSimulator.getInstance().start(); //TODO move to async task
        for (int i = 0; i < matches.size(); i++) {
            matchViews[i].setData(matches.get(i));
        }

        List<TeamResult> teamResults = TeamResultGenerator.fromMatches(matches);
        Collections.sort(teamResults, new TeamResultByPointsComparator());
        overallStandingsTable.setData(teamResults);

        MatchStatisticsModel.getInstance().update(teamResults);
        logStatistics();

        // Show games
        homeAnimationController.startAnimations();
    }

    @OnClick(R.id.statistics_btn)
    public void onStatisticsButtonClicked() {
        startActivity(new Intent(this, StatisticsActivity.class));
    }

    private void logStatistics() {
        Log.i(TAG, "====== Games played: " + MatchStatisticsModel.getInstance().getGamePlayedCount() + " ======");
        List<TeamResult> teamResults = MatchStatisticsModel.getInstance().findAll();
        for (TeamResult teamResult : teamResults) {
            Log.i(TAG, teamResult.toString());
        }
    }
}
