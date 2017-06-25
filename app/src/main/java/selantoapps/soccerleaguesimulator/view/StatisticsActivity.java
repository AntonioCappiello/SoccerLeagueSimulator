package selantoapps.soccerleaguesimulator.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import selantoapps.soccerleaguesimulator.R;
import selantoapps.soccerleaguesimulator.control.TeamResultByPointsComparator;
import selantoapps.soccerleaguesimulator.model.MatchStatisticsModel;
import selantoapps.soccerleaguesimulator.model.StatisticsModel;
import selantoapps.soccerleaguesimulator.model.TeamResult;
import selantoapps.soccerleaguesimulator.view.widget.OverallStandingsTableView;
import selantoapps.soccerleaguesimulator.view.widget.TextView;

/**
 * Created by antoniocappiello on 25/06/17.
 */

public class StatisticsActivity extends AppCompatActivity {

    @BindView(R.id.winner_logo_iv)
    ImageView winnerLogoIv;

    @BindView(R.id.winner_name_tv)
    TextView winnerNameTv;

    @BindView(R.id.runner_up_logo_iv)
    ImageView runnerUpLogoIv;

    @BindView(R.id.runner_up_name_tv)
    TextView runnerUpNameTv;

    @BindView(R.id.game_count_tv)
    TextView gameCountTv;

    @BindView(R.id.match_count_tv)
    TextView matchCountTv;

    @BindView(R.id.scored_count_tv)
    TextView scoredCountTv;

    @BindView(R.id.statistics_table)
    OverallStandingsTableView statisticsTable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ButterKnife.bind(this);

        List<TeamResult> teamResults = MatchStatisticsModel.getInstance().findAll();
        Collections.sort(teamResults, new TeamResultByPointsComparator());
        statisticsTable.setData(teamResults);

        winnerLogoIv.setImageResource(teamResults.get(0).getTeam().logo());
        winnerNameTv.setText(teamResults.get(0).getTeam().name());

        runnerUpLogoIv.setImageResource(teamResults.get(1).getTeam().logo());
        runnerUpNameTv.setText(teamResults.get(1).getTeam().name());

        gameCountTv.setText(String.valueOf(StatisticsModel.getInstance().getGamesCount()));
        matchCountTv.setText(String.valueOf(StatisticsModel.getInstance().getMatchesCount()));
        scoredCountTv.setText(String.valueOf(StatisticsModel.getInstance().getGoalsCount()));
    }
}
