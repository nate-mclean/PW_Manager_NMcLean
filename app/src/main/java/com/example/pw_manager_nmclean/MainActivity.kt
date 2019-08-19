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
import org.litepal.extension.deleteAll


class MainActivity : AppCompatActivity() {
    val websites = ArrayList<String>()
    var myEmail = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get user info from login
        myEmail = intent.getStringExtra("email")

        //init database
        LitePal.initialize(this)


        //get all websites for that user for the listview
        LitePal.findAll<Passwords>().forEach(){
            if(it.email == myEmail)
                websites.add(it.siteaddress)
        }



        //listview auto populate with all user websites that have passwords
        val listview = findViewById<ListView>(R.id.passwordlist)
        val itemsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, websites)
        listview.adapter = itemsAdapter

        //click listview to bring up more info about the website login information
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

            var websiteaddress = ""
            //get the rest of the info for that website
            LitePal.findAll<Passwords>().forEach(){
                if(it.siteaddress == websites.get(position)) {
                    website.setText("Website: " + it.siteaddress)
                    email.setText("Username: " + it.sitelogin)
                    pw.setText("Password: " + it.sitepassword)
                    websiteaddress=it.siteaddress
                }
            }

            //delete password button
            val deletebutton = popupView2.findViewById<Button>(R.id.deletebutton)
            deletebutton.setOnClickListener{
                LitePal.deleteAll<Passwords>("email = ? and siteaddress = ?", myEmail, websiteaddress)
                //update listview
                updateListView(itemsAdapter)
                //close popup
                popupWindow.dismiss()
            }


        }


        //add new password with button on click listener
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


            //add password button listener
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

                //update listview
                updateListView(itemsAdapter)


                popupWindow.dismiss()
                //itemsAdapter.notifyDataSetChanged()
            }
        }

    }

    fun updateListView (arr:ArrayAdapter<String>):  Boolean {
        //update listview
        websites.clear()
        LitePal.findAll<Passwords>().forEach(){
            if(it.email == myEmail)
                websites.add(it.siteaddress)
        }
        arr.notifyDataSetChanged()
        return true
    }
}
