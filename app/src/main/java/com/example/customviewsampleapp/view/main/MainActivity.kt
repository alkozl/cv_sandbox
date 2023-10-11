package com.example.customviewsampleapp.view.main

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.customviewsampleapp.R
import com.example.customviewsampleapp.databinding.ActivityMainBinding
import com.example.customviewsampleapp.view.navigation.MainNavigation.navigateToGraphFragment

class MainActivity : FragmentActivity() {
    val fragmentContainer get() = R.id.fragmentContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        navigateToGraphFragment(this)
    }
}