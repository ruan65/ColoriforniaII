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
    private ListSelectionListener mCallBack;

    public FragmentHelpContents() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {

        mContentsAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.list_item_help_contents,
                R.id.list_item_contents_textview,
                getResources().getStringArray(R.array.help_contents));

        View rootView = inflater.inflate(R.layout.fragment_help_contents, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_help_contents);
        listView.setAdapter(mContentsAdapter);

        listView.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mCallBack = (ListSelectionListener) activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mCallBack.onItemSelected(position);
    }
}
