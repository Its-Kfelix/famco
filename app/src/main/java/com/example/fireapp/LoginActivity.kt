package com.example.fireapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_signup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        logUserbtn.setOnClickListener {
            when {
                TextUtils.isEmpty(etEmailAd.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(etpassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
                }else->{
                val email:String= etEmailAd.text.toString().trim { it<=' ' }
                val password:String= etpassword.text.toString().trim { it<= ' ' }

                //Log in user
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                            //if the registration is successfull
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "You have logged in successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
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