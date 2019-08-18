package com.example.pw_manager_nmclean

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

class PWEntities: LitePalSupport() {

    var email:String=""
    var sitelogin:String=""
    var sitepassword:String=""
    var siteaddress:String=""

}