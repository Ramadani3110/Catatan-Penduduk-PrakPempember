package com.rams.catatanpenduduk.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.rams.catatanpenduduk.R
import com.rams.catatanpenduduk.data.local.TokenManager
import com.rams.catatanpenduduk.databinding.ActivityLoginBinding
import com.rams.catatanpenduduk.di.Inject
import com.rams.catatanpenduduk.di.ViewModelFactory
import com.rams.catatanpenduduk.ui.main.MainActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val repository = Inject.provideRepository(this)
        val factory = ViewModelFactory(repository)
        loginViewModel = ViewModelProvider(this,factory)[LoginViewModel::class.java]

        setupListener()
        setupObservers()

    }

    private fun setupListener(){
        binding.apply {
            btnLogin.setOnClickAction {
                val email = edEmail.text.toString()
                val password = edPassword.text.toString()

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(this@LoginActivity, "Email atau Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    return@setOnClickAction
                }

                loginViewModel.login(email,password)
            }
        }
    }

    private fun setupObservers(){
        loginViewModel.isLoading.observe(this){
            binding.btnLogin.showLoading(it)
        }
        loginViewModel.loginResult.observe(this) { result ->
            lifecycleScope.launch {
                TokenManager.getInstance(applicationContext).saveToken(result.accessToken)
            }
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
        loginViewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}