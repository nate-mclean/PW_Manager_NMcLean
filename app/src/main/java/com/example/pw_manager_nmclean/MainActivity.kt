package com.example.pw_manager_nmclean

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.view.Gravity
import android.content.Context
import android.view.LayoutInflater
import android.widget.*
import android.widget.ArrayAdapter
import org.litepal.LitePal
import org.litepal.extension.findAll




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get user info from login
        val myEmail = intent.getStringExtra("email")
        val myPassword = intent.getStringExtra("password")

        //init database
        LitePal.initialize(this)


        //get all websites for that user

        val websites = ArrayList<String>()
        LitePal.findAll<Passwords>().forEach(){
            if(it.email == myEmail)
                websites.add(it.siteaddress)
        }




        //listview auto populate with all user password entities
        val listview = findViewById<ListView>(R.id.passwordlist)
        val itemsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, websites)
        listview.adapter = itemsAdapter

        //click listview to bring up more info about password
        listview.setOnItemClickListener { _, _, position, _ ->
           // val element = user.passwords.get(position) // The item that was clicked

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


            val website = popupView2.findViewById<TextView>(R.id.website)
            val email = popupView2.findViewById<TextView>(R.id.email)
            val pw = popupView2.findViewById<TextView>(R.id.pw)

            LitePal.findAll<Passwords>().forEach(){
                if(it.siteaddress == websites.get(position)) {
                    website.setText("Website: " + it.siteaddress)
                    email.setText("Username: " + it.sitelogin)
                    pw.setText("Password: " + it.sitepassword)
                }
            }
            /*
            val conditions = "siteaddress = " + myEmail
            val websitesTable = LitePal.where(conditions).find<Passwords>()

            website.setText("Website: " + user.passwords.get(position).website)
            email.setText("Username: " + user.passwords.get(position).username)
            pw.setText("Password: " + user.passwords.get(position).pw)
            */


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

                //SAVE new entry to database
                val newpw = Passwords()
                newpw.email = myEmail
                newpw.siteaddress = editwebsite.text.toString()
                newpw.sitepassword = editpw.text.toString()
                newpw.sitelogin = editemail.text.toString()
                newpw.save()

                websites.clear()

                //update listview
                LitePal.findAll<Passwords>().forEach(){
                    if(it.email == myEmail)
                        websites.add(it.siteaddress)
                }
                itemsAdapter.notifyDataSetChanged()


                popupWindow.dismiss()
                //itemsAdapter.notifyDataSetChanged()
            }
        }





    }
}
