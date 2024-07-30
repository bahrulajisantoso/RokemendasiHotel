package com.example.aplikasiskripsi.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikasiskripsi.UserPreference
import com.example.aplikasiskripsi.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var pref: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        pref = UserPreference(this)

        binding.tvLog2.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }

        setupAction()
    }

    private fun setupAction() {
        binding.btnReg.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPass.text.toString()
            when {
                email.isEmpty() -> {
                    binding.etEmail.error = "Email harus diisi"
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.etEmail.error = "Email tidak valid"
                }
                password.isEmpty() -> {
                    binding.etPass.error = "Password harus diisi"
                }
                password.length < 8 -> {
                    binding.etPass.error = "Password min 8 karakter"
                }
                else -> {
                    register(email, password)
                }
            }
        }
    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                binding.progressBar.visibility = View.VISIBLE
                if (it.isSuccessful) {
                    Toast.makeText(applicationContext, "Register sukses", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    finish()
                    binding.progressBar.visibility = View.GONE
                } else {
                    try {
                        throw it.exception!!
                    } catch (e: FirebaseAuthUserCollisionException) {
                        binding.etEmail.error = "Email sudah digunakan"
                    }
                    binding.progressBar.visibility = View.GONE
                }
            }
    }
}