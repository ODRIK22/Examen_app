package com.example.examen_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    onNavigateToSignup: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Estados de error
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    // No necesitas showErrorDialog ni errorMessage si los errores se muestran en los campos.
    // var showErrorDialog by remember { mutableStateOf(false) }
    // var errorMessage by remember { mutableStateOf("") }

    // Función de validación de email
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Función de validación de contraseña
    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    // Función de validación completa
    fun validateFields(): Boolean {
        // Validar email
        if (email.isBlank()) {
            emailError = "El email es requerido"
        } else if (!isValidEmail(email)) {
            emailError = "El formato del email es inválido"
        } else {
            emailError = ""
        }

        // Validar contraseña
        if (password.isBlank()) {
            passwordError = "La contraseña es requerida"
        } else if (!isValidPassword(password)) {
            passwordError = "La contraseña debe tener al menos 6 caracteres"
        } else {
            passwordError = ""
        }

        // Devuelve true si no hay errores
        return emailError.isEmpty() && passwordError.isEmpty()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // ... (resto del código superior sin cambios)

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Login",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            text = "Welcome back!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // --- CAMPO EMAIL CON ERROR INTEGRADO ---
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                // Opcional: limpiar el error en cuanto el usuario empieza a escribir de nuevo.
                if (emailError.isNotEmpty()) {
                    emailError = ""
                }
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            // 1. El campo se marca visualmente como "en error" si la variable de error no está vacía.
            isError = emailError.isNotEmpty(),
            // 2. Aquí se muestra el mensaje de error debajo del campo.
            supportingText = {
                if (emailError.isNotEmpty()) {
                    Text(
                        text = emailError,
                        color = MaterialTheme.colorScheme.error // Usa el color de error del tema
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- CAMPO CONTRASEÑA CON ERROR INTEGRADO ---
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                // Limpiar el error al escribir.
                if (passwordError.isNotEmpty()) passwordError = ""
            },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    // Usar un Icon es más estándar que texto para el ícono de visibilidad
                    val icon = if (passwordVisible) "👁️" else "👁️‍🗨️"
                    Text(text = icon, fontSize = 20.sp)
                }
            },
            singleLine = true,
            // 1. Marcar el campo en error.
            isError = passwordError.isNotEmpty(),
            // 2. Mostrar el mensaje de error.
            supportingText = {
                if (passwordError.isNotEmpty()) {
                    Text(
                        text = passwordError,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        // ... (resto de tu código sigue igual)
        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { /* TODO: Implementar lógica de olvidar contraseña */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "Forgot Password?",
                color = Color(0xFF2196F3)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (validateFields()) {
                    // Si la validación es exitosa, procede con el login.
                    onLoginSuccess()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Log In",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // ... El resto de la UI (Signup, divisores, botones sociales) permanece igual
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account? ",
                color = Color.Black
            )
            TextButton(onClick = onNavigateToSignup) {
                Text(
                    text = "Signup",
                    color = Color(0xFF2196F3),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text(
                text = " Or ",
                modifier = Modifier.padding(horizontal = 8.dp),
                color = Color.Gray
            )
            HorizontalDivider(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.face),
                    contentDescription = "Face",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp)
                )
                Text(
                    text = "Sign up with Facebook",
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp)
                )
                Text(
                    text = "Sign up with Google",
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}
