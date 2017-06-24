package selantoapps.soccerleaguesimulator.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import selantoapps.soccerleaguesimulator.R;
import selantoapps.soccerleaguesimulator.control.animation.HomeAnimationController;

/**
 * Created by antoniocappiello on 24/06/17.
 */

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.background)
    ViewGroup backgroundView;

    @BindView(R.id.overall_standings_table)
    TableLayout overallStandingsTable;

    @BindView(R.id.match_1)
    ViewGroup match1View;

    @BindView(R.id.match_2)
    ViewGroup match2View;

    @BindView(R.id.match_3)
    ViewGroup match3View;

    @BindView(R.id.match_4)
    ViewGroup match4View;

    @BindView(R.id.match_5)
    ViewGroup match5View;

    @BindView(R.id.match_6)
    ViewGroup match6View;

    private ViewGroup[] matchViews;

    private HomeAnimationController homeAnimationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the views
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        matchViews = new ViewGroup[]{match1View, match2View, match3View, match4View, match5View, match6View};
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
        homeAnimationController.startAnimations();
    }

    @OnClick(R.id.statistics_btn)
    public void onStatisticsButtonClicked() {
        //TODO
    }
}
