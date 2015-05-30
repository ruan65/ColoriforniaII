package com.engstuff.coloriphornia.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;

import com.engstuff.coloriphornia.R;
import com.engstuff.coloriphornia.data.Cv;
import com.engstuff.coloriphornia.fragments.FragmentHelpContents;
import com.engstuff.coloriphornia.fragments.FragmentInstruction;

public class HelpActivity extends MockUpActivity implements FragmentHelpContents.ListSelectionListener {

    private FragmentInstruction fragmentInstruction;
    private FragmentHelpContents fragmentHelpContents;

    private FragmentManager fm;

    private int selectedItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        fragmentHelpContents = new FragmentHelpContents();
        fragmentInstruction = new FragmentInstruction();

        fm = getFragmentManager();

        super.onCreate(savedInstanceState);

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
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(Cv.SELECTED_ITEM, selectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        selectedItem = savedInstanceState.getInt(Cv.SELECTED_ITEM, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isInTwoPaneMode()) {

            final ListView lv = fragmentHelpContents.getListView();

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

        if (fragmentInstruction == null) {

            fragmentInstruction = new FragmentInstruction();
        }

        if (!isInTwoPaneMode()) {

            fm.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .replace(R.id.universal_fragment_container, fragmentInstruction)
                    .addToBackStack(null)
                    .commit();

            fm.executePendingTransactions();
        }

        fragmentInstruction.setText(
                getResources().getStringArray(R.array.help_description)[position]);

        fragmentInstruction.setImage(position);

        selectedItem = position;
    }

    private boolean isInTwoPaneMode() {
        return findViewById(R.id.universal_fragment_container) == null;
    }
}
