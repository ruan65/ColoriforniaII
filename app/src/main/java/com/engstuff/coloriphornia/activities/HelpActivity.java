package com.engstuff.coloriphornia.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.engstuff.coloriphornia.R;
import com.engstuff.coloriphornia.fragments.FragmentHelpContents;
import com.engstuff.coloriphornia.fragments.FragmentInstruction;

public class HelpActivity extends MockUpActivity implements FragmentHelpContents.ListSelectionListener {

    private FragmentInstruction fragmentInstruction;
    private FragmentHelpContents fragmentHelpContents;
    FragmentManager fm;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        fragmentInstruction = new FragmentInstruction();
        fragmentHelpContents = new FragmentHelpContents();
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
    }

    private boolean isInTwoPaneMode() {
        return findViewById(R.id.universal_fragment_container) == null;
    }
}
