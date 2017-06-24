package selantoapps.soccerleaguesimulator.control.animation;

import android.content.Context;
import android.os.Handler;
import android.view.ViewGroup;

import jp.wasabeef.blurry.Blurry;
import selantoapps.soccerleaguesimulator.control.Config;

/**
 * Created by antoniocappiello on 24/06/17.
 * <p>
 * This class orchestrates the animation in the Home screen.
 */

public class HomeAnimationController {

    private final Context context;

    private final ViewGroup backgroundView;

    private final ViewGroup overallStandingsView;

    private final ViewGroup[] matchViews;
    private boolean isBlurred;

    public HomeAnimationController(Context context, ViewGroup backgroundView, ViewGroup overallStandingsView, ViewGroup[] matchViews) {
        this.context = context;
        this.backgroundView = backgroundView;
        this.overallStandingsView = overallStandingsView;
        this.matchViews = matchViews;
    }

    private void clearPendingAnimations() {
        overallStandingsView.clearAnimation();
        for (ViewGroup matchView : matchViews) {
            matchView.clearAnimation();
        }
    }

    public void startAnimations() {
        clearPendingAnimations();

        long startOffset = Config.BASIC_ANIMATION_OFFSET;

        blurBackground(startOffset);

        // Start animating the "match" views...
        for (ViewGroup matchView : matchViews) {
            startOffset += Config.BASIC_ANIMATION_OFFSET / 3; // ...with a small delay from each other
            matchView.startAnimation(
                    AnimationProvider.getHomeAnimation(context, startOffset, Config.HOME_ANIMATION_DURATION)); //TODO initialize those animation before to improve performance
        }

        // ...while the overall standing view enter the screen.
        overallStandingsView.startAnimation(
                AnimationProvider.getHomeAnimation(context, startOffset + Config.HOME_ANIMATION_DURATION * 2, Config.HOME_ANIMATION_DURATION)); //TODO initialize those animation before to improve performance
    }

    private void blurBackground(long offset) {
        if (!isBlurred) {
            // do it only once
            isBlurred = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Blurry.with(context)
                            .animate((int) Config.HOME_ANIMATION_DURATION)
                            .radius(20)
                            .sampling(1)
                            .onto(backgroundView);
                }
            }, offset);
        }
    }
}
