package com.rubens.conectamedicina.shimmertutorial

import android.util.Log
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ShimmerListItem(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Row(modifier = modifier) {
            Box(
                modifier = Modifier.size(100.dp)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(20.dp)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(20.dp)
                        .shimmerEffect()
                )


            }
        }
    } else {
        contentAfterLoading()
    }

}

@Composable
fun ShimmerCircularImageWithBackground(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Box(
            modifier = modifier
                .size(45.dp)
                .clip(CircleShape)
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.width(6.dp))
    } else {
        contentAfterLoading()
    }
}

@Composable
fun ShimmerUserWelcomingText(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Box(
            modifier = modifier.fillMaxWidth(0.5f)
                .clip(RoundedCornerShape(4.dp))
                .size(40.dp)
                .shimmerEffect()


        )
    } else {
        contentAfterLoading()
    }

}

@Composable
fun ShimmerLabelText(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Box(
            modifier = modifier.fillMaxWidth(0.5f)
                .clip(RoundedCornerShape(4.dp))
                .size(20.dp)
                .padding(start = 16.dp)
                .shimmerEffect()


        )
    } else {
        contentAfterLoading()
    }

}

@Composable
fun ShimmerSearchIcon(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Box(
            modifier = modifier
                .size(45.dp)
                .clip(CircleShape)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.width(6.dp))
    } else {
        contentAfterLoading()
    }

}

@Composable
fun ShimmerNotificationWithBadge(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Box(
            modifier = modifier
                .size(45.dp)
                .clip(CircleShape)
                .shimmerEffect()
        )
    } else {
        contentAfterLoading()
    }

}

@Composable
fun ShimmerHorizontalListItem(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Card(
            modifier = Modifier
                .padding(vertical = 16.dp)

        ) {
            Box(
                modifier = Modifier
                    .shimmerEffect()
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 5.dp)
                        .width(120.dp)
                ) {
                    Box(

                        modifier = Modifier
                            .size(48.dp)
                            .clip(shape = RoundedCornerShape(4.dp))
                            .align(Alignment.CenterHorizontally)

                    )

                    Box(

                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()

                    )
                }
            }


        }
    } else {
        contentAfterLoading()
    }
}

@Composable
fun ShimmerVerticalListItem(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Box(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 0.dp)
                .fillMaxWidth()
                .height(80.dp)
                .shimmerEffect()
        )

    } else {
        contentAfterLoading()
    }
}

@Composable
fun ShimmerDoctorList(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        LazyColumn {
            items(5){
                Box(
                    modifier = modifier
                        .padding(vertical = 16.dp, horizontal = 0.dp)
                        .fillMaxWidth()
                        .height(80.dp)
                        .shimmerEffect()
                )

            }
        }


    } else {
        contentAfterLoading()
    }
}

@Composable
fun ShimmerNotificationItem(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
){
    if(isLoading){
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(8){
                Column (){
                    Row{
                        Box(modifier = Modifier.fillMaxWidth(0.2f)
                            .padding(top = 10.dp, end = 5.dp, start = 16.dp)
                            .size(20.dp)
                            .clip(CircleShape)
                            .shimmerEffect()){}

                        Box(modifier = Modifier.fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 10.dp)
                            .height(20.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .shimmerEffect()){}

                    }

                    Box(modifier = Modifier.fillMaxWidth()
                        .padding(top = 5.dp, end = 16.dp, start = 16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .height(20.dp)
                        .shimmerEffect()){}

                    Box(modifier = Modifier.fillMaxWidth()
                        .padding(top = 5.dp, end = 16.dp, start = 16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .height(20.dp)
                        .shimmerEffect()){}

                }

            }
        }
    }else{
        contentAfterLoading()
    }
}

@Composable
fun ShimmerAppointmentList(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit
){
    if(isLoading){
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(5){
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Box(modifier = Modifier.fillParentMaxWidth(0.2f)
                        .height(60.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()) {  }
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .padding(end = 16.dp)
                            .shimmerEffect()) {  }
                        Spacer(Modifier.height(20.dp))
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .padding(end = 16.dp)
                            .shimmerEffect()) {  }
                        Spacer(Modifier.height(20.dp))

//                        Row {
//                            Box(modifier = Modifier
//                                .fillMaxWidth()
//                                .height(20.dp)
//                                .padding(end = 16.dp)) {  }
//
//                            Box(modifier = Modifier
//                                .fillMaxWidth()
//                                .height(20.dp)
//                                .padding(end = 16.dp)) {  }
//
//                        }
                    }
                }
            }

        }

    }else{
        contentAfterLoading()
    }
}

@Preview
@Composable
fun PreviewShimmerAppointmentList(){
    ShimmerAppointmentList(
        isLoading = true,
        contentAfterLoading = {}
    )
}


fun Modifier.shimmerEffect(): Modifier = composed {
    //size from the composable that you want to animate
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )

    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFE6E6E6),
                Color(0xFFD0D0D0),
                Color(0xFFD0D0D0),

                ),
            start = Offset(startOffsetX, 0f),
            end = Offset(
                startOffsetX + size.width.toFloat(),
                size.height.toFloat()
            )
        )

    )
        .onGloballyPositioned {
            size = it.size
        }
}

@Preview(widthDp = 300, heightDp = 800)
@Composable
fun PreviewShimmerEffect() {

    var isLoading by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(true) {
        delay(10000)
        isLoading = false
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(20) {
            ShimmerListItem(
                isLoading = isLoading,
                contentAfterLoading = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "This is a long text to simulate some text that I" +
                                    " load from the Api"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            )
        }
    }
}

