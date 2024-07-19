package com.creamydark.cvsugo.presentation.screens.util

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.creamydark.cvsugo.presentation.screens.util.enums.UserChooserType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TwoSelectableComponent(modifier: Modifier = Modifier,selectedOption: UserChooserType,onSelect:(UserChooserType)->Unit={}) {

    SingleChoiceSegmentedButtonRow(
        modifier = modifier
    ) {
        UserChooserType.entries.forEachIndexed { index, option ->
            SegmentedButton(
                selected = selectedOption == option,
                onClick = { onSelect(option) },
                shape = SegmentedButtonDefaults.itemShape(index = index, count = UserChooserType.entries.size),
                label = {
                    Text(option.name)
                }
            )
        }
    }
}

