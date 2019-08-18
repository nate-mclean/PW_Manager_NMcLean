package com.example.pw_manager_nmclean

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import org.litepal.LitePal
import android.database.sqlite.SQLiteDatabase
import org.litepal.extension.findAll


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        //instantiate litepal
        //val userManager = userManager()
        //val db = LitePal.getDatabase()
        LitePal.initialize(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //edit texts
        val editemail = findViewById<EditText>(R.id.enteremail)
        val editpw = findViewById<EditText>(R.id.enterpassword)



        //login button
        val loginbutton = findViewById<Button>(R.id.loginbutton)
        loginbutton.setOnClickListener {
            var myEmail = editemail.text.toString()
            var myPW = editpw.text.toString()
            //check if login is valid

            if (this.login(myEmail, myPW)) {
                //start main activity if login is valid
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("keyIdentifier", 1)
                startActivity(intent)
                Toast.makeText(getApplicationContext(), "Login Successful.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Invalid Login.", Toast.LENGTH_SHORT).show();
            }
        }


        //register button
        val registerbutton = findViewById<Button>(R.id.registerbutton)
        registerbutton.setOnClickListener {
            var myEmail = editemail.text.toString()
            var myPW = editpw.text.toString()
            var validEmail = false;
            var validPw = false;


            //check email is valid
            if (myEmail.validEmail())
                validEmail = true;
            else
                Toast.makeText(getApplicationContext(), "Email invalid", Toast.LENGTH_SHORT).show();

            //check if password is valid
            myPW.validator()
                .nonEmpty()
                .minLength(8)
                .atleastOneUpperCase()
                .atleastOneSpecialCharacters()
                .atleastOneNumber()
                .addErrorCallback {
                    // Invalid password
                    Toast.makeText(
                        getApplicationContext(), "Password must be at least 8 chars long," +
                                " contain at least 1 uppercase, lowercase, number, and symbol.", Toast.LENGTH_SHORT
                    ).show();
                    editpw.setText("")
                }
                .addSuccessCallback {
                    //success
                    validPw = true;
                }
                .check()

            //if both yes (count == 2) then save to memory
            //create account, clear fields, toast success
            if(validEmail && validPw){

                //call controller to create account, return bool
                if(this.createAccount(myEmail,myPW))
                {
                    //if true
                    //toast success
                    editemail.setText("")
                    editpw.setText("")
                    Toast.makeText(getApplicationContext(), "Account created successfully.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //if false
                    //account already exists
                    Toast.makeText(getApplicationContext(), "Account already exists.", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

    //see if user credentials exist
    fun login(email:String, pw:String) : Boolean {

        LitePal.findAll<Users>().forEach(){
            if(it.email == email && it.password == pw)
                return true
        }
        return false
    }
    //create a new account
    fun createAccount(email:String, pw:String) : Boolean {

        //ee if user already exisits
        LitePal.findAll<Users>().forEach(){
            if(it.email.equals(email))
                return false
        }
        //if not then create new and add to database
        val newuser = Users()
        newuser.email = email
        newuser.password = pw
        newuser.save()
        return true

    }
}