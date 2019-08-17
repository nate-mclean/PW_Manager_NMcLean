package com.example.pw_manager_nmclean

import java.net.URL

class user(val email: String, val pw: String, val passwords: ArrayList<passwordEntity>){

    fun addpassword(user:user, login:String, pw:String, url: String){
        user.passwords.add(passwordEntity(login, pw, url))
    }
    fun onlywebsites(): ArrayList<String>{

        var a = ArrayList<String>()
        this.passwords.forEach(){
            a.add(it.website)
        }
        return a
    }
}