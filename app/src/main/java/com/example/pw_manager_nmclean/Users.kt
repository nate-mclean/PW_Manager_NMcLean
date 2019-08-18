package com.example.pw_manager_nmclean

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

class Users: LitePalSupport() {
    @Column(unique = true, defaultValue = "unknown")
    var email:String=""
     var password:String=""
}