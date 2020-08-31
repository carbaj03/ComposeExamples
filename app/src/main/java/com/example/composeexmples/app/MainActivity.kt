package com.example.composeexmples.app

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.material.Button
import androidx.compose.runtime.Providers
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import com.example.composeexmples.app.common.ProvideDisplayInsets
import com.example.composeexmples.app.common.SysUiController
import com.example.composeexmples.app.common.SystemUiController

class MainActivity : AppCompatActivity() {

    private val navigationViewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            Providers(SysUiController provides systemUiController) {

                App(navigationViewModel = navigationViewModel)
            }
        }
    }
}