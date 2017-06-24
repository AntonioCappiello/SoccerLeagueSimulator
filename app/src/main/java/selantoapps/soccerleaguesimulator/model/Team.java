package selantoapps.soccerleaguesimulator.model;

import com.google.auto.value.AutoValue;

/**
 * Created by antoniocappiello on 24/06/17.
 */

@AutoValue
public abstract class Team {

    public static Builder builder() {
        return new AutoValue_Team.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setName(String name);

        public abstract Builder setLogo(int resId);

        public abstract Builder setStrength(int strength);

        public abstract Team build();
    }

    public abstract String name();

    public abstract int logo();

    public abstract int strength();

}
