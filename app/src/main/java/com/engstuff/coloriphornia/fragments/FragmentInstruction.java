package com.engstuff.coloriphornia.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.engstuff.coloriphornia.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentInstruction extends Fragment {

    TextView textView;
    ImageView imageView;
    int position;
    Activity ctx;

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

            root = new ListView(ctx);
            ((ListView) root).setAdapter(createAdapter());

        }
        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ctx = activity;
    }

    public void setText(String text) {

        try {
            textView.setText(text);
        } catch (Exception e) {
            Log.e(getClass().getName(), "Error: ", e);
        }
    }

    public void setImage(int id) {

        try {
            imageView.setImageResource(ctx.getResources().
                    getIdentifier("drawable/instr_" + id, null, ctx.getPackageName()));
        } catch (Exception e) {
            Log.e(getClass().getName(), "Error: ", e);
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private SimpleAdapter createAdapter() {

        String[] texts = ctx.getResources().getStringArray(R.array.help_buttons);

        int[] images = {R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher};
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> m;

        for (int i = 0; i < texts.length; i++) {

            m = new HashMap<>();
            m.put("text", texts[i]);
            m.put("img", images[i]);
            data.add(m);
        }

        return new SimpleAdapter(ctx,
                data,
                R.layout.help_btn_list_item,
                new String[]{"img", "text"},
                new int[]{R.id.help_btn_img, R.id.help_btn_text});
    }
}
