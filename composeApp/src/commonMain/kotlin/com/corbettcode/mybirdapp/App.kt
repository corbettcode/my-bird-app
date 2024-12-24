package com.corbettcode.mybirdapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.corbettcode.compose.component.DropdownList
import com.corbettcode.mybirdapp.model.BirdImage
import com.corbettcode.mybirdapp.model.BirdsViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
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
fun BirdAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(primary = Color.Black),
        shapes = MaterialTheme.shapes.copy(
            small = AbsoluteCutCornerShape(0.dp),
            medium = AbsoluteCutCornerShape(0.dp),
            large = AbsoluteCutCornerShape(0.dp),
        )
    ) {
        content()
    }
}

@Composable
@Preview
fun App() {
    BirdAppTheme {
        val birdsViewModel = getViewModel(Unit, viewModelFactory { BirdsViewModel() })
        BirdsPage(birdsViewModel)
    }
}

@Composable
fun BirdsPage(viewModel: BirdsViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    val buttonModifier = Modifier.width(100.dp)
    var selectedIndex by rememberSaveable { mutableStateOf(0) }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            Modifier.fillMaxWidth().padding(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            DropdownList(
                itemList = uiState.categories,
                selectedIndex = 0,
                modifier = buttonModifier,
                onItemClick = {viewModel.selectCategory(it)},
            )
//            for(category in uiState.categories) {
//                Button(onClick = {
//                    viewModel.selectCategory(category)
//                },
//                    modifier = Modifier.aspectRatio(1.0f).fillMaxSize().weight(1.0f),
//                    elevation = ButtonDefaults.elevation(
//                        defaultElevation = 0.dp,
//                        focusedElevation = 0.dp
//                    )
//                ) {
//                    Text(category)
//                }
//            }
        }

        AnimatedVisibility(uiState.selectedImages.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed( 2 ),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp),
                content = {
                    items(uiState.selectedImages) {
                        BirdImageCell(it)
                    }
                }
            )
        }
    }
}

@Composable
fun BirdImageCell(image: BirdImage) {
    KamelImage(
        asyncPainterResource( "https://sebi.io/demo-image-api/${image.path}" ),
        "${image.category} by ${image.author}",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth().aspectRatio(1.0f)
    )
}
