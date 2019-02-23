package vn.com.qqbee.core.preferences

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import vn.com.qqbee.App
import vn.com.qqbee.R
import vn.com.qqbee.core.utils.BaseActivity
import vn.com.qqbee.databinding.ActivitySettingsBinding

class SettingsActivity : BaseActivity() {

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    private lateinit var app: App
    lateinit var binding: ActivitySettingsBinding private set

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        app = application as App
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        title = getString(R.string.action_settings)
    }
}
