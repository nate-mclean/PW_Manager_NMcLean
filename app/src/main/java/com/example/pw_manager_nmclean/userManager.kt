package com.example.pw_manager_nmclean

import java.net.URL
import org.litepal.LitePal



class userManager {
    val userlist = userlist()

    //populate userlist with saved data on app startup


    //create a new account
    fun createAccount(email:String, pw:String) : Boolean {

        userlist.getArray().forEach(){
            if(it.email.equals(email))
                return false
        }
            userlist.addUser(user(email, pw, ArrayList<passwordEntity>(), ArrayList<String>()))
            return true
    }

    //see if user credentials exist
    fun login(email:String, pw:String) : Boolean {

        LitePal.findAll(Users::class.java).forEach(){
            if(it.email == email && it.password == pw)
                return true
        }
        return false
    }

}