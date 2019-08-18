package com.example.pw_manager_nmclean

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

class Album: LitePalSupport() {
    @Column(unique = true, defaultValue = "unknown")
    private val name:String = ""
    private val price:Float = 0.toFloat()
    private val cover: ByteArray? = null
    private val songs = ArrayList<Song>()
    // generated getters and setters.
}