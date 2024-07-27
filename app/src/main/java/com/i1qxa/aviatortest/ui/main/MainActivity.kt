package com.i1qxa.aviatortest.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.i1qxa.aviatortest.R
import com.i1qxa.aviatortest.databinding.ActivityMainBinding
import com.i1qxa.aviatortest.domain.backgroundIdKey
import com.i1qxa.aviatortest.domain.dataStore
import com.i1qxa.aviatortest.ui.game_activity.GameActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        observeTimer()
        observeBackgroundResource()
    }

    private fun observeTimer(){
        viewModel.shouldStartGame.observe(this){
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeBackgroundResource(){
        lifecycleScope.launch {
            this@MainActivity.dataStore.data.collect{
                val backgroundResourceId = it[backgroundIdKey]?:R.drawable.game_background_1
                binding.mainBackground?.setImageResource(backgroundResourceId)
            }
        }
    }

}