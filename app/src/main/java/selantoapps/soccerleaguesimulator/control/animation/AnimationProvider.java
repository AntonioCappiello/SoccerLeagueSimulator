package selantoapps.soccerleaguesimulator.control.animation;

import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;

import org.greenrobot.eventbus.EventBus;

import selantoapps.soccerleaguesimulator.R;
import selantoapps.soccerleaguesimulator.control.Config;

/**
 * Created by antoniocappiello on 24/06/17.
 * <p>
 * Helper class to avoid duplicate boilerplate code in creating basic animations
 */

public class AnimationProvider {
    private AnimationProvider() {
        //no instance
    }

    public static Animation get(Context context, int animRes) {
        return get(context, animRes, null);
    }

    public static Animation get(Context context, int animRes, final AnimationType animationType) {
        Animation animation = AnimationUtils.loadAnimation(context, animRes);
        animation.setDuration(Config.BASIC_ANIMATION_DURATION);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        if (null != animationType)
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    EventBus.getDefault().post(animationType);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        return animation;
    }

    public static Animation getHomeAnimation(Context context, long animationOffset, long animationDuration) {
        Animation enterAnimation = AnimationUtils.loadAnimation(context, R.anim.task_open_enter);
        enterAnimation.setDuration(animationDuration);
        enterAnimation.setStartOffset(animationOffset);
        enterAnimation.setInterpolator(new OvershootInterpolator());
        enterAnimation.setFillBefore(true);
        return enterAnimation;
    }
}
