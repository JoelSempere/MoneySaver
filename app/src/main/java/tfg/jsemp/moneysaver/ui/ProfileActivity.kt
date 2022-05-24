package tfg.jsemp.moneysaver.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
        onChangeActivity()

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
                    FirestoreUtil.updateUserName(currentUser)
                    setUserInfo()
                    etNewUsernameC.visibility = View.GONE
                    btnSaveChanges.visibility = View.INVISIBLE

                }
            }
    }


    private fun onChangeActivity() {
        btnMain.setOnClickListener {
         intent = Intent(applicationContext, MainActivity::class.java)
         startActivity(intent)
         finish()
        }
        btnWallet.setOnClickListener{
            intent = Intent(applicationContext, WalletActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}