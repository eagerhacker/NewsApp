package com.savvynomad.newsapp.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.savvynomad.newsapp.R
import com.savvynomad.newsapp.helper.Constants

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var sp : SharedPreferences
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        sp = PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listPreferences = preferenceManager.findPreference<ListPreference>("language")
        listPreferences?.setOnPreferenceChangeListener { preference, newValue ->
            Constants.language = newValue.toString()
            true
        }

    }

}