package vn.com.qqbee.phonecall

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.gson.reflect.TypeToken
import java.util.Date
import timber.log.Timber
import io.reactivex.disposables.CompositeDisposable

import vn.com.qqbee.core.Odoo
import vn.com.qqbee.customer.entities.Customer
import vn.com.qqbee.gson

class CallReceiver : PhonecallReceiver() {
    private var compositeDisposable: CompositeDisposable? = null
    private val customerListType = object : TypeToken<ArrayList<Customer>>() {}.type

    override fun onIncomingCallReceived(ctx: Context, number: String, start: Date) {
        Timber.i("incomming....")
        val pnumber = number.trim().replace("\\s".toRegex() , "").replace("+", "")
        Timber.i( pnumber)
        checkphonenumber(ctx, pnumber)
    }

    override fun onIncomingCallAnswered(ctx: Context, number: String, start: Date) {
        Timber.i("incomming Answered....")
        val pnumber = number.trim().replace("\\s".toRegex() , "").replace("+", "")
        Timber.i( pnumber)
        checkphonenumber(ctx, pnumber)
    }

    override fun onOutgoingCallStarted(ctx: Context, number: String, start: Date) {
        Timber.i( "outgoing....")
        val pnumber = number.trim().replace("\\s".toRegex() , "").replace("+", "")
        Timber.i( pnumber)
        checkphonenumber(ctx, pnumber)
    }

    override fun onIncomingCallEnded(ctx: Context, number: String, start: Date, end: Date) {
        Timber.i( "incomming end....")
        val pnumber = number.trim().replace("\\s".toRegex() , "").replace("+", "")
        Timber.i( pnumber)
        if (callerWindow != null) {
            callerWindow!!.dismiss()
            callerWindow = null
        }
    }

    override fun onOutgoingCallEnded(ctx: Context, number: String, start: Date, end: Date) {
        Timber.i( "outgoing end....")
        val pnumber = number.trim().replace("\\s".toRegex() , "").replace("+", "")
        Timber.i( pnumber)
        if (callerWindow != null) {
            callerWindow!!.dismiss()
            callerWindow = null
        }
    }

    override fun onMissedCall(ctx: Context, number: String, start: Date) {
        Timber.i( "missing....")
        val pnumber = number.trim().replace("\\s".toRegex() , "").replace("+", "")
        Timber.i( pnumber)
        if (callerWindow != null) {
            callerWindow!!.dismiss()
            callerWindow = null
        }
    }

    fun checkphonenumber(ctx: Context, number: String) {
        Odoo.searchRead(
            model = "res.partner", fields = listOf(
                "id", "name", "email", "parent_name", "parent_id", "phone", "mobile", "image_small"
            ),
            domain = listOf("|" , listOf("phone", "ilike", number), listOf("mobile", "ilike", number)),
            offset = 0, limit = 1, sort = ""
        ) {
            onSubscribe { disposable ->
                compositeDisposable?.add(disposable)
            }

            onNext { response ->
                if (response.isSuccessful) {
                    val searchRead = response.body()!!
                    if (searchRead.isSuccessful) {
                        val items: ArrayList<Customer> = gson.fromJson(searchRead.result.records, customerListType)

                        if (callerWindow == null) callerWindow = CallerWindow()
                        callerWindow!!.show(ctx, items[0])
                        // ...
                    } else {
                        // Odoo specific error
                        Timber.w("search() failed with ${searchRead.errorMessage}")
                    }
                } else {
                    Timber.w("request failed with ${response.code()}:${response.message()}")
                }
            }

            onError { error ->
                error.printStackTrace()
            }

            onComplete { }
        }
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        internal var callerWindow: CallerWindow? = null
    }
}