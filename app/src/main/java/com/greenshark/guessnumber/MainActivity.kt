package com.greenshark.guessnumber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
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

    var intentos by remember {
        mutableStateOf(5)
    }

    var numAleatorio by remember {
        mutableStateOf(Random.nextInt(1, 100))
    }

    var mensaje by remember {
        mutableStateOf("Buena suerte!")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Adivina el número")

        Text(text = mensaje)

        var numIngresado by remember {
            mutableStateOf("")
        }

        var activo by remember {
            mutableStateOf(true)
        }

        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            enabled = activo,
            value = numIngresado,
            onValueChange = {
                numIngresado = it
            })

        Text(text = "Intentos: $intentos")

        Row {
            Button(enabled = !activo, onClick = {
                numAleatorio = Random.nextInt(1, 100)
                intentos = 5
                mensaje = "Buena suerte!"
                activo = true
            }) {
                Text(text = "Reiniciar")
            }

            Button(enabled = activo && numIngresado.isNotEmpty(), onClick = {
                //Decrementamos los intentos
                intentos--
                //Comprobamos si el número ingresado es correcto
                if(numAleatorio == numIngresado.toInt()){
                    //Si es correcto, reiniciamos el juego
                    // y le mostramos un mensaje de Ganaste!
                    mensaje = "Ganaste!"
                    activo = false
                } else {
                    //Comprobamos si hay intentos restantes
                   if(intentos > 0){
                       //Si hay intentos restantes,
                       // comprobamos si el número ingresado es más alto o más bajo
                       if(numIngresado.toInt() > numAleatorio){
                           mensaje = "El número es más bajo"
                       } else {
                           mensaje = "El número es más alto"
                       }
                   }else{
                       //Si no hay intentos restantes,
                       // entonces le mostramos el mensaje de Perdiste!
                       mensaje = "Perdiste!"
                       activo = false
                   }

                }

                //Luego de hacer las verificaciones necesarias,
                // volvemos el valor de numIngresado a vacío
                numIngresado = ""

            }) {
                Text(text = "¡Adivina!")
            }

        }

    }
}

//Función para comprobar si el número ingresado es correcto
fun checkNumber(numIngresado: Int, numAleatorio: Int): String {
    if (numIngresado == numAleatorio) {
        return "Ganaste!"
    } else if (numIngresado > numAleatorio) {

        return "El número es más bajo"
    } else {
        return "El número es más alto"
    }
}

@Preview(showBackground = true)
@Composable
fun GamePreview() {
    GuessNumberTheme {
        Game()
    }
}