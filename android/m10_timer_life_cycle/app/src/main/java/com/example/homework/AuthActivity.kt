package com.example.homework

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BundleCompat
import androidx.core.view.isVisible
import com.example.homework.databinding.AuthActivityBinding

class AuthActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "EXTRA_USER"
        const val EXTRA_HAS_SNAME = "EXTRA_HAS_SNAME"
    }

    private lateinit var binding: AuthActivityBinding

    class AuthResultContract : ActivityResultContract<Boolean, User?>() {
        override fun createIntent(context: Context, input: Boolean): Intent {
            return Intent(context, AuthActivity::class.java).apply {
                putExtra("EXTRA_HAS_SNAME", input)
            }
        }

        override fun parseResult(resultCode: Int, intent: Intent?): User? {
            if (resultCode == Activity.RESULT_OK && intent != null) {
                return intent.extras?.let {
                    BundleCompat.getParcelable(it, EXTRA_USER, User::class.java)
                }
            }
            return null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ДЗ6", "Авторизация OnCreate")

        binding = AuthActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hasSname = intent.getBooleanExtra(EXTRA_HAS_SNAME, false)
        binding.snameInputLayout.isVisible = hasSname

        binding.saveButton.setOnClickListener {
            val user = User(
                lname = binding.lnameInputText.text.toString(),
                fname = binding.fnameInputText.text.toString(),
                sname = if (hasSname) binding.snameInputText.text.toString() else null
            )

            val result = Intent().apply {
                putExtra(EXTRA_USER, user)
            }
            setResult(Activity.RESULT_OK, result)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("ДЗ6", "Авторизация onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ДЗ6", "Авторизация onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ДЗ6", "Авторизация onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ДЗ6", "Авторизация onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ДЗ6", "Авторизация onDestroy")
    }
}