package selantoapps.soccerleaguesimulator.view;

import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by antoniocappiello on 24/06/17.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
