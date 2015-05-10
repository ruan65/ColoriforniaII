package com.engstuff.coloriphornia.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.engstuff.coloriphornia.R;
import com.engstuff.coloriphornia.data.Cv;
import com.engstuff.coloriphornia.fragments.FragmentColorBox;
import com.engstuff.coloriphornia.fragments.SeekBarsColorControlFragment;
import com.engstuff.coloriphornia.helpers.AppHelper;
import com.engstuff.coloriphornia.helpers.ColorParams;
import com.engstuff.coloriphornia.helpers.PrefsHelper;

public class FontAndBackgroundSolidActivity extends BaseColorActivity {

    private TextView mText;

    private boolean tuneColor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mText = (TextView) findViewById(R.id.font_color);

        fragmentControl = new SeekBarsColorControlFragment();

        getFragmentManager().beginTransaction()

                .add(R.id.color_control_container, fragmentControl)
                .add(R.id.color_box_container, fragmentColorBox)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        AppHelper.setColorToColorBox(this, Cv.LAST_BACKGROUND, fragmentControl, fragmentColorBox);
        AppHelper.setLikesAndInfo(this, fragmentColorBox);

        mText.setTextColor(PrefsHelper.readFromPrefsInt(
                this, Cv.PREFS_RETAIN, Cv.LAST_COLOR_FONT));

        unlockInfo = true;
    }

    @Override
    public void onPause() {
        super.onPause();

        PrefsHelper.writeToPrefs(this, Cv.PREFS_RETAIN, Cv.LAST_BACKGROUND,
                fragmentColorBox.getHexColorParams());

        PrefsHelper.writeToPrefs(this, Cv.PREFS_RETAIN, Cv.LAST_COLOR_FONT,
                mText.getCurrentTextColor());

        unlockInfo = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        boolean rv = super.onCreateOptionsMenu(menu);
        tuneTextIcon.setVisible(true);
        return rv;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.tune_text:

                tuneColor = !tuneColor;

                tuneTextIcon.setIcon(tuneColor
                        ? R.drawable.ic_flip_to_front_white_36dp
                        : R.drawable.ic_flip_to_back_white_36dp);

                int currentTextColor = mText.getCurrentTextColor();
                int color = currentColorBox.getColor();

                fragmentControl.setControls(tuneColor
                        ? currentTextColor : color);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onColorControlChange(int p, int id) {

        if (tuneColor) {

            mText.setTextColor(fragmentControl.getColor());

            if (unlockInfo) {
                switchInfo(p, id);
                animInfoAndGone();
            }
        } else {
            super.onColorControlChange(p, id);
        }
    }

    @Override
    public void onInfoClicked(FragmentColorBox box) {

        fullColorStarted = true;

        AppHelper.startFullColorC(this, box.getHexColorParams(), getHexFont());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_font_and_background_solid;
    }

    public void setTextColorOpaque() {

        mText.setTextColor(mText.getCurrentTextColor() | 0xff000000);
    }

    public String getHexBackground() {
        return fragmentColorBox.getHexColorParams();
    }

    public String getHexFont() {
        return ColorParams.makeHexInfo(mText.getCurrentTextColor());
    }
}
