package selantoapps.soccerleaguesimulator.control.animation;

import android.content.Context;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import selantoapps.soccerleaguesimulator.R;
import selantoapps.soccerleaguesimulator.control.Config;
import selantoapps.soccerleaguesimulator.control.EventType;

/**
 * Created by antoniocappiello on 24/06/17.
 * <p>
 * This class orchestrates the animation in the Splash screen
 * When the last animation ends then a {@link EventType#SPLASH_ANIMATION_ENDED} event is fired on
 * the event bus. That will be a signal for the Splash screen to conclude and go into the game.
 */

public class SplashAnimationController {

    private final Context context;

    private final TextView introTv;

    public SplashAnimationController(Context context, TextView introTv) {
        this.context = context;
        this.introTv = introTv;
        EventBus.getDefault().register(this);
    }

    public void startAnimations() {
        Animation slideInAnimation = AnimationProvider.get(
                context,
                R.anim.slide_in_right, // Animation description
                AnimationType.ENTER_AUTHOR); // Animation type to fire on the event bus when the animation ends.
        slideInAnimation.setStartOffset(Config.BASIC_ANIMATION_OFFSET);
        introTv.startAnimation(slideInAnimation);
    }

    /**
     * Listen to {@link AnimationType} events on the bus. Each of this events indicates when a specific
     * animation is concluded. This is the signal to start preparing and firing the next animation.
     * This method control the order of the animations.
     *
     * @param animationType The animation that is just concluded.
     */
    @Subscribe
    public void onAnimationEnded(AnimationType animationType) {
        switch (animationType) {
            case ENTER_AUTHOR:
                introTv.startAnimation(AnimationProvider.get(context, android.R.anim.fade_out, AnimationType.EXIT_AUTHOR));
                break;
            case EXIT_AUTHOR:
                introTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
                introTv.setText(R.string.presents);
                introTv.startAnimation(AnimationProvider.get(context, R.anim.slide_in_right, AnimationType.ENTER_PRESENTS));
                break;
            case ENTER_PRESENTS:
                introTv.startAnimation(AnimationProvider.get(context, android.R.anim.fade_out, AnimationType.EXIT_PRESENTS));
                break;
            case EXIT_PRESENTS:
                introTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 70);
                introTv.setText(R.string.app_name_extended);
                Animation anim = AnimationProvider.get(context, R.anim.task_open_enter, AnimationType.ENTER_GAME_NAME);
                anim.setStartOffset(Config.BASIC_ANIMATION_OFFSET);
                introTv.startAnimation(anim);
                break;
            case ENTER_GAME_NAME:
                EventBus.getDefault().unregister(this);
                EventBus.getDefault().post(EventType.SPLASH_ANIMATION_ENDED);
                break;
        }
    }
}
