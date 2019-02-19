package vn.com.qqbee.core.utils

import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import vn.com.qqbee.App
import vn.com.qqbee.GlideRequests
import vn.com.qqbee.R
import vn.com.qqbee.core.OdooUser
import vn.com.qqbee.trimFalse

class NavHeaderViewHolder(
    view: View
) {
    private val pic: ImageView = view.findViewById(R.id.userImage)
    private val name: TextView = view.findViewById(R.id.header_name)
    private val email: TextView = view.findViewById(R.id.header_details)
    private val menuToggle: ConstraintLayout = view.findViewById(R.id.menuToggle)
    private val menuToggleImage: ImageView = view.findViewById(R.id.ivDropdown)


    fun setUser(user: OdooUser, glideRequests: GlideRequests) {
        name.text = user.name
        email.text = user.login
        if (user.imageSmall.trimFalse().isNotEmpty()) {
            val byteArray = Base64.decode(user.imageSmall, Base64.DEFAULT)
            glideRequests
                .asBitmap()
                .load(byteArray)
                .dontAnimate()
                .circleCrop()
                .into(pic)
        } else {
            glideRequests
                .asBitmap()
                .load((pic.context.applicationContext as App).getLetterTile(user.name))
                .dontAnimate()
                .circleCrop()
                .into(pic)
        }
    }
}
