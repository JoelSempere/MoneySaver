package tfg.jsemp.moneysaver

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import tfg.jsemp.moneysaver.utils.ConstantsUtil.constantesLogin.LOGIN_EMAIL


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
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


    private fun createSingInIntent() {
        val loginIntent = Intent(this, SignInActivity::class.java)
        startActivity(loginIntent)
    }


    //***COMPRUEBA CONTENIDO EN LOS EDIT TEXT***//
    private fun comprobarCamposLogin(): Boolean {
     return (etEmail.text.isNotEmpty() && etPassword.text.isNotEmpty())
    }


    //***ACCIONES DE LOS CLICKS PARA INICIAR APP***//
    private fun iniciarApp() {
        btnSignIn.setOnClickListener {
            createSingInIntent()
        }
        btnLogin.setOnClickListener {
            if (comprobarCamposLogin()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        etEmail.text.toString(),
                        etPassword.text.toString()
                    )
                    .addOnCompleteListener{
                        if (!it.isSuccessful){
                            getErrorSignIn()
                        }else{
                            createLoginIntent()
                        }
                    }
            }
        }
    }


}