package tfg.jsemp.moneysaver.utils

class ConstantsUtil {

    object ConstantsLogin {
        @kotlin.jvm.JvmField
        var LOGIN_EMAIL = "tfg.jsemp.moneysaver.utils.LOGIN_EMAIL"
        @kotlin.jvm.JvmField
        var CURRENT_USER = "tfg.jsemp.moneysaver.utils.CURRENT_USER"
        const val GOOGLE_SIGN_IN = 100

    }


    object ConstantsFirestore {
        @kotlin.jvm.JvmField
        var USERS = "Users"
        @kotlin.jvm.JvmField
        var ACCOUNTS = "Accounts"
        @kotlin.jvm.JvmField
        var ECONOMY = "Economy"
        @kotlin.jvm.JvmField
        var CATEGORIES = "Categories"
        @kotlin.jvm.JvmField
        var TRANSACTIONS = "Transactions"


        @kotlin.jvm.JvmField
        var NAME = "name"
        @kotlin.jvm.JvmField
        var USER_ID = "userId"
        @kotlin.jvm.JvmField
        var TOTAL = "total"

        @kotlin.jvm.JvmField
        var WALLET_NAME_1 = "Principal"
        @kotlin.jvm.JvmField
        var WALLET_NAME_2 = "Secundaria"
        @kotlin.jvm.JvmField
        var MIN_VALUE = 0
    }

    object ConstantsSimbols {
        @kotlin.jvm.JvmField
        var EURO = " €"
    }
}