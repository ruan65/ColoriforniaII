package com.engstuff.coloriphornia.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.engstuff.coloriphornia.R;
import com.engstuff.coloriphornia.data.Cv;
import com.engstuff.coloriphornia.fragments.FragmentHelpContents;
import com.engstuff.coloriphornia.fragments.FragmentInstruction;

public class HelpActivity extends MockUpActivity
        implements FragmentHelpContents.ListSelectionListener {

    private FragmentInstruction fragmentInstruction;
    private FragmentHelpContents fragmentHelpContents;

    private FragmentManager fm;

    private int selectedItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            selectedItem = savedInstanceState.getInt(Cv.SELECTED_ITEM, 0);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(Cv.SELECTED_ITEM, selectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        fragmentHelpContents = new FragmentHelpContents();
        fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setPosition(selectedItem);

        fm = getFragmentManager();

        if (isInTwoPaneMode()) {

            fm.beginTransaction()
                    .replace(R.id.help_list_fragment_container, fragmentHelpContents)
                    .replace(R.id.help_instruction_fragment_container, fragmentInstruction)
                    .commit();
        } else {

            fm.beginTransaction()
                    .replace(R.id.universal_fragment_container, new FragmentHelpContents())
                    .commit();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (isInTwoPaneMode()) {

            final ListView lv = fragmentHelpContents.getmListContents();

            new Handler().post(new Runnable() {

                @Override
                public void run() {

                    lv.performItemClick(
                            lv.getChildAt(selectedItem),
                            selectedItem,
                            lv.getAdapter().getItemId(selectedItem));
                }
            });
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_help;
    }

    @Override
    protected String composeEmailBody(boolean calledFromContextMenu) {
        return "";
    }

    @Override
    public void onItemSelected(int position) {

        selectedItem = position;
        fragmentInstruction = null;

        fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setPosition(position);

        if (isInTwoPaneMode()) {

            fm.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .replace(R.id.help_instruction_fragment_container, fragmentInstruction)
                    .commit();
        } else {

            fm.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .replace(R.id.universal_fragment_container, fragmentInstruction)
                    .addToBackStack(null)
                    .commit();

        }
        fm.executePendingTransactions();

        if (position > 1) {

            fragmentInstruction.setText(
                    getResources().getStringArray(R.array.help_description)[position]);

            fragmentInstruction.setImage(position);
        }

    }

    private boolean isInTwoPaneMode() {
        return findViewById(R.id.universal_fragment_container) == null;
    }
}
