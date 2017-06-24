package selantoapps.soccerleaguesimulator.model;

import com.google.auto.value.AutoValue;

/**
 * Created by antoniocappiello on 24/06/17.
 */

@AutoValue
public abstract class Match {

    public static Builder builder() {
        return new AutoValue_Match.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setHomeTeam(Team homeTeam);

        public abstract Builder setAwayTeam(Team awayTeam);

        public abstract Builder setHomeTeamGoals(int homeTeamGoals);

        public abstract Builder setAwayTeamGoals(int awayTeamGoals);

        public abstract Match build();
    }

    public abstract Team homeTeam();

    public abstract Team awayTeam();

    public abstract int homeTeamGoals();

    public abstract int awayTeamGoals();

}
