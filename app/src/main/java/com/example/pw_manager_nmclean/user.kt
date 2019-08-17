package com.example.pw_manager_nmclean

import java.net.URL

class user(val email: String, val pw: String, val passwords: ArrayList<passwordEntity>, val websites: ArrayList<String>){

    fun addpassword(login:String, pw:String, url: String){
        this.passwords.add(passwordEntity(login, pw, url))
        this.websites.add(url)
    }

}