package com.example.fireapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tv_login.setOnClickListener {
            onBackPressed()
        }

        addUserbtn.setOnClickListener {
            when{
                TextUtils.isEmpty(etEmailreg.text.toString().trim{it<= ' '})->{
                    Toast.makeText(this,"Please enter your email address",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(etregpassword.text.toString().trim { it <= ' ' })->{
                    Toast.makeText(this,"Please enter your password", Toast.LENGTH_SHORT).show()
                }else->{
                    val email:String= etEmailreg.text.toString().trim { it<=' ' }
                    val password:String= etregpassword.text.toString().trim { it<= ' ' }

                //storing the user in the firebase  database
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                            //if the registration is successfull
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                Toast.makeText(
                                    this,
                                    "You have registered successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(this, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("user_email", email)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }
}