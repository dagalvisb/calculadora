package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraApp()
        }
    }
}

@Composable
fun CalculadoraApp() {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    CalculadoraUI(
        input = input,
        onInputChange = { input = it },
        result = result,
        onCalculate = { result = calculate(input) },
        onReset = { input = ""; result = "" }
    )
}

@Composable
fun CalculadoraUI(
    input: String,
    onInputChange: (String) -> Unit,
    result: String,
    onCalculate: () -> Unit,
    onReset: () -> Unit
) {
    val naranja = Color(0xFFF57C00)
    val gris = Color(0xFF616161)
    val blanco = Color.White
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "$result", fontSize = 60.sp)

        Spacer(modifier = Modifier.height(16.dp))
        BasicTextField(
            value = TextFieldValue(input),
            onValueChange = { onInputChange(it.text) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CalculatorButton(text = "1", onClick = { onInputChange(input + "1") }, backgroundColor = gris, textColor = blanco)
            CalculatorButton(text = "2", onClick = { onInputChange(input + "2") }, backgroundColor = gris, textColor = blanco)
            CalculatorButton(text = "3", onClick = { onInputChange(input + "3") }, backgroundColor = gris, textColor = blanco)
            CalculatorButton(text = "/", onClick = { onInputChange(input + "/") }, backgroundColor = naranja, textColor = blanco)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CalculatorButton(text = "4", onClick = { onInputChange(input + "4") }, backgroundColor = gris, textColor = blanco)
            CalculatorButton(text = "5", onClick = { onInputChange(input + "5") }, backgroundColor = gris, textColor = blanco)
            CalculatorButton(text = "6", onClick = { onInputChange(input + "6") }, backgroundColor = gris, textColor = blanco)
            CalculatorButton(text = "*", onClick = { onInputChange(input + "*") }, backgroundColor = naranja, textColor = blanco)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CalculatorButton(text = "7", onClick = { onInputChange(input + "7") }, backgroundColor = gris, textColor = blanco)
            CalculatorButton(text = "8", onClick = { onInputChange(input + "8") }, backgroundColor = gris, textColor = blanco)
            CalculatorButton(text = "9", onClick = { onInputChange(input + "9") }, backgroundColor = gris, textColor = blanco)
            CalculatorButton(text = "-", onClick = { onInputChange(input + "-") }, backgroundColor = naranja, textColor = blanco)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CalculatorButton(text = "0", onClick = { onInputChange(input + "0") }, backgroundColor = gris, textColor = blanco)
            CalculatorButton(text = ".", onClick = { onInputChange(input + ".") }, backgroundColor = naranja, textColor = blanco)
            CalculatorButton(text = "=", onClick = { onCalculate() }, backgroundColor = naranja, textColor = blanco)
            CalculatorButton(text = "+", onClick = { onInputChange(input + "+") }, backgroundColor = naranja, textColor = blanco)
        }

        Spacer(modifier = Modifier.height(24.dp))
        CalculatorButton(text = "C", onClick = onReset, backgroundColor = naranja, textColor = blanco)
    }
}

@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary
) {

    Button(
        onClick = onClick,
        modifier = Modifier.size(80.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        )
    ) {
        Text(text = text, fontSize = 25.sp)
    }
}

fun calculate(input: String): String {
    return try {
        val result = eval(input)
        result.toString()
    } catch (e: Exception) {
        "Error"
    }
}


fun eval(expression: String): Double {
    val process = ProcessBuilder("bc", "-l").start()
    val output = process.outputStream
    val input = process.inputStream

    output.write("$expression\n".toByteArray())
    output.flush()
    output.close()

    val result = input.bufferedReader().readLine()
    return result.toDouble()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculadoraApp()
}