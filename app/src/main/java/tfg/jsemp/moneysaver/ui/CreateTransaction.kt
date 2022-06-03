package tfg.jsemp.moneysaver.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_create_transaction.*
import tfg.jsemp.moneysaver.R
import tfg.jsemp.moneysaver.model.Account
import tfg.jsemp.moneysaver.model.Category
import tfg.jsemp.moneysaver.model.Transaction
import tfg.jsemp.moneysaver.utils.FirestoreUtil

class CreateTransaction : AppCompatActivity() {
    private  var transaction = Transaction()
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var accountsAdapter : ArrayAdapter<CharSequence>
    private lateinit var categoriesAdapter : ArrayAdapter<CharSequence>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_transaction)
        firebaseAuth = FirebaseAuth.getInstance()
        initializeSpiners()
        onSaveTransaction(transaction)
        setSupportActionBar(mtToolbar)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_transaction, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_close -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun initializeSpiners() {
       accountsAdapter = ArrayAdapter.createFromResource(this, R.array.accounts,
       android.R.layout.simple_spinner_dropdown_item)
        categoriesAdapter = ArrayAdapter.createFromResource(this, R.array.categories,
            android.R.layout.simple_spinner_dropdown_item)
        spWallet.adapter = accountsAdapter
        spCategory.adapter = categoriesAdapter
    }


    /**Comprueba que se puede guardar la transacción y recupera los datos necesarios de firestore para ello**/
    private fun onSaveTransaction(transaction: Transaction){
        var canSaveRecord = true
        fabSaveTransaction.setOnClickListener {
            if(checkFields()) {
                transaction.userId = firebaseAuth.currentUser!!.uid
                transaction.quantity =  tietNewUsernameC.text.toString().toFloat()
                transaction.isInCome = swIsInCome.isChecked
                FirestoreUtil.getIdByName(spCategory.selectedItem.toString(), "Categories").observe(this){ ctgId ->
                    transaction.categoryId = ctgId.toString()
                    FirestoreUtil.getAccountIdByName(spWallet.selectedItem.toString(), "Accounts").observe(this){ accId ->
                        transaction.accountId = accId.toString()
                        if(canSaveRecord) {
                            setNewTransaction(transaction)
                            canSaveRecord = false
                        }
                    }
                }
            }
        }
    }

    /**Guarda la transacción**/
    private fun setNewTransaction(transaction: Transaction) {
        FirestoreUtil.setTransactionsIntoCategories(
            transaction.categoryId,
            transaction
        )
        var qtty : Float = if (transaction.isInCome) {
            transaction.quantity
        } else {
            -(transaction.quantity)
        }
        FirestoreUtil.setNewAccountValue(transaction.accountId, qtty)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    /**Devuelve aviso en caso de no poder guardarse la transacción**/
    private fun checkFields() : Boolean {
        var isSaveable = true
        if(tfCantidad.editText!!.text.isEmpty())  {
            Toast.makeText(this, getString(R.string.msg_error_save_transaction), Toast.LENGTH_SHORT).show()
            isSaveable = false
        }
        return isSaveable
    }

}