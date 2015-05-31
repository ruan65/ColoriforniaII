package com.engstuff.coloriphornia.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.engstuff.coloriphornia.R;

public class FragmentHelpContents extends Fragment implements AdapterView.OnItemClickListener {

    public interface ListSelectionListener {
        void onItemSelected(int position);
    }

    private ArrayAdapter<String> mContentsAdapter;
    private ListSelectionListener mOnClickCallBack;
    private ListView mListContents;

    public FragmentHelpContents() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {

        mContentsAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_activated_2,
                android.R.id.text1,
                getResources().getStringArray(R.array.help_contents));  // data sits in the XML

        View rootView = inflater.inflate(R.layout.fragment_help_contents, container, false);

        mListContents = (ListView) rootView.findViewById(R.id.listview_help_contents);
        mListContents.setAdapter(mContentsAdapter);
        mListContents.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mOnClickCallBack = (ListSelectionListener) activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mOnClickCallBack.onItemSelected(position);
    }

    public ListView getmListContents() {
        return mListContents;
    }
}
