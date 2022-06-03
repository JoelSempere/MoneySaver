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
        onLogout()

    }


    private fun onLogout() {
        ibLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
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
            tietNewUsernameC.visibility = View.VISIBLE
            tilNewUsernameC.visibility = View.VISIBLE
            tietNewUsernameC.setText(tvUsernameC.text)
            btnSaveChanges.visibility = View.VISIBLE
        }
    }


    private fun onSaveAction() {
            btnSaveChanges.setOnClickListener{
                if (currentUser.name != tietNewUsernameC.text.toString()){
                    currentUser.name = tietNewUsernameC.text.toString()
                    FirestoreUtil.updateUserName(currentUser)
                    setUserInfo()
                    tietNewUsernameC.visibility = View.GONE
                    tilNewUsernameC.visibility = View.GONE
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