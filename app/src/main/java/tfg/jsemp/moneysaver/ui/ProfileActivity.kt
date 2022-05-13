package tfg.jsemp.moneysaver.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*
import tfg.jsemp.moneysaver.R
import tfg.jsemp.moneysaver.model.User
import tfg.jsemp.moneysaver.utils.ConstantsUtil
import tfg.jsemp.moneysaver.utils.FirestoreUtil

class ProfileActivity : AppCompatActivity() {
    lateinit var currentUser : User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setUserInfo()
        onEditAction()
        onSaveAction()

    }


    private fun setUserInfo() {
        currentUser = (intent.getSerializableExtra(ConstantsUtil.ConstantsLogin.CURRENT_USER) as? User)!!
        if(currentUser != null){
            tvEmailC.text = currentUser.email
            tvUsernameC.text = currentUser.name
        }
    }


    private fun onEditAction() {
        btnEdit.setOnClickListener {
            etNewUsernameC.visibility = View.VISIBLE
            btnSaveChanges.visibility = View.VISIBLE
           //TODO "Create button to change user profile image"
        }
    }


    private fun onSaveAction() {
            btnSaveChanges.setOnClickListener{
                //TODO "Create button to change user profile image""update record"
                if (currentUser.name != etNewUsernameC.text.toString()){
                    currentUser.name = etNewUsernameC.text.toString()
                    FirestoreUtil.updateUser(currentUser)
                    setUserInfo()
                    etNewUsernameC.visibility = View.GONE
                    btnSaveChanges.visibility = View.INVISIBLE

                }
            }
    }
}