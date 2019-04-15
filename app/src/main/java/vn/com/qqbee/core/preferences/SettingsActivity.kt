package vn.com.qqbee.core.preferences

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import vn.com.qqbee.App
import vn.com.qqbee.R
import vn.com.qqbee.core.utils.BaseActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity() {

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    private lateinit var app: App

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        app = application as App
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        title = getString(R.string.action_settings)
    }
}
