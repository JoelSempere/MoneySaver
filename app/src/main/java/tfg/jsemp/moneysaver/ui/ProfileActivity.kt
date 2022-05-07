package tfg.jsemp.moneysaver.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*
import tfg.jsemp.moneysaver.R
import tfg.jsemp.moneysaver.model.User
import tfg.jsemp.moneysaver.utils.ConstantsUtil

class ProfileActivity : AppCompatActivity() {
    lateinit var currentUser : User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setUserInfo()

    }


    private fun setUserInfo() {
        currentUser = (intent.getSerializableExtra(ConstantsUtil.ConstantsLogin.CURRENT_USER) as? User)!!
        if(currentUser != null){
            tvEmailC.text = currentUser.email
            tvUsernameC.text = currentUser.name
        }
    }
}