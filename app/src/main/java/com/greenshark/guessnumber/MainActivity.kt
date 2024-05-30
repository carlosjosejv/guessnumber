package com.greenshark.guessnumber

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greenshark.guessnumber.ui.theme.GuessNumberTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GuessNumberTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Game(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Game(modifier: Modifier = Modifier) {
    var numero by remember {
        mutableIntStateOf(Random.nextInt(1, 101))
    }

    var intentos by remember {
        mutableStateOf(1)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Adivina el Número",
            modifier = modifier
        )

        var message by remember {
            mutableStateOf("")
        }

        Text(text = message)

        var filledText by remember {
            mutableStateOf("")
        }

        var isEnabled by remember {
            mutableStateOf(true)
        }

        var visibility by remember {
            mutableStateOf(0f)
        }

        TextField(
            value = filledText,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { filledText = it }, enabled = isEnabled
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Text(text = "Intentos restantes: $intentos")

        Spacer(modifier = Modifier.padding(5.dp))

        Button(onClick = {
            intentos--
            if (intentos > 0) {
                message = checkNumber(filledText.toInt(), numero)
            } else {
                message = "Has agotado tus intentos"
                isEnabled = false
                visibility = 1f
            }
        }, enabled = isEnabled) {
            Text(text = "¡Adivinar!")
        }

        Button(onClick = {
            isEnabled = true
            numero = Random.nextInt(1, 101)
            intentos = 10
            visibility = 0f
            message = ""
            filledText = ""
        }, Modifier.alpha(visibility), enabled = !isEnabled) {
            Text(text = "¡Volver a Jugar!")
        }
    }
}

fun checkNumber(number: Int, answer: Int): String {
    Log.d("TrackNumber", "checkNumber: $number")
    Log.d("TrackNumber", "checkNumber: $answer")
    return if (number == answer) {
        "Felicidades! ¡Has adivinado el número!"
    } else {
        if (number > answer) {
            "El número es más bajo"
        } else {
            "El número es más alto"
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GamePreview() {
    GuessNumberTheme {
        Game()
    }
}