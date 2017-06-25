package selantoapps.soccerleaguesimulator.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import selantoapps.soccerleaguesimulator.BuildConfig;
import selantoapps.soccerleaguesimulator.R;
import selantoapps.soccerleaguesimulator.control.EventType;
import selantoapps.soccerleaguesimulator.control.animation.SplashAnimationController;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.intro_tv)
    TextView introTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the views
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        // Skip splash animation in development mode
        if (BuildConfig.SKIP_SPLASH) {
            goToStartScreen();
        } else {
            new SplashAnimationController(this, introTv).startAnimations();
        }
    }

    /**
     * When an {@link EventType#SPLASH_ANIMATION_ENDED} is posted on the event bus, then this
     * method will conclude the {@link SplashActivity} and start the game {@link HomeActivity}
     *
     * @param eventType
     */
    @Subscribe
    public void onAnimationEnded(EventType eventType) {
        if (eventType == EventType.SPLASH_ANIMATION_ENDED) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goToStartScreen();
                }
            }, 1400);
        }

    }

    /**
     * If the user click anywhere in the screen while the splash animations are running, then
     * the remaining animations will be skipped and the user will be brought to the the game
     * {@link HomeActivity}
     */
    @OnClick(R.id.root_view)
    public void goToStartScreen() {
        startActivity(new Intent(this, HomeActivity.class));
        // Fade in the new screen for a smoother transition
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

}
