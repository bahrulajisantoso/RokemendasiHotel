package com.example.aplikasiskripsi.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikasiskripsi.MainActivity
import com.example.aplikasiskripsi.R
import com.example.aplikasiskripsi.UserPreference
import com.example.aplikasiskripsi.auth.LoginActivity
import com.example.aplikasiskripsi.databinding.ActivityUserBinding
import com.google.firebase.auth.FirebaseAuth

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "User"

        bottomNav()

        val auth = FirebaseAuth.getInstance()
        val pref = UserPreference(this)

        val id = pref.getUserId()
        val email = pref.getEmail()

        binding.tvId.text = "ID: $id"
        binding.tvEmail.text = "Email: $email"

        binding.btnLogout.setOnClickListener {

            AlertDialog.Builder(this@UserActivity).apply {
                setTitle("Logout")
                setMessage("Anda yakin ingin logout?")
                setPositiveButton("Ya") { _, _ ->
                    auth.signOut()
                    pref.logOut()
                    Toast.makeText(this@UserActivity, "Logout sukses", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@UserActivity, LoginActivity::class.java))
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    finish()
                }
                setNegativeButton("Tidak") { _, _ -> }
                create()
                show()
            }
        }
    }

    private fun bottomNav() {
        val navigation = binding.navView

        navigation.selectedItemId = R.id.navigation_user

        navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_user -> true
                R.id.navigation_home -> {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                else -> {
                    true
                }
            }

        }
    }
}