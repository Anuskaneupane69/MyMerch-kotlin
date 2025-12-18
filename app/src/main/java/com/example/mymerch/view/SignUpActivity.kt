package com.example.mymerch.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymerch.R
import com.example.mymerch.ui.theme.BLUE
import com.example.mymerch.viewmodel.SignUpViewModel

class SignUpActivity : ComponentActivity() {
    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SignUpBody(
                viewModel = signUpViewModel,
                onSignInClicked = {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            })
        }
    }
}

@Composable
fun SignUpBody(viewModel: SignUpViewModel, onSignInClicked: () -> Unit) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    val signUpStatus by viewModel.signUpStatus.collectAsState()

    LaunchedEffect(signUpStatus) {
        signUpStatus?.let {
            if (it.isSuccess) {
                Toast.makeText(context, "Sign up successful", Toast.LENGTH_SHORT).show()
                // Navigate to main screen or another destination
            } else {
                Toast.makeText(context, it.exceptionOrNull()?.message ?: "Sign up failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = "My Merch",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(1.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "My Merch Logo",
                modifier = Modifier
                    .size(120.dp)
                    .padding(8.dp),
                alignment = Alignment.Center
            )



            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.signUp(email, password)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign Up")
            }


            Text(buildAnnotatedString {
                append("Already have an account? ")

                withStyle(SpanStyle(color = BLUE)) {
                    append("Sign In")
                }
            },
                modifier = Modifier.clickable {
                    onSignInClicked()
                })


        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignup() {
    SignUpBody(viewModel = SignUpViewModel(), onSignInClicked = {})
}
