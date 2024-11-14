package io.mindset.jagamental.ui.component.journal

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.mindset.jagamental.R

@Composable
fun AddJournalButton(onclick: () -> Unit, modifier: Modifier = Modifier, isExpanded: Boolean = false) {
    ExtendedFloatingActionButton(
        onClick = onclick,
        icon = {
            Icon(painter = painterResource(R.drawable.pencil), contentDescription = null, Modifier.size(16.dp))
        },
        text = {
            Text(text = "Tulis Jurnal Baru")
        },
        modifier = modifier
            .padding(16.dp)
            .height(48.dp),
        containerColor = Color(0xFF194A47),
        expanded = isExpanded,
    )
}