package com.pk.palace.ui.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pk.palace.model.Gif
import com.pk.palace.model.Image
import com.pk.palace.model.Text
import com.pk.palace.ui.ExerciseViewModel
import com.pk.palace.ui.ImageLoaderProvider
import kotlinx.coroutines.delay

@Composable
fun ExerciseDetailScreen(exerciseId: String, viewModel: ExerciseViewModel = viewModel()) {

    val exercise = viewModel.getNoteByIdStateFlow(exerciseId.toInt()).collectAsState().value

    if (exercise == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100) // Small delay to make the animation noticeable
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it / 2 }, animationSpec = tween(500))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = exercise.title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                exercise.targetGroups.forEach { targetGroup ->
                    Image(
                        painter = painterResource(id = targetGroup.toDrawable()),
                        contentDescription = targetGroup.name,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = DividerDefaults.Thickness,
                color = DividerDefaults.color
            )
            Spacer(modifier = Modifier.height(24.dp))

            when (val content = exercise.content) {
                is Text -> ExerciseScreenComposable(content)
                is Image -> ImageScreenComposable(content)
                is Gif -> GifScreenComposable(content)
            }
        }
    }
}

@Composable
fun GifScreenComposable(gif: Gif) {
    val context = LocalContext.current
    val imageLoader = ImageLoaderProvider.get(context)

    Column(modifier = Modifier.fillMaxWidth()) {
        if (gif.description.isNotBlank()) {
            Text(
                text = gif.description,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Card(shape = RoundedCornerShape(16.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(gif.url)
                    .crossfade(true)
                    .build(),
                imageLoader = imageLoader,
                contentDescription = "Exercise GIF",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun ImageScreenComposable(image: Image) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (image.description.isNotBlank()) {
            Text(
                text = image.description,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Card(shape = RoundedCornerShape(16.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image.url)
                    .crossfade(true)
                    .build(),
                contentDescription = "Exercise Image",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun ExerciseScreenComposable(note: Text) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
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
}
