package com.engstuff.coloriphornia.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.engstuff.coloriphornia.R;
import com.engstuff.coloriphornia.data.Cv;

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

        if (position > 1) {
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

        String[] texts = ctx.getResources().getStringArray(position == 0
                ? R.array.help_buttons
                : R.array.help_navigation);

        int[] images =
                position == 0
                ? new int[]
                {
                        R.drawable.ic_menu_white_36dp,
                        R.drawable.ic_repeat_white_36dp,
                        R.drawable.ic_send_white_36dp,
                        R.drawable.ic_blur_on_white_36dp,
                        R.drawable.ic_blur_off_white_36dp,
                        R.drawable.ic_info_outline_white_36dp,
                        R.drawable.ic_photo_library_white_36dp,
                        R.drawable.ic_loyalty_white_36dp,
                        R.drawable.ic_target_w,
                        R.drawable.ic_sync_white_36dp,
                        R.drawable.ic_flip_to_back_white_36dp,
                        R.drawable.ic_flip_to_front_white_36dp,
                        R.drawable.ic_format_bold_white_36dp,
                        R.drawable.ic_format_italic_white_36dp,
                        R.drawable.ic_add_circle_outline_white_36dp,
                        R.drawable.ic_remove_circle_outline_white_36dp,
                        R.drawable.ic_delete_white_36dp,
                        R.drawable.ic_done_white_48dp,
                        R.drawable.ic_select_all_white_36dp,
                        R.drawable.ic_undo_white_36dp
                }
                : new int[]
                {
                        R.drawable.ic_looks_two_white_36dp,
                        R.drawable.ic_tune_white_36dp,
                        R.drawable.ic_track_changes_white_36dp,
                        R.drawable.ic_photo_library_white_36dp,
                        R.drawable.ic_format_quote_white_36dp,
                        R.drawable.ic_format_color_text_white_36dp,
                        R.drawable.ic_settings_white_36dp,
                        R.drawable.ic_favorite_white_36dp,
                        R.drawable.ic_help_white_36dp
                };

        ArrayList<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> m;

        for (int i = 0; i < texts.length; i++) {

            m = new HashMap<>();
            m.put(Cv.IMG, images[i]);
            m.put(Cv.TEXT, texts[i]);
            data.add(m);
        }

        return new SimpleAdapter(ctx,
                data,
                position == 0 ? R.layout.help_btn_list_item : R.layout.help_navigation_list_item,
                new String[]{Cv.IMG, Cv.TEXT},
                new int[]{R.id.help_btn_img, R.id.help_btn_text});
    }
}
