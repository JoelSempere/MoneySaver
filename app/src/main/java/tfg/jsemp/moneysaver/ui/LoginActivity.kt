package tfg.jsemp.moneysaver.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import tfg.jsemp.moneysaver.R
import tfg.jsemp.moneysaver.utils.ConstantsUtil.ConstantsLogin.GOOGLE_SIGN_IN
import tfg.jsemp.moneysaver.utils.ConstantsUtil.ConstantsLogin.LOGIN_EMAIL
import java.lang.Exception


class LoginActivity : AppCompatActivity() {
    lateinit var googleSignInClient : GoogleSignInClient
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth = FirebaseAuth.getInstance()
        val gImage = getDrawable(R.mipmap.google) //Unica manera de insertar la imagen en un button basico
        btnGoogle.setCompoundDrawablesWithIntrinsicBounds(gImage,null,null,null)
        iniciarApp()
    }


    //***CONTROL DE ERRORES REGISTRO***//
    private fun getErrorSignIn() {
        Toast.makeText(this, getString(R.string.msg_ukn_err), Toast.LENGTH_LONG).show()
    }


    //***INICIO DE SESION INTENT***//
    private fun createLoginIntent(email: String) {
        val signInIntent = Intent(this, MainActivity::class.java)
            .apply {
                putExtra(LOGIN_EMAIL,email)
            }
        startActivity(signInIntent)
        finish()
    }


    private fun createSingInIntent() {
        val loginIntent = Intent(this, SignInActivity::class.java)
        startActivity(loginIntent)
    }


    //***COMPRUEBA CONTENIDO EN LOS EDIT TEXT***//
    private fun checkFieldsLogin(): Boolean {
     return (etEmail.text.isNotEmpty() && etPassword.text.isNotEmpty())
    }


    //***GOOGLE OPTIONS***//
    private fun googleSignInRequest(): GoogleSignInOptions {
        return GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("74949683138-kmh37unjkp75vfkgio3g7j13nl8v7lph.apps.googleusercontent.com")
            .requestEmail()
            .build()
    }

    //***SIGN IN CON GOOGLE EN FIREBASE***//
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener{
            if(it.isSuccessful) {
                createLoginIntent(
                    firebaseAuth.currentUser!!.email.toString())
                println( "EMAIL:" + firebaseAuth.currentUser!!.email.toString())
                finish()
            }
            else {
                //TODO
                println("Error en firebaseAuth")
            }
        }

    }


    //***ACCIONES DE LOS CLICKS PARA INICIAR APP***//
    private fun iniciarApp() {
        btnSignIn.setOnClickListener {
            createSingInIntent()
        }
        btnLogin.setOnClickListener {
            if (checkFieldsLogin()) {
                firebaseAuth = FirebaseAuth.getInstance()
                firebaseAuth
                    .signInWithEmailAndPassword(
                        etEmail.text.toString(),
                        etPassword.text.toString()
                    )
                    .addOnCompleteListener{
                        if (!it.isSuccessful){
                            getErrorSignIn()
                        }else{
                            createLoginIntent(etEmail.text.toString())
                        }
                    }
            }
        }
        btnGoogle.setOnClickListener{
            googleSignInClient = GoogleSignIn.getClient(this, googleSignInRequest())
            intent = googleSignInClient.signInIntent
            startActivityForResult(intent, GOOGLE_SIGN_IN)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            GOOGLE_SIGN_IN -> {
                val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
                try{ //Google Sign In OK
                    if(accountTask.result != null){
                        firebaseAuthWithGoogle(accountTask.getResult(ApiException::class.java))
                    }


                }catch (ex: Exception){
                    ex.cause
                }
            }
        }
    }


}