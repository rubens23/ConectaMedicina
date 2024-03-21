package com.rubens.conectamedicina

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Badge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rubens.conectamedicina.data.doctors.Doctor
import com.rubens.conectamedicina.data.doctors.MedicalCategory
import com.rubens.conectamedicina.ui.utils.mainBlue
import com.rubens.conectamedicina.ui.MainViewModel
import com.rubens.conectamedicina.ui.mainScreen.viewModel.MainScreenViewModel
import com.rubens.conectamedicina.ui.notificatioScreen.viewModel.NotificationViewModel


@Composable
fun CircularImageWithBackgroundUserProfileImage(
                                                modifier: Modifier = Modifier,
                                                viewModel: MainScreenViewModel,
                                                userUsername: String,
                                                userProfilePicture: String){
    var imageUri = remember { mutableStateOf(if(userProfilePicture.isNotBlank())userProfilePicture.toUri() else null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){
        uri ->
        imageUri.value = uri
        viewModel.saveProfileImageToStorage(uri, userUsername)
    }


    Surface(
        modifier = modifier
            .size(45.dp)
            .clip(CircleShape)
            .background(Color(android.graphics.Color.parseColor("#43c2ff")))
            .clickable {
                       launcher.launch("image/*")
            },
        contentColor = contentColorFor(MaterialTheme.colorScheme.background)
    ){

        //todo se o user tiver uma imagem de perfil tem que ser mostrada aqui
        //todo imageUri deve aparecer sempre que o user escolher uma imagem nova
        //todo se o user não tem uma imagem salva e ele ainda não escolheu nenhuma, o drawable deve aparecer
        //AsyncImage é um metodo do coil para android
        AsyncImage(
            model = imageUri.value?:R.drawable.baseline_person_24,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
                .clip(CircleShape)
             //colorFilter =  if(imageUri.value == null )ColorFilter.tint(Color(android.graphics.Color.parseColor("#43c2ff"))) else null


        )
    }
}

@Composable
fun CircularImageWithBackground(imageResId: Int,
                                modifier: Modifier = Modifier,
                                viewModel: MainViewModel = hiltViewModel()){
    var imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){
            uri ->
//        imageUri.value = uri
//        viewModel.saveProfileImageToStorage(uri, userUsername)
    }


    Surface(
        modifier = modifier
            .size(45.dp)
            .clip(CircleShape)
            .background(Color(android.graphics.Color.parseColor("#43c2ff")))
            .clickable {
                launcher.launch("image/*")
            },
        contentColor = contentColorFor(MaterialTheme.colorScheme.background)
    ){

        AsyncImage(
            model = imageUri.value?:R.drawable.baseline_person_24,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
                .clip(CircleShape)
            //colorFilter =  if(imageUri.value == null )ColorFilter.tint(Color(android.graphics.Color.parseColor("#43c2ff"))) else null


        )
    }
}

@Composable
fun UserWelcomingText(userName: String, modifier: Modifier = Modifier
){
    Text("Hi $userName", fontSize = 26.sp,
        modifier = modifier.fillMaxWidth(0.7f),
    fontWeight = FontWeight.ExtraBold)
}

@Composable
fun SearchIcon(modifier: Modifier = Modifier,
               goToSearchScreen: ()->Unit){



    Surface(
        modifier = modifier
            .size(48.dp)
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
    ){
        IconButton(onClick = {
            goToSearchScreen()

        }){
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color(android.graphics.Color.parseColor("#666666")),
                modifier = Modifier
                    .size(34.dp)

            )
        }




    }
}


@Composable
fun HorizontalListItem(item: MedicalCategory, onItemClick: (MedicalCategory)-> Unit){
    Card(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .clickable { onItemClick(item) }

    ){
        Box(
            modifier = Modifier
                .background(Color.White)
        ){
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 5.dp)
                    .width(120.dp)
            ){
                Image(
                    painter = painterResource(id = item.categoryImage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(Color(android.graphics.Color.parseColor("#43c2ff"))),
                    modifier = Modifier
                        .size(48.dp)
                        .clip(shape = RoundedCornerShape(4.dp))
                        .align(Alignment.CenterHorizontally)

                )

                Text(
                    text = item.name,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }



    }
}



@Composable
fun LabelText(text: String, modifier: Modifier = Modifier){
    Text(text, modifier = modifier.padding(start = 16.dp),
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.ExtraBold)
}

@Composable
fun VerticalListItem(item: Doctor, onItemClick: (Doctor)->Unit){
    Card(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .clickable { onItemClick(item) }
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.White)

        ){
            Row(
                modifier = Modifier.fillMaxSize()
            ){
                //configurar o carregamento da foto a partir da url
                AsyncImage(
                    //painter = if(item.profilePicture != "") rememberAsyncImagePainter(item.profilePicture) else painterResource(R.drawable.doctor1),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.profilePicture)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.whiteplaceholder),//todo replace this later with a white image
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth(0.35f)
                        .fillMaxHeight()
                        .clip(shape = RoundedCornerShape(8.dp))

                )

                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp)


                ){
                    Text(text = item.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp)
                    Text(text = item.specialty,
                        modifier = Modifier
                            .padding(top = 5.dp),
                    fontSize = 16.sp)

                    StarRating(item.rating, item.totalOfRatings)
                }
            }



        }
    }

}

@Composable
fun StarRating(rating: Int, totalOfRatings: Int){
    Row(
        modifier = Modifier.padding(top = 4.dp)
    ){
        Log.d("MainScreenComposables2", "rating $rating totalOfRatings $totalOfRatings")
        if(totalOfRatings > 0){
            repeat(rating){
                Icon(
                    painter = painterResource(id = R.drawable.ic_star_filled),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                        .padding(end = 2.dp),
                    tint = Color.Unspecified
                )
            }
        }

    }
}



//notification icon with badge
//https://www.youtube.com/watch?v=7YkOUs98Cfs
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationWithBadge(viewModel: NotificationViewModel = hiltViewModel(), goToNotificationsScreen: ()->Unit){

    viewModel.getUnseenNotificationCount()

    val unseenNotificationsCount = remember { mutableStateOf<Int>(0) }

    LaunchedEffect(Unit){
        viewModel.notificationCountResult.collect{

            unseenNotificationsCount.value = it
        }
    }


    Box(
        modifier = Modifier.padding(top = 10.dp)
            .clickable {
                goToNotificationsScreen()

            }
        //contentAlignment = Alignment.Center
    ){
        BadgedBox(
            badge = {
                Badge(
                    backgroundColor = if (unseenNotificationsCount.value > 0) Color(android.graphics.Color.parseColor(mainBlue)) else Color.Transparent,
                    contentColor = Color.White,
                    content = {
                        if (unseenNotificationsCount.value > 0){
                            Text(text = unseenNotificationsCount.value.toString(),
                                color = Color.White)
                        }

                    },
                    modifier = Modifier.offset(x = (-13).dp, y = 12.dp)

                )
            }
        ){
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = null,
                modifier = Modifier.size(36.dp),
                tint = Color(android.graphics.Color.parseColor("#666666"))
            )
        }
    }
}




