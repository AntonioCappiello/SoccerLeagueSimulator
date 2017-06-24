package selantoapps.soccerleaguesimulator.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import selantoapps.soccerleaguesimulator.R;
import selantoapps.soccerleaguesimulator.model.Match;

/**
 * Created by antoniocappiello on 24/06/17.
 */

public class MatchView extends RelativeLayout {

    private Match match;

    @BindView(R.id.home_team_iv)
    ImageView homeTeamIv;

    @BindView(R.id.home_team_tv)
    TextView homeTeamTv;

    @BindView(R.id.away_team_iv)
    ImageView awayTeamIv;

    @BindView(R.id.away_team_tv)
    TextView awayTeamTv;

    @BindView(R.id.match_result_tv)
    TextView matchResultTv;

    public MatchView(Context context) {
        super(context);
        init();
    }

    public MatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MatchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.match, this);
        ButterKnife.bind(this);
        refreshView();
    }

    public void setData(Match match) {
        this.match = match;
        refreshView();
    }

    private void refreshView() {
        if (null == match) {
            return;
        }

        homeTeamIv.setImageResource(match.homeTeam().logo());
        homeTeamTv.setText(match.homeTeam().name());

        awayTeamIv.setImageResource(match.awayTeam().logo());
        awayTeamTv.setText(match.awayTeam().name());

        matchResultTv.setText(match.homeTeamGoals() +
                "  -  " +
                match.awayTeamGoals()
        );
    }
}
