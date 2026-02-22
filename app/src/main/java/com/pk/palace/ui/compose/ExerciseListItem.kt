package com.pk.palace.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pk.palace.model.ExerciseCategory
import com.pk.palace.model.TargetGroup


@Composable
fun ExerciseListItem(text: String, categories: List<ExerciseCategory>, targetGroups: List<TargetGroup>, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (categories.isEmpty()) {
                    Icon(
                        imageVector = Icons.Default.FitnessCenter,
                        contentDescription = "Exercise Category Icon",
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    categories.forEach { category ->
                        Icon(
                            imageVector = category.toIcon(),
                            contentDescription = "Exercise Category Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            Row {
                targetGroups.forEach {
                    Image(
                        painter = painterResource(id = it.toDrawable()),
                        contentDescription = "Target Group Icon",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}
