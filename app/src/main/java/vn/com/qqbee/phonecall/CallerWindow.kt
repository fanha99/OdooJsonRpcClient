
package vn.com.qqbee.phonecall

import android.annotation.SuppressLint
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Handler
import android.util.Base64
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView

import android.widget.TextView
import vn.com.qqbee.App
import vn.com.qqbee.GlideApp
import vn.com.qqbee.R
import vn.com.qqbee.customer.entities.Customer
import timber.log.Timber

class CallerWindow {

    private var windowManager:WindowManager? = null
    private var keyguardManager:KeyguardManager? = null
    private var callerView:View? = null

    private val windowParams:WindowManager.LayoutParams get() {
        val params = WindowManager.LayoutParams(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
        PixelFormat.TRANSLUCENT)
        params.gravity = Gravity.CENTER
        return params
    }

    @SuppressLint("InflateParams")
    private fun buildView(context:Context, customer: Customer): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_view_customer, null)
        view.setBackgroundColor(Color.parseColor("#0288D1"))
        view.alpha = 0.85.toFloat()
        view.findViewById<TextView>(R.id.name).text = customer.name
        view.findViewById<TextView>(R.id.company_name).text = customer.companyName
        view.findViewById<TextView>(R.id.phone).text = customer.phone

        val raw : ByteArray = Base64.decode(customer.imageSmall, Base64.DEFAULT)
        view.findViewById<ImageView>(R.id.imageSmall).setImageBitmap(BitmapFactory.decodeByteArray( raw, 0, raw.size))
        //val imageView: ImageView = view.findViewById<ImageView>(R.id.imageSmall)
        //Customer.loadImage(imageView, customer.imageSmall, customer.name)
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

    companion object {

        fun setText(parent_view:View, textview_id:Int, value:Any) {
            val textView = parent_view.findViewById<View>(textview_id) as TextView
            if (value is String || value is CharSequence)
                textView.text = value.toString()
            if (value is Int)
                textView.setText(Integer.parseInt(value.toString()))
        }
    }
}


