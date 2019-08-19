package com.example.pw_manager_nmclean

import org.litepal.crud.LitePalSupport

//this class stores all user websites and associated login and password
class Passwords: LitePalSupport() {

    var email:String=""
    var sitelogin:String=""
    var sitepassword:String=""
    var siteaddress:String=""

}