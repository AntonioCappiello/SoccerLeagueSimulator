package selantoapps.soccerleaguesimulator.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

import selantoapps.soccerleaguesimulator.R;


/**
 * Created by antoniocappiello on 16/06/17.
 *
 * Custom TextView which automatically load custom fonts.
 */

public class TextView extends AppCompatTextView {

    private static final String TAG = TextView.class.getName();

    public TextView(Context context) {
        super(context);
        init(null, 0, 0);
    }

    public TextView(Context context, AttributeSet attributes) {
        super(context, attributes);
        init(attributes, 0, 0);
    }

    public TextView(Context context, AttributeSet attributes, int defaultStyleAttribute) {
        super(context, attributes, defaultStyleAttribute);
        init(attributes, defaultStyleAttribute, 0);
    }

    private void init(AttributeSet attributes, int defaultStyleAttribute, int defaultStyleResourceIdentifier) {
        if (attributes != null && !isInEditMode()) {
            TypedArray a = getContext().obtainStyledAttributes(
                    attributes,
                    R.styleable.TextView,
                    defaultStyleAttribute,
                    defaultStyleResourceIdentifier
            );
            String typefaceName = a.getString(R.styleable.TextView_typeface);
            String extension = a.getString(R.styleable.TextView_fontAssetExtension);

            if (typefaceName != null) {
                try {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + typefaceName + "." + extension);
                    setTypeface(myTypeface);
                } catch (Exception e) {
                    Log.e(TAG, "could not load font " + typefaceName + "." + extension, e);
                }
            }
            a.recycle();
        }
    }
}
