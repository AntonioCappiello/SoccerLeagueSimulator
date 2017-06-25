package selantoapps.soccerleaguesimulator.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableRow;

import butterknife.BindView;
import butterknife.ButterKnife;
import selantoapps.soccerleaguesimulator.R;
import selantoapps.soccerleaguesimulator.model.TeamResult;

/**
 * Created by antoniocappiello on 25/06/17.
 */

public class OverallStandingsRowView extends TableRow {

    @BindView(R.id.logo_iv)
    ImageView logoIv;

    @BindView(R.id.name_tv)
    TextView nameTv;

    @BindView(R.id.won_tv)
    TextView wonTv;

    @BindView(R.id.lost_tv)
    TextView lostTv;

    @BindView(R.id.draw_tv)
    TextView drawTv;

    @BindView(R.id.scored_tv)
    TextView scoredTv;

    @BindView(R.id.conceded_tv)
    TextView concededTv;

    @BindView(R.id.overall_tv)
    TextView overallTv;

    @BindView(R.id.points_tv)
    TextView pointsTv;

    private TeamResult teamResult;

    public OverallStandingsRowView(Context context) {
        super(context);
        init();
    }

    public OverallStandingsRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        inflate(getContext(), R.layout.raw_content, this);
        setGravity(Gravity.CENTER_VERTICAL);
        ButterKnife.bind(this);
    }

    private void refreshView() {
        logoIv.setImageResource(teamResult.getTeam().logo());
        nameTv.setText(teamResult.getTeam().name());
        wonTv.setText(String.valueOf(teamResult.getWon()));
        lostTv.setText(String.valueOf(teamResult.getLost()));
        drawTv.setText(String.valueOf(teamResult.getDraw()));
        scoredTv.setText(String.valueOf(teamResult.getScored()));
        concededTv.setText(String.valueOf(teamResult.getConceded()));
        overallTv.setText(String.valueOf(teamResult.getOverall()));
        pointsTv.setText(String.valueOf(teamResult.getPoints()));
    }

    public void setData(TeamResult teamResult) {
        this.teamResult = teamResult;
        refreshView();
    }
}
