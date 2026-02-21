package com.pk.palace.ui.compose

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pk.palace.model.Gif
import com.pk.palace.model.Image
import com.pk.palace.model.Text
import com.pk.palace.ui.ExerciseViewModel
import com.pk.palace.ui.ImageLoaderProvider

@Composable
fun ExerciseDetailScreen(exerciseId: String, viewModel: ExerciseViewModel = viewModel()) {

    val bite = viewModel.getNoteByIdStateFlow(exerciseId.toInt()).collectAsState().value

    if (bite == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Log.i("QuoteDetailScreen", "Bite: ${bite.javaClass.simpleName}")
    when(bite.content){
        is Text -> ExerciseScreenComposable(bite.content)
        is Image -> ImageScreenComposable(bite.content)
        is Gif -> GifScreenComposable(bite.content)
    }
}

@Composable
fun GifScreenComposable(gif: Gif) {
    val context = LocalContext.current
    val imageLoader = ImageLoaderProvider.get(context)

    Log.i("GifScreen", "Gif: $gif")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = gif.title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = gif.description,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(36.dp))
        Log.i("GifScreen", "Fetch gif with url ${gif.url}")
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(gif.url)
                .crossfade(true)
                .build(),
            imageLoader = imageLoader,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun ImageScreenComposable(image: Image){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = image.title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = image.description,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(36.dp))
        Log.i("ImageScreen", "Fetch image with url ${image.url}")
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image.url)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ExerciseScreenComposable(note: Text){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = note.title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Instructions",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = note.description,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Sets: 3, Reps: 12",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
