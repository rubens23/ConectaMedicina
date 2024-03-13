package com.rubens.conectamedicina.ui.reviewAndFeedbackScreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rubens.conectamedicina.R
import com.rubens.conectamedicina.ui.reviewAndFeedbackScreen.viewModel.ReviewAndFeedbacksViewModel

@Composable
fun StarRatingSystem(
    clickedOnRating: (rating: Int)-> Unit) {
    val numberOfStarChosen = remember { mutableStateOf(0) }
    val interactionSource = remember { MutableInteractionSource() }

    Spacer(Modifier.width(10.dp))




    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(if (numberOfStarChosen.value > 0) R.drawable.ic_star_filled else R.drawable.star_unfilled),
            contentDescription = null,
            colorFilter = if (numberOfStarChosen.value > 0) ColorFilter.tint(
                Color(
                    android.graphics.Color.parseColor(
                        "#43c2ff"
                    )
                )
            ) else ColorFilter.tint(Color.LightGray),
            modifier = Modifier.size(40.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    numberOfStarChosen.value = 1
                    clickedOnRating(1)
                }
        )
        Spacer(Modifier.width(10.dp))

        Image(
            painter = painterResource(if (numberOfStarChosen.value > 1) R.drawable.ic_star_filled else R.drawable.star_unfilled),
            contentDescription = null,
            colorFilter = if (numberOfStarChosen.value > 1) ColorFilter.tint(
                Color(
                    android.graphics.Color.parseColor(
                        "#43c2ff"
                    )
                )
            ) else ColorFilter.tint(Color.LightGray), modifier = Modifier.size(40.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    numberOfStarChosen.value = 2
                    clickedOnRating(2)
                }

        )
        Spacer(Modifier.width(10.dp))

        Image(
            painter = painterResource(if (numberOfStarChosen.value > 2) R.drawable.ic_star_filled else R.drawable.star_unfilled),
            contentDescription = null,
            colorFilter = if (numberOfStarChosen.value > 2) ColorFilter.tint(
                Color(
                    android.graphics.Color.parseColor(
                        "#43c2ff"
                    )
                )
            ) else ColorFilter.tint(Color.LightGray), modifier = Modifier.size(40.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    numberOfStarChosen.value = 3
                    clickedOnRating(3)
                }

        )
        Spacer(Modifier.width(10.dp))

        Image(
            painter = painterResource(if (numberOfStarChosen.value > 3) R.drawable.ic_star_filled else R.drawable.star_unfilled),
            contentDescription = null,
            colorFilter = if (numberOfStarChosen.value > 3) ColorFilter.tint(
                Color(
                    android.graphics.Color.parseColor(
                        "#43c2ff"
                    )
                )
            ) else ColorFilter.tint(Color.LightGray), modifier = Modifier.size(40.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    numberOfStarChosen.value = 4
                    clickedOnRating(4)
                }

        )
        Spacer(Modifier.width(10.dp))



        Image(
            painter = painterResource(if (numberOfStarChosen.value > 4) R.drawable.ic_star_filled else R.drawable.star_unfilled),
            contentDescription = null,
            colorFilter = if (numberOfStarChosen.value > 4) ColorFilter.tint(
                Color(
                    android.graphics.Color.parseColor(
                        "#43c2ff"
                    )
                )
            ) else ColorFilter.tint(Color.LightGray), modifier = Modifier.size(40.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    numberOfStarChosen.value = 5
                    clickedOnRating(5)
                }

        )
    }

}

@Composable
@Preview
fun StarRatingSystemPreview() {
    StarRatingSystem(){

    }

}

@Composable
@Preview
fun ReviewAndFeedbackLayoutPreview() {
    //ReviewAndFeedbackLayout()
}

