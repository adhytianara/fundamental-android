package bangkit.adhytia.github_user.view

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import bangkit.adhytia.github_user.R
import bangkit.adhytia.github_user.utils.AlarmReceiver

class PreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var REMINDER: String
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var isReminderPreference: SwitchPreference

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preferences)
        alarmReceiver = AlarmReceiver()
        init()
        setSummaries()
    }

    private fun init() {
        REMINDER = resources.getString(R.string.reminder)
        isReminderPreference = findPreference<SwitchPreference>(REMINDER) as SwitchPreference
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        isReminderPreference.isChecked = sh.getBoolean(REMINDER, false)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == REMINDER) {
            val isActive = sharedPreferences.getBoolean(REMINDER, false)
            isReminderPreference.isChecked = isActive
            if (isActive) {
                setRepeatingAlarm()
            } else {
                cancelAlarm()
            }
        }
    }

    private fun setRepeatingAlarm() {
        context?.let {
            alarmReceiver.setRepeatingAlarm(
                it, AlarmReceiver.ALARM_TIME, AlarmReceiver.ALARM_MESSAGE
            )
        }
    }

    private fun cancelAlarm() {
        context?.let {
            alarmReceiver.cancelAlarm(
                it
            )
        }
    }
}