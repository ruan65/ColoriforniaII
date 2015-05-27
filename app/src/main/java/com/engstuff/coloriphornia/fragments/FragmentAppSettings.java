package com.engstuff.coloriphornia.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

import com.engstuff.coloriphornia.BuildConfig;
import com.engstuff.coloriphornia.R;
import com.engstuff.coloriphornia.activities.HelpActivity;
import com.engstuff.coloriphornia.data.Cv;
import com.engstuff.coloriphornia.helpers.AppHelper;
import com.engstuff.coloriphornia.helpers.PrefsHelper;

import java.util.Set;

public class FragmentAppSettings extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    Preference emailPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        emailPref = findPreference(getString(R.string.prefs_user_saved_emails));
        emailPref.setSummary(savedEmails() + "\n" + getString(R.string.prefs_summary));

        createPref(R.string.prefs_user_saved_emails, this);
        createPref(R.string.key_prefs_about, this);
        createPref(R.string.key_prefs_contacts, this);
        createPref(R.string.key_prefs_help, this);
    }

    private void createPref(int key, Preference.OnPreferenceClickListener l) {
        findPreference(getString(key))
                .setOnPreferenceClickListener(l);
    }

    private String savedEmails() {

        Set<String> strings = PrefsHelper.readFromPrefsAll(getActivity(), Cv.SAVED_EMAILS).keySet();

        StringBuilder sb = new StringBuilder();

        for (String s : strings) {
            sb.append(s + "\n");
        }
        return sb.toString();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        if (preference.getKey().equals(getString(R.string.prefs_user_saved_emails))) {

            new DialogFragmentSavedEmails().show(getFragmentManager(), null);

        } else if (preference.getKey().equals(getString(R.string.key_prefs_help))) {

            startActivity(new Intent(getActivity(), HelpActivity.class));

        } else if (preference.getKey().equals(getString(R.string.key_prefs_contacts))) {

            AppHelper.sendEmailToDeveloper(getActivity());

        } else if (preference.getKey().equals(getString(R.string.key_prefs_about))) {

            String message = getString(R.string.dialog_about_message_ver) + " "
                    + BuildConfig.VERSION_NAME + "\n\n"
                    + getString(R.string.info_about);

            new AlertDialog.Builder(getActivity())
                    .setTitle(getString(R.string.dialog_about_title))
                    .setMessage(message)
                    .setPositiveButton(getString(R.string.btn_ok), null)
                    .create()
                    .show();

        }
        return true;
    }


}