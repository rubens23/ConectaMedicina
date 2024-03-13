package com.rubens.conectamedicina.ui.signinScreen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rubens.conectamedicina.ui.MainViewModel

@Composable
fun EmailEditText(
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit
    ){
    var text by remember {mutableStateOf("")}

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onTextChanged(it)
        },
        modifier = modifier,
        leadingIcon = {
                      Icon(
                          imageVector = Icons.Default.Email,
                          contentDescription = null,
                          tint = Color.Black
                      )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        placeholder = {
            Text("Email address")
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF43C2FF),
            cursorColor = Color(0xFF43C2FF)

        )    )
}

@Composable
fun NameEditText(
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit
){
    var text by remember {mutableStateOf("")}

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onTextChanged(it)
        },
        modifier = modifier,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = Color.Black
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        placeholder = {
            Text("name")
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF43C2FF),
            cursorColor = Color(0xFF43C2FF)

        )    )
}

@Composable
fun LastNameEditText(
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit
){
    var text by remember {mutableStateOf("")}

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onTextChanged(it)
        },
        modifier = modifier,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = Color.Black
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        placeholder = {
            Text("last name")
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF43C2FF),
            cursorColor = Color(0xFF43C2FF)

        )

    )
}

@Composable
fun PasswordEditText(
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit
){
    var password by remember { mutableStateOf("") }

    OutlinedTextField(
        value = password,
        onValueChange = {
            password = it
            onTextChanged(it)
        },
        modifier = modifier,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = Color.Black
            )
        },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        placeholder = {
            Text("Password")
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF43C2FF),
            cursorColor = Color(0xFF43C2FF)
        )
    )
}

@Composable
fun SignUpButton(
    btnText: String,
//    typedUsername: String,
//    typedPassword: String,
    modifier: Modifier = Modifier,
    //onClick: (String, String) -> Unit
){
    Button(onClick = {},//onClick(typedUsername, typedPassword)},
        modifier = modifier,
    colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#43c2ff")))){
        Text(text = btnText, color = Color.White)
    }
}






//@Preview
//@Composable
//fun UsernameEditTextPreview(){
//    var username by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//
//    Column(modifier = Modifier.fillMaxWidth()
//        .padding(horizontal = 40.dp, vertical = 20.dp)) {
//        Text("Sign Up", modifier = Modifier.align(Alignment.CenterHorizontally)
//            .padding(vertical = 20.dp),
//        fontSize = 26.sp)
//        EmailEditText(modifier = Modifier.fillMaxWidth()){
//                typedUsername->
//            username = typedUsername
//        }
//        Spacer(Modifier.padding(top = 20.dp))
//        PasswordEditText(modifier = Modifier.fillMaxWidth()){
//            typedPassword->
//            password = typedPassword
//        }
//
//        Spacer(Modifier.padding(top = 20.dp))
//
//        SignUpButton("Sign Up", typedUsername = username, typedPassword =  password, modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 20.dp)
//        ) { clickedUsername, clickedPassword ->
//            //chamar metodo que ira fazer o signup
//
//        }
//
//
//    }
//
//
//
//}




@Composable
fun SecretScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(text = "You're authenticated!")
    }
}


@Composable
fun KindRadioGroupUsage(radioGroupItems: List<String>, modifier: Modifier = Modifier, viewModel: MainViewModel){
    val (selected, setSelected) = remember { mutableStateOf("") }
    Column(modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KindRadioGroup(
            mItems = radioGroupItems,
            viewModel.selectedRadioItem, setSelected, viewModel
        )

    }

}



@Composable
fun KindRadioGroup(
    mItems: List<String>,
    selected: String,
    setSelected: (selected: String) -> Unit,
    viewModel: MainViewModel,
){




    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

        mItems.forEach {
            item->
            RadioButton(
                selected = selected == item,
                onClick = {
                    setSelected(item)
                    viewModel.setSelectedRadioItem(item)
                },
                enabled = true,
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.Black
                )
            )
            Text(text = item, modifier = Modifier.padding(start = 8.dp))

        }


        }
    }
}

