package com.example.etheriumblockchainweb3.screens.sendbalance

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CreateSendBalance(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    Surface(
        color = Color.LightGray,
        shape = RoundedCornerShape(10.dp),

        ) {

        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Send Ethereum",
                style = MaterialTheme.typography.h5,
                modifier = modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = modifier.fillMaxWidth()  .bringIntoViewRequester(bringIntoViewRequester).onFocusEvent { focusState ->
                    if (focusState.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
                value = "",
                onValueChange = {},
                label = { Text(text = "Amount") },
                placeholder = {
                    Text(
                        text = "Please Enter Ethereum amount"
                    )
                }

            )
            Spacer(modifier = modifier.height(20.dp))
            Button(onClick = { }) {
                Text(text = "Send")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSendBalance() {
    CreateSendBalance()
}