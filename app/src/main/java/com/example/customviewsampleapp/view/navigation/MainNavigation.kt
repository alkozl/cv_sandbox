package com.example.customviewsampleapp.view.navigation

import androidx.fragment.app.commit
import com.example.customviewsampleapp.view.graph.GraphFragment
import com.example.customviewsampleapp.view.main.MainActivity

object MainNavigation {
    fun navigateToGraphFragment(mainActivity: MainActivity) {
        mainActivity.supportFragmentManager.run {
            commit {
                add(mainActivity.fragmentContainer, GraphFragment())
            }
        }
    }
}