package com.engstuff.coloriphornia.activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.engstuff.coloriphornia.R;
import com.engstuff.coloriphornia.data.Cv;
import com.engstuff.coloriphornia.fragments.FragmentColorBox;
import com.engstuff.coloriphornia.fragments.SeekBarsColorControlFragment;
import com.engstuff.coloriphornia.helpers.AppHelper;
import com.engstuff.coloriphornia.helpers.ColorParams;
import com.engstuff.coloriphornia.helpers.PrefsHelper;

public class FontAndBackgroundActivity extends BaseColorActivity {

    private TextView mText;
    private SeekBar mSeekBar;

    private boolean tuneColor;
    private boolean bold, italic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mText = (TextView) findViewById(R.id.font_color);

        mSeekBar = (SeekBar) findViewById(R.id.seek_bar_text_zoom);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int p, boolean fromUser) {

                mText.setTextSize(TypedValue.COMPLEX_UNIT_SP, interpolate(p));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        fragmentControl = new SeekBarsColorControlFragment();

        getFragmentManager().beginTransaction()

                .add(R.id.color_control_container, fragmentControl)
                .add(R.id.color_box_container, fragmentColorBox)
                .commit();
    }

    /**
     * Since I want non linear text sizing
     * @param n taken from SeekBar progress
     * @return float value accepted by TextView.setTextSize()
     */
    private float interpolate(int n) {

        float k = n / 10f;
        return k * k + 10f;
    }

    @Override
    public void onResume() {
        super.onResume();

        AppHelper.setColorToColorBox(this, Cv.LAST_BACKGROUND, fragmentControl, fragmentColorBox);
        AppHelper.setLikesAndInfo(this, fragmentColorBox);

        int tColor = PrefsHelper.readFromPrefsInt(
                this, Cv.PREFS_RETAIN, Cv.LAST_COLOR_FONT);

        mText.setTextColor(tColor == 0 ? Color.BLACK : tColor);

        mText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, interpolate(mSeekBar.getProgress()));

        mText.setText(Html.fromHtml(getString(R.string.html_dummy_citation)));

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
        boldIcon.setVisible(true);
        italicIcon.setVisible(true);

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

            case R.id.text_bold:

                bold = !bold;
                if (italic) {
                    mText.setTypeface(null, bold ? Typeface.BOLD_ITALIC : Typeface.ITALIC);
                } else {
                    mText.setTypeface(null, bold ? Typeface.BOLD : Typeface.NORMAL);
                }
                break;

            case R.id.text_italic:

                italic = !italic;
                if (bold) {
                    mText.setTypeface(null, italic ? Typeface.BOLD_ITALIC : Typeface.BOLD);
                } else {
                    mText.setTypeface(null, italic ? Typeface.ITALIC : Typeface.NORMAL);
                }
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
        return R.layout.activity_font_and_background;
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
