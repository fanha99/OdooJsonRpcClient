package vn.com.qqbee.phonecall

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat
import timber.log.Timber
import java.util.*

abstract class PhonecallReceiver : BroadcastReceiver() {

    companion object {
        //The receiver will be recreated whenever android feels like it.  We need a static variable to remember data between instantiations
        private var lastState = TelephonyManager.CALL_STATE_IDLE
        private var callStartTime: Date = Date()
        private var isIncoming: Boolean = false
        private var savedNumber: String = ""  //because the passed incoming is only valid in ringing
    }

    override fun onReceive(context: Context, intent: Intent) {

        Timber.d("PhonecallReceiver:onReceive")

        //We listen to two intents.  The new outgoing call only tells us of an outgoing call.  We use it to get the number.
        if (intent.action == "android.intent.action.NEW_OUTGOING_CALL") {
            savedNumber = intent.extras?.getString("android.intent.extra.PHONE_NUMBER") ?: ""
        } else {
            intent.extras?.let {
                val stateStr = it.getString(TelephonyManager.EXTRA_STATE, "")
                val number = it.getString(TelephonyManager.EXTRA_INCOMING_NUMBER, "")
                var state = 0

                when (stateStr) {
                    TelephonyManager.EXTRA_STATE_IDLE -> state = TelephonyManager.CALL_STATE_IDLE
                    TelephonyManager.EXTRA_STATE_OFFHOOK -> state = TelephonyManager.CALL_STATE_OFFHOOK
                    TelephonyManager.EXTRA_STATE_RINGING -> state = TelephonyManager.CALL_STATE_RINGING
                }

                onCallStateChanged(context, state, number)
            }
        }
    }

    //Deals with actual events

    //Incoming call-  goes from IDLE to RINGING when it rings, to OFFHOOK when it's answered, to IDLE when its hung up
    //Outgoing call-  goes from IDLE to OFFHOOK when it dials out, to IDLE when hung up
    private fun onCallStateChanged(context: Context, state: Int, number: String) {
        if (lastState == state) {
            //No change, debounce extras
            return
        }

        when (state) {
            TelephonyManager.CALL_STATE_RINGING -> {
                isIncoming = true
                callStartTime = Date()
                savedNumber = number
                onIncomingCallReceived(context, number, callStartTime)
            }
            TelephonyManager.CALL_STATE_OFFHOOK ->
                //Transition of ringing -> offhook are pickups of incoming calls.  Nothing done on them
                if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                    isIncoming = false
                    callStartTime = Date()
                    onOutgoingCallStarted(context, number, callStartTime)
                } else {
                    isIncoming = true
                    callStartTime = Date()
                    onIncomingCallAnswered(context, savedNumber, callStartTime)
                }
            TelephonyManager.CALL_STATE_IDLE ->
                //Went to idle -  this is the end of a call.  What type depends on previous state(s)
                if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                    //Ring but no pickup-  a miss
                    onMissedCall(context, savedNumber, callStartTime)
                } else if (isIncoming) {
                    onIncomingCallEnded(context, savedNumber, callStartTime, Date())
                } else {
                    onOutgoingCallEnded(context, savedNumber, callStartTime, Date())
                }
        }
        lastState = state
    }

    //    val readOutGoingCall = ContextCompat.checkSelfPermission(context, Manifest.permission.PROCESS_OUTGOING_CALLS) == PackageManager.PERMISSION_GRANTED
    private fun checkReadPhoneStatePermission(context: Context) = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED

    protected abstract fun onIncomingCallReceived(ctx: Context, number: String, start: Date)

    protected abstract fun onIncomingCallAnswered(ctx: Context, number: String, start: Date)

    protected abstract fun onIncomingCallEnded(ctx: Context, number: String, start: Date, end: Date)

    protected abstract fun onOutgoingCallStarted(ctx: Context, number: String, start: Date)

    protected abstract fun onOutgoingCallEnded(ctx: Context, number: String, start: Date, end: Date)

    protected abstract fun onMissedCall(ctx: Context, number: String, start: Date)

}