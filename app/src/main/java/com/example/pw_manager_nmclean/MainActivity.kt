package com.example.pw_manager_nmclean

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import android.view.Gravity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import android.widget.ArrayAdapter
import java.net.URL


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get user info from login
        //sample info
        val array = ArrayList<passwordEntity>()
        val array2 = ArrayList<String>()
        val user = user("nate4495@gmail.com","12345678",array,array2)


        //listview auto populate with all user password entities
        val listview = findViewById<ListView>(R.id.passwordlist)
        val itemsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, user.websites)
        listview.adapter = itemsAdapter
        runOnUiThread { itemsAdapter.notifyDataSetChanged() }

        //click listview to bring up more info about password
        listview.setOnItemClickListener { parent, view, position, id ->
            val element = user.passwords.get(position) // The item that was clicked

            //bring up popup with info
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView2 = inflater.inflate(R.layout.view_pw_popup, null)

            // create the popup window
            val width = LinearLayout.LayoutParams.WRAP_CONTENT
            val height = LinearLayout.LayoutParams.WRAP_CONTENT
            val focusable = true // lets taps outside the popup also dismiss it
            val popupWindow = PopupWindow(popupView2, width, height, focusable)

            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window tolken
            popupWindow.showAtLocation(findViewById(R.id.floatingActionButton), Gravity.CENTER, 0, 0)


            //add password button
            val website = popupView2.findViewById<TextView>(R.id.website)
            val email = popupView2.findViewById<TextView>(R.id.email)
            val pw = popupView2.findViewById<TextView>(R.id.pw)

            website.setText(user.passwords.get(position).website)
            email.setText(user.passwords.get(position).username)
            pw.setText(user.passwords.get(position).pw)


        }


        //add new password with button
        val add = findViewById<FloatingActionButton>(R.id.floatingActionButton)


        add.setOnClickListener{
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.add_pw_popup, null)

            // create the popup window
            val width = LinearLayout.LayoutParams.WRAP_CONTENT
            val height = LinearLayout.LayoutParams.WRAP_CONTENT
            val focusable = true // lets taps outside the popup also dismiss it
            val popupWindow = PopupWindow(popupView, width, height, focusable)

            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window tolken
            popupWindow.showAtLocation(findViewById(R.id.floatingActionButton), Gravity.CENTER, 0, 0)


            //add password button
            val editwebsite = popupView.findViewById<EditText>(R.id.editwebsite)
            val editemail = popupView.findViewById<EditText>(R.id.editemail)
            val editpw = popupView.findViewById<EditText>(R.id.editpw)

            val addbutton = popupView.findViewById<Button>(R.id.addpassword)
            addbutton.setOnClickListener{
                user.addpassword(editemail.text.toString(),editpw.text.toString(),editwebsite.text.toString())
                popupWindow.dismiss()
                //itemsAdapter.notifyDataSetChanged()
            }
        }





    }
}
