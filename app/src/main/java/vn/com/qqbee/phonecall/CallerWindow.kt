
package vn.com.qqbee.phonecall

import android.annotation.SuppressLint
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.util.Base64
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView

import android.widget.TextView
import vn.com.qqbee.customer.entities.Customer
import timber.log.Timber
import vn.com.qqbee.*

class CallerWindow {

    private var windowManager:WindowManager? = null
    private var keyguardManager:KeyguardManager? = null
    private var callerView:View? = null

    private val windowParams:WindowManager.LayoutParams get() {
        val params = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            PixelFormat.TRANSLUCENT)
        } else {
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT)
        }
        params.gravity = Gravity.CENTER
        return params
    }

    @SuppressLint("InflateParams", "TimberArgCount")
    private fun buildView(context:Context, customer: Customer): View {
        val view = LayoutInflater.from(context).inflate(R.layout.caller_window, null)

        view.setBackgroundColor(Color.parseColor("#0288D1"))
        view.alpha = 0.85.toFloat()

        view.findViewById<TextView>(R.id.name).text = customer.name
        view.findViewById<TextView>(R.id.company_name).text = customer.companyName
        view.findViewById<TextView>(R.id.phone).text = customer.phone
        val imageView: ImageView = view.findViewById<ImageView>(R.id.imageSmall)
        imageView.tag = null
        Customer.loadImage(imageView, customer.imageSmall, customer.name)
        return view
    }

     fun show(context:Context, customer: Customer) {
        windowManager = context.getSystemService(Activity.WINDOW_SERVICE) as WindowManager
        keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        Timber.i("Showing caller window")
        Handler().postDelayed({
            if (callerView == null) callerView = buildView(context, customer)
            val params = windowParams
            windowManager!!.addView(callerView, params)
            // Fanha: click to dimiss, sometime it is not dimiss while end call
            callerView!!.setOnClickListener {
                // TODO Auto-generated method stub
                Timber.i("click")
                dismiss()
            }
        }, 200)
    }

    fun dismiss() {
        try {
            if (callerView != null)
            windowManager!!.removeViewImmediate(callerView)
            callerView = null
        }
        catch (e:Exception) {
            e.printStackTrace()
        }

    }
}


