package com.example.pw_manager_nmclean

class Controller {
    val userlist = userlist()

    //populate userlist with saved data on app startup

    fun createAccount(email:String,pw:String) : Boolean {

        userlist.getArray().forEach(){
            if(it.email.equals(email))
                return false
        }
            userlist.addUser(user(email, pw))
            return true

    }

}