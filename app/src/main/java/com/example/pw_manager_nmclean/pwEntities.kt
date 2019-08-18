package com.example.pw_manager_nmclean

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

class pwEntities: LitePalSupport() {
    @Column(unique = true, defaultValue = "unknown")
    private val email:String=""
    private val sitepassword:String=""
    private val siteaddress:String=""
}