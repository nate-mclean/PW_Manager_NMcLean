package com.example.pw_manager_nmclean

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

class Song: LitePalSupport() {
    @Column(nullable = false)
    private val name:String = ""
    private val duration:Int = 0
    @Column(ignore = true)
    private val uselessField:String = ""
    private val album: Album? = null
}// generated getters and setters.