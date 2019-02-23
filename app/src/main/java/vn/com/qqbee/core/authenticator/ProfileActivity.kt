package vn.com.qqbee.core.authenticator

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import vn.com.qqbee.App
import vn.com.qqbee.R
import vn.com.qqbee.core.utils.BaseActivity
import vn.com.qqbee.databinding.ActivityProfileBinding
import vn.com.qqbee.getActiveOdooUser

class ProfileActivity : BaseActivity() {

    private lateinit var app: App
    private lateinit var binding: ActivityProfileBinding

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        app = application as App
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val user = getActiveOdooUser()
        if (user != null) {
            binding.user = user
        }
    }
}
