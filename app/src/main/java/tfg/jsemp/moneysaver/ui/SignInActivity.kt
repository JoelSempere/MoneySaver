package tfg.jsemp.moneysaver.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_in.*
import tfg.jsemp.moneysaver.R
import tfg.jsemp.moneysaver.model.User
import tfg.jsemp.moneysaver.utils.ConstantsUtil.ConstantsLogin.LOGIN_EMAIL
import tfg.jsemp.moneysaver.utils.FirestoreUtil

class SignInActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        iniciarApp()
    }


    //***CONTROL DE ERRORES REGISTRO***//
    private fun getErrorSignIn() {
        Toast.makeText(this, getString(R.string.msg_ukn_err), Toast.LENGTH_LONG).show()
    }


    //***INICIO DE SESION INTENT***//
    private fun createLoginIntent() {
        val signInIntent = Intent(this, MainActivity::class.java)
            .apply {
                putExtra(LOGIN_EMAIL,etEmail.text)
            }
        startActivity(signInIntent)
    }


    //***COMPRUEBA CONTENIDO EN LOS EDIT TEXT***//
    private fun comprobarCamposLogin(): Boolean {
        return (
                etEmail.text.isNotEmpty() && etPassword.text.isNotEmpty() && etPassword2.text.isNotEmpty() &&
                        (
                                etPassword.text.toString() == etPassword2.text.toString()
                                )
                )
    }


    //***ACCION DE REGISTRO PARA INCIAR APP***//
    private fun iniciarApp() {
        btnSignIn.setOnClickListener {
            println("@Joel: Comprobar Boolean - 17/04/22 - " + comprobarCamposLogin())
            if (comprobarCamposLogin()) {
                firebaseAuth = FirebaseAuth.getInstance()
                firebaseAuth
                    .createUserWithEmailAndPassword(
                        etEmail.text.toString(),
                        etPassword.text.toString()
                    )
                    .addOnCompleteListener {
                        if (!it.isSuccessful) {
                            getErrorSignIn()
                        } else {
                            var currentUser = FirestoreUtil.getUserinfo(firebaseAuth)
                            FirestoreUtil.setUserInCollection(currentUser)
                            createLoginIntent()
                        }
                    }
            }
        }
    }


}