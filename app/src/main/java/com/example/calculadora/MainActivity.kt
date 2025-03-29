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

    CalculadoraUI(input = input, onInputChange = { input = it }, result = result, onCalculate = { result = calculate(input) })
}

@Composable
fun CalculadoraUI(input: String, onInputChange: (String) -> Unit, result: String, onCalculate: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Display result
        Text(text = "Resultado: $result", style = MaterialTheme.typography.headlineMedium)

        // Input field
        Spacer(modifier = Modifier.height(16.dp))
        BasicTextField(
            value = TextFieldValue(input),
            onValueChange = { onInputChange(it.text) },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            singleLine = true
        )

        // Buttons
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(text = "1", onClick = { onInputChange(input + "1") })
            CalculatorButton(text = "2", onClick = { onInputChange(input + "2") })
            CalculatorButton(text = "3", onClick = { onInputChange(input + "3") })
            CalculatorButton(text = "/", onClick = { onInputChange(input + "/") })
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(text = "4", onClick = { onInputChange(input + "4") })
            CalculatorButton(text = "5", onClick = { onInputChange(input + "5") })
            CalculatorButton(text = "6", onClick = { onInputChange(input + "6") })
            CalculatorButton(text = "*", onClick = { onInputChange(input + "*") })
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(text = "7", onClick = { onInputChange(input + "7") })
            CalculatorButton(text = "8", onClick = { onInputChange(input + "8") })
            CalculatorButton(text = "9", onClick = { onInputChange(input + "9") })
            CalculatorButton(text = "-", onClick = { onInputChange(input + "-") })
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(text = "0", onClick = { onInputChange(input + "0") })
            CalculatorButton(text = ".", onClick = { onInputChange(input + ".") })
            CalculatorButton(text = "=", onClick = { onCalculate() })
            CalculatorButton(text = "+", onClick = { onInputChange(input + "+") })
        }
    }
}

@Composable
fun CalculatorButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.size(60.dp)) {
        Text(text = text)
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

// A simple eval function (you could also implement this yourself or use libraries)
fun eval(expression: String): Double {
    val process = ProcessBuilder("bc", "-l")
    val output = process.start().outputStream
    output.write(expression.toByteArray())
    output.close()
    return output.read().toDouble()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculadoraApp()
}