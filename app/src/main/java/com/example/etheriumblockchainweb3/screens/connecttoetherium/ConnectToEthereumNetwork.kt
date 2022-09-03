package com.example.etheriumblockchainweb3.screens.connecttoetherium

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConnectToEthereum(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Connect to Ethereum Network", style = MaterialTheme.typography.h5)
        Spacer(modifier = modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {

            Column {
                OutlinedButton(onClick = { }) {
                    Text(text = "Connect to node")
                }
                Text(
                    text = "You are connected!",
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = modifier.padding(horizontal = 10.dp)
                )
            }

            Spacer(
                modifier = modifier
                    .height(40.dp)
                    .width(2.dp)
                    .background(color = Color.Red)
            )
            Column {
                OutlinedButton(onClick = { }) {
                    Text(text = "Get balance")
                }
                Text(
                    text = "Your balance!",
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = modifier.padding(horizontal = 10.dp)
                )
            }


        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConnectionToEthereum() {
    ConnectToEthereum()
}