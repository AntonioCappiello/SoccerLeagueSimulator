package selantoapps.soccerleaguesimulator.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

import selantoapps.soccerleaguesimulator.R;
import selantoapps.soccerleaguesimulator.model.TeamModel;
import selantoapps.soccerleaguesimulator.model.TeamResult;

/**
 * Created by antoniocappiello on 25/06/17.
 */

public class OverallStandingsTableView extends TableLayout {

    private List<TeamResult> teamResults;

    private List<OverallStandingsRowView> tableRows;

    public OverallStandingsTableView(Context context) {
        super(context);
        init();
    }

    public OverallStandingsTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        TableLayout rootView = (TableLayout) inflate(getContext(), R.layout.overall_standings_table, this);
        rootView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        rootView.setBackgroundResource(R.drawable.rounded_bg);

        int teamCount = TeamModel.getInstance().getTeamsCount();
        tableRows = new ArrayList<>(teamCount);

        for (int i = 0; i < teamCount; i++) {
            tableRows.add(new OverallStandingsRowView(getContext()));
            rootView.addView(tableRows.get(i));
        }
    }

    private void refreshView() {
        for (int i = 0; i < tableRows.size(); i++) {
            tableRows.get(i).setData(teamResults.get(i));
        }
    }

    public void setData(List<TeamResult> teamResults) {
        this.teamResults = teamResults;
        sortTeams();
        refreshView();
    }

    private void sortTeams() {
        //TODO
    }
}
