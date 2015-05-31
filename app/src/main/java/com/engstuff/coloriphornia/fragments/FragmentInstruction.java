package com.engstuff.coloriphornia.fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.engstuff.coloriphornia.R;

public class FragmentInstruction extends Fragment {

    TextView textView;
    ImageView imageView;
    int position;

    public FragmentInstruction() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root;

        if (position > 0) {
            root = inflater.inflate(R.layout.fragment_instruction, container, false);

            textView = (TextView) root.findViewById(R.id.instruction_text);
            imageView = (ImageView) root.findViewById(R.id.instruction_img);
        } else {
            root = new ListView(getActivity());
            TextView header = new TextView(getActivity());
            header.setText("Buttons");
            ((ListView) root).addHeaderView(header);

        }
        return root;
    }

    public void setText(String text) {

        try {
            textView.setText(text);
        } catch (Exception e) {
            Log.e(getClass().getName(), "Error: ", e);
        }
    }

    public void setImage(int id) {

        Context c = getActivity();

        try {
            imageView.setImageResource(c.getResources().
                    getIdentifier("drawable/instr_" + id, null, c.getPackageName()));
        } catch (Exception e) {
            Log.e(getClass().getName(), "Error: ", e);
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
