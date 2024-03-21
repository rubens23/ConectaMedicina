package com.rubens.conectamedicina

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.rubens.conectamedicina.ui.MainTheme
import com.rubens.conectamedicina.ui.theme.ConectaMedicinaTheme
import com.rubens.conectamedicina.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.auto(android.graphics.Color.TRANSPARENT,android.graphics.Color.TRANSPARENT))
        //WindowCompat.setDecorFitsSystemWindows(window, false)

        installSplashScreen().apply{
            setKeepOnScreenCondition{ viewModel.loading.value }
        }
        setContent { ConectaMedicinaTheme { MainTheme(viewModel = viewModel) } }


    }


}







