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
import com.pk.palace.model.Image
import com.pk.palace.model.Note
import com.pk.palace.ui.NoteViewModel

@Composable
fun QuoteDetailScreen(noteId: String, viewModel: NoteViewModel = viewModel()) {

    val bite = viewModel.getNoteByIdStateFlow(noteId.toInt()).collectAsState().value

    if (bite == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    when(bite){
        is Note -> NoteScreenComposable(bite)
        is Image -> ImageScreenComposable(bite)
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
fun NoteScreenComposable(note: Note){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = note.title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = note.description,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(36.dp))

        // Add more fields: video, image, createdAt, tags, etc.
    }
}