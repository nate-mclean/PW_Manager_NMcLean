package com.example.pw_manager_nmclean

class userlist {
    var userdatabase: ArrayList<user> = ArrayList()

    fun getArray(): List<user> {
        return this.userdatabase
    }

    fun addUser(user: user) {
        this.userdatabase.add(user)
    }
}