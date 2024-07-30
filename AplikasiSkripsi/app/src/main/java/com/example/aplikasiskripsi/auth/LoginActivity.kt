package com.example.aplikasiskripsi.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikasiskripsi.MainActivity
import com.example.aplikasiskripsi.UserPreference
import com.example.aplikasiskripsi.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var pref: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        pref = UserPreference(this)

        binding.tvReg2.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        setupAction()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
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
                    login(email, password)
                }
            }
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                binding.progressBar.visibility = View.VISIBLE
                if (it.isSuccessful) {
                    auth.currentUser?.getIdToken(true)?.addOnCompleteListener { task ->
                        if (it.isSuccessful) {
                            val idToken: String = task.result?.token.toString()
                            val userId = auth.currentUser?.uid.toString()
                            val emailPref = auth.currentUser?.email.toString()

                            val pref = UserPreference(this@LoginActivity)
                            pref.setUser(userId, idToken, emailPref)

                            Toast.makeText(this, "Login sukses", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            finish()
                            binding.progressBar.visibility = View.GONE
                        } else {
                            Log.d("Error", task.exception.toString())
                        }
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Login gagal, pastikan email dan password benar",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar.visibility = View.GONE
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if (pref.getIdToken().isNotEmpty()) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            finish()
        }
    }
}