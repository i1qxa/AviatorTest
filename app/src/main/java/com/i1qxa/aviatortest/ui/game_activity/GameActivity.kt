package com.i1qxa.aviatortest.ui.game_activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.i1qxa.aviatortest.R
import com.i1qxa.aviatortest.databinding.ActivityGameBinding
import com.i1qxa.aviatortest.domain.backgroundIdKey
import com.i1qxa.aviatortest.domain.dataStore
import com.i1qxa.aviatortest.domain.highScoreKey
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity() {

    private val binding by lazy { ActivityGameBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        observeBackgroundResource()
    }

    private fun observeBackgroundResource(){
        lifecycleScope.launch {
            this@GameActivity.dataStore.data.collect{
                val backgroundResourceId = it[backgroundIdKey]?:R.drawable.game_background_1
                binding.mainBackground.setImageResource(backgroundResourceId)
            }
        }
    }

}