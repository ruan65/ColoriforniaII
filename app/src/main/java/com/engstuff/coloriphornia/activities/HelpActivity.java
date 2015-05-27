package com.engstuff.coloriphornia.activities;

import android.os.Bundle;
import android.util.Log;

import com.engstuff.coloriphornia.R;
import com.engstuff.coloriphornia.fragments.FragmentHelpContents;

public class HelpActivity extends MockUpActivity implements FragmentHelpContents.ListSelectionListener {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .add(R.id.help_list_fragment_container, new FragmentHelpContents())
                .commit();
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
    }
}
