package vn.com.qqbee

import android.content.Context
import android.content.res.Configuration
import androidx.multidex.MultiDexApplication
import vn.com.qqbee.core.Odoo
import vn.com.qqbee.core.OdooDatabase
import vn.com.qqbee.core.utils.CookiePrefs
import vn.com.qqbee.core.utils.LetterTileProvider
import vn.com.qqbee.core.utils.LocaleHelper
import vn.com.qqbee.core.utils.Retrofit2Helper
import timber.log.Timber

class App : MultiDexApplication() {

    companion object {
        const val KEY_ACCOUNT_TYPE = "${BuildConfig.APPLICATION_ID}.auth"
    }

    private val letterTileProvider: LetterTileProvider by lazy {
        LetterTileProvider(this)
    }

    val cookiePrefs: CookiePrefs by lazy {
        CookiePrefs(this)
    }

    override fun attachBaseContext(base: Context?) {
        if (base != null) {
            super.attachBaseContext(LocaleHelper.setLocale(base))
        } else {
            super.attachBaseContext(base)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        LocaleHelper.setLocale(this)
    }

    override fun onCreate() {
        super.onCreate()
        Retrofit2Helper.app = this
        Odoo.app = this
        OdooDatabase.app = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    fun getLetterTile(displayName: String): ByteArray =
        letterTileProvider.getLetterTile(displayName)
}