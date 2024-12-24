package com.corbettcode.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

/**
 * A drop down list component.
 *
 *
 *
 * @property itemList list of the possible selections.
 * @property selectedIndex state variable.
 * @property modifier parameter will allow configuration of some of the basic button appearances such as width and height.
 * @property onItemClick lambda takes an Integer as its parameter and return nothing.
 *
 * @author inspired by the work of Itsuki
 * @author <a href="mailto:paul@corbettcode.com">Paul Corbett <paul@corbettcode.com></a>
 */
@Composable
fun DropdownList(
    itemList: List<String>,
    selectedIndex: Int,
    modifier: Modifier,
    onItemClick: (Int) -> Unit
) {
    var showDropdown by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column (
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // button
        Box(
            modifier = modifier
                .background(MaterialTheme.colors.secondary)
                .clickable { showDropdown = true },
//                .clickable { showDropdown = !showDropdown },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (itemList.isNotEmpty()) itemList[selectedIndex]  else "",
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .padding(3.dp)
            )
        }

        // drop down list
        Box() {
            if (showDropdown == true) {
                Popup(
                    alignment = Alignment.TopCenter,
                    properties = PopupProperties(
    //                    excludeFromSystemGesture = true,
                    ),
                    // to dismiss on click outside
                ) {
                    Column(
                        modifier = modifier
                            .heightIn(max = 90.dp)
                            .verticalScroll(state = scrollState)
                            .border(width = 1.dp, color = Color.Gray),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemList.onEachIndexed { index, item ->
                            if (index != 0) {
                                Divider(thickness = 1.dp, color = Color.LightGray)
                            }
                            Box(
                                modifier = Modifier
                                    .background( if (index == selectedIndex)
                                        MaterialTheme.colors.secondary
                                        else MaterialTheme.colors.secondaryVariant
                                    )
                                    .clickable {
                                        onItemClick(index)
                                        showDropdown = !showDropdown
                                    }
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = item,
                                    color = MaterialTheme.colors.onSecondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}