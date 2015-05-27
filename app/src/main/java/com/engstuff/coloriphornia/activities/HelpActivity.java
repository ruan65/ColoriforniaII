package com.engstuff.coloriphornia.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.engstuff.coloriphornia.R;
import com.engstuff.coloriphornia.fragments.FragmentHelpContents;
import com.engstuff.coloriphornia.fragments.FragmentInstruction;

public class HelpActivity extends MockUpActivity implements FragmentHelpContents.ListSelectionListener {

    private FragmentInstruction fragmentInstruction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isInTwoPaneMode()) {

            fragmentInstruction = new FragmentInstruction();
        } else {

            getFragmentManager().beginTransaction()
                    .add(R.id.universal_fragment_container, new FragmentHelpContents())
                    .commit();
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
        Log.d("ml", "List position = " + position);

        if (fragmentInstruction == null) {

            fragmentInstruction = new FragmentInstruction();
        }

        if (!isInTwoPaneMode()) {

            FragmentManager fm = getFragmentManager();

            fm.beginTransaction()
            .replace(R.id.universal_fragment_container, fragmentInstruction)
            .addToBackStack(null)
            .commit();

            fm.executePendingTransactions();
        }


    }

    private boolean isInTwoPaneMode() {
        return findViewById(R.id.universal_fragment_container) == null;
    }
}
