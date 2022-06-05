package tfg.jsemp.moneysaver.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*
import tfg.jsemp.moneysaver.R
import tfg.jsemp.moneysaver.utils.ConstantsUtil.ConstantsLogin.LOGIN_EMAIL
import tfg.jsemp.moneysaver.utils.FirestoreUtil

class SignInActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        firebaseAuth = FirebaseAuth.getInstance()
        iniciarApp()
    }


    /**CONTROL DE ERRORES REGISTRO**/
    private fun getErrorSignIn() {
        Toast.makeText(this, getString(R.string.msg_ukn_err), Toast.LENGTH_LONG).show()
    }


    /**INICIO DE SESION INTENT**/
    private fun createLoginIntent() {
        val signInIntent = Intent(this, MainActivity::class.java)
            .apply {
                putExtra(LOGIN_EMAIL,etEmail.text)
            }
        startActivity(signInIntent)
    }


    /**COMPRUEBA CONTENIDO EN LOS EDIT TEXT**/
    private fun checkLoginFields(): Boolean {
        return (
                etEmail.text.isNotEmpty() && etPassword.text.isNotEmpty() && etPassword2.text.isNotEmpty()
                        && (etPassword.text.toString() == etPassword2.text.toString())
                        && Patterns.EMAIL_ADDRESS.matcher(etEmail.text).matches()
                )
    }


    //***ACCION DE REGISTRO PARA INCIAR APP***//
    private fun iniciarApp() {
        btnSignIn.setOnClickListener {
            if (checkLoginFields()) {
                firebaseAuth.createUserWithEmailAndPassword(
                        etEmail.text.toString(),
                        etPassword.text.toString()
                    )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            FirestoreUtil.getUserinfo(firebaseAuth).observe( this) {
                                createLoginIntent()
                            }
                        } else {
                            Toast.makeText(this, it.result.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener{
                        Toast.makeText(this, it.cause.toString(), Toast.LENGTH_LONG).show()
                    }
                    .runCatching { getErrorSignIn() }
            }
            else {
                getErrorSignIn()
            }
        }
    }


}