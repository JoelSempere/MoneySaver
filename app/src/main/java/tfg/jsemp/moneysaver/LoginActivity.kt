package tfg.jsemp.moneysaver

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        comprobarAutenticacion()
    }


    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result -> onSignInResult(result) }

    //***RESULTADO DEL INICIO DE SESIÓN***//
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            getErrorResult(response)
        }
        finish()
    }


    //***CONTROL DE ERRORES DE INICIO DE SESIÓN***//
    private fun getErrorResult(response: IdpResponse?) {
        val message : String =
            when {
                response == null -> {
                    getString(R.string.msg_need_auth)
                }
                response.error!!.errorCode == ErrorCodes.NO_NETWORK -> { //!! -> non null
                    getString(R.string.msg_missing_con)
                }
                else -> {
                    getString(R.string.msg_ukn_err)
                }
            }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    //***INICIO DE SESION INTENT***//
    private fun createSignInIntent() {
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
                //Pendiente añadir Auth con Google
            .setIsSmartLockEnabled(false) //para guardar contraseñas y usuario: true
            .build()
        signInLauncher.launch(signInIntent)
    }


    private fun comprobarAutenticacion() { //metodos autodescriptivos: no requieren de explicacion comentada
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            createSignInIntent()
        }
    }


}