package com.engstuff.coloriphornia.activities;

import android.os.Bundle;

import com.engstuff.coloriphornia.R;

public class HelpActivity extends MockUpActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_help;
    }

    @Override
    protected String composeEmailBody(boolean calledFromContextMenu) {
        return "";
    }

}
