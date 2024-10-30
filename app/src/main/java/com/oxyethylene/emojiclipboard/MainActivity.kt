package com.oxyethylene.emojiclipboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kongzue.dialogx.DialogX
import com.oxyethylene.emojiclipboard.application.App
import com.oxyethylene.emojiclipboard.common.constants.AppRoutes
import com.oxyethylene.emojiclipboard.domain.objects.EmojiMap
import com.oxyethylene.emojiclipboard.ui.activity.mainactivity.BottomNavBar
import com.oxyethylene.emojiclipboard.ui.activity.mainactivity.EmojiPage
import com.oxyethylene.emojiclipboard.ui.activity.mainactivity.HomeFragment
import com.oxyethylene.emojiclipboard.ui.activity.mainactivity.ProfilePage
import com.oxyethylene.emojiclipboard.ui.theme.EmojiClipboardTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var emojiMap: EmojiMap

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.getInstance().putData("emoji_map", emojiMap)

        setContent {
            EmojiClipboardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()

                    val darkTheme = isSystemInDarkTheme()

                    if (darkTheme) {
                        DialogX.globalTheme = DialogX.THEME.DARK
                    } else {
                        DialogX.globalTheme = DialogX.THEME.LIGHT
                    }

                    Scaffold(
                        modifier = Modifier.background(Color.Transparent),
                        bottomBar = { BottomNavBar(navController) }
                    ) {
                        innerPadding ->

                        NavHost(
                            navController = navController,
                            startDestination = AppRoutes.HOME_PAGE,
                            modifier = Modifier.padding(innerPadding),
                            enterTransition = { fadeIn(tween(400)) },
                            exitTransition = { fadeOut(tween(400)) }
                        ) {

                            composable(route = AppRoutes.HOME_PAGE) {
                                HomeFragment(emojiMap = emojiMap)
                            }

                            composable(route = AppRoutes.EMOJI_PAGE) {
                                EmojiPage(emojiMap = emojiMap)
                            }

                            composable(route = AppRoutes.PROFILE_PAGE) {
                                ProfilePage()
                            }

                        }

                    }

                }
            }
        }
    }
}
