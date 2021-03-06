package com.engstuff.coloriphornia.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import com.engstuff.coloriphornia.R;
import com.engstuff.coloriphornia.data.Cv;
import com.engstuff.coloriphornia.fragments.FragmentFullScreenColor;
import com.engstuff.coloriphornia.helpers.PrefsHelper;
import com.engstuff.coloriphornia.interfaces.HideInfoListener;
import com.engstuff.coloriphornia.interfaces.OnFlingListener;

import java.util.ArrayList;
import java.util.List;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class FullScreenColorC extends Activity implements OnFlingListener, HideInfoListener {

    private boolean calledFromFavorites;
    private List<String> savedColorsSet;
    private int position;
    private boolean hideInfoFlag;

    String startedColor = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_color_c);

        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
        Intent intent = getIntent();

        startedColor = intent.getStringExtra(Cv.EXTRA_MESSAGE_COLOR_1);
        String fontColor = intent.getStringExtra(Cv.EXTRA_MESSAGE_FONT_COLOR);
        calledFromFavorites = intent.getBooleanExtra(Cv.CALLED_FROM_FAVORITES, false);

        if (calledFromFavorites) {
            savedColorsSet = new ArrayList<>(
                    PrefsHelper.readFromPrefsAll(this, Cv.SAVED_COLORS).keySet());
            position = savedColorsSet.indexOf(startedColor);
        }

        FragmentFullScreenColor fragmentFullScreenColor = prepareFragment(startedColor, fontColor);
        performFragmentTransaction(fragmentFullScreenColor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_l, R.anim.slide_out_l);
    }

    @Override
    public void onFling(boolean next) {

        if (calledFromFavorites && savedColorsSet.size() > 0 && position != -1) {

            String hex = next
                    ? (position < savedColorsSet.size() - 1 ? savedColorsSet.get(++position)
                    : savedColorsSet.get(position = 0))
                    : position > 0 ? savedColorsSet.get(--position)
                    : savedColorsSet.get(position = savedColorsSet.size() - 1);

            performFragmentTransaction(prepareFragment(hex, null));
        } else finish();
    }

    private FragmentFullScreenColor prepareFragment(String background, String font) {

        FragmentFullScreenColor fragment = new FragmentFullScreenColor();

        fragment.setHexBackColorString(background);

        if (null != font) {
            fragment.setHexFontColorString(font);
        }
        return fragment;
    }

    void performFragmentTransaction(FragmentFullScreenColor f) {

        FragmentManager manager = getFragmentManager();

        manager.beginTransaction()
                .replace(R.id.frame_for_full_screen_color_fragment, f)
                .commit();

        manager.executePendingTransactions();

        if (hideInfoFlag) {
            f.closeInfo();
        }
    }

    @Override
    public void onHideInfoInvoked(boolean hideInfoIfTrue) {
        hideInfoFlag = hideInfoIfTrue;
    }
}