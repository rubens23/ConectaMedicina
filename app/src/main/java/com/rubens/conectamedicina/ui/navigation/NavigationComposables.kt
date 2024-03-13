package com.rubens.conectamedicina.ui.navigation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rubens.conectamedicina.ui.utils.getTintedColorFilter
import com.rubens.conectamedicina.ui.utils.mainBlue


@Composable
fun EmptyBottomNavigation(){
    NavigationBar {  }
}

@Composable
fun BottomNavigation(navController: NavHostController,
                     goToHomeScreen: ()->Unit,
                     goToSearchScreen: ()->Unit,
                     goToAppointmentsScreen: ()-> Unit,
                     goToNotificationsScreen: ()->Unit){
    val items = BottomNavItems.items

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEachIndexed { index, screen ->

            val selected = currentDestination?.hierarchy?.any {
                    it.route == screen.screenRoute
            } == true

            NavigationBarItem(selected = selected,
                onClick = {
                    when(screen.screenRoute){
                        "home"->{
                            goToHomeScreen()
                        }
                        "searchScreen"->{
                            goToSearchScreen()
                        }
                        "appointments"->{
                            goToAppointmentsScreen()
                        }
                        "notification"->{
                            goToNotificationsScreen()
                        }
                    }
                },
                icon = {
                    if(selected) {
                        Image(
                            painter = painterResource(id = screen.icon),
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            colorFilter = getTintedColorFilter(mainBlue)
                        )
                    }else{
                        Image(
                            painter = painterResource(id = screen.icon),
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            colorFilter = getTintedColorFilter("#666666")
                        )
                    }

                },
//                colors = NavigationBarItemDefaults.colors(
//                    selectedIconColor = Color(0xFF43C2FF),
//                    unselectedIconColor = Color.DarkGray
//                )
            )

        }


    }

}