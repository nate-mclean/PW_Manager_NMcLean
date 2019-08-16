package com.example.pw_manager_nmclean

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        //instantiate controller
        val userManager = userManager()

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

            if (userManager.login(myEmail, myPW)) {
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
                if(userManager.createAccount(myEmail,myPW))
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
}