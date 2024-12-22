package com.corbettcode.mybirdapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.corbettcode.mybirdapp.model.BirdImage
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import my_bird_app.composeapp.generated.resources.Res
import my_bird_app.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        val greeting = remember { Greeting().greet() }
        var showContent by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            println(getImages())
        }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    KamelImage(
                        asyncPainterResource("https://sebi.io/demo-image-api/pigeon/vladislav-nikonov-yVYaUSwkTOs-unsplash.jpg")
                        , "Pigeon"
                    )
                }
            }
        }
    }
}

val httpClient: HttpClient = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun getImages(): List<BirdImage> {
    val images: List<BirdImage> = httpClient
        .get( "https://sebi.io/demo-image-api/pictures.json")
        .body<List<BirdImage>>()
    return images
}