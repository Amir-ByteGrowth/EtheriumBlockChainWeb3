package com.example.etheriumblockchainweb3.screens.createwallet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.etheriumblockchainweb3.screens.connecttoetherium.ConnectToEthereum
import com.example.etheriumblockchainweb3.ui.theme.Teal200

@Composable
fun CreateEthereumWallet(modifier: Modifier = Modifier) {

    var isWalletAddressAvailable by remember {
        mutableStateOf(true)
    }

    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            text = "Create Ethereum Wallet",
            style = MaterialTheme.typography.h5,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.height(10.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text(text = "Wallet Path") },
            placeholder = { Text(text = "Enter wallet name") }, modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = modifier.height(5.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(text = "Password") },
            placeholder = { Text(text = "Enter password to wallet") },
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = modifier.height(25.dp))
        Button(
            onClick = {}, modifier = modifier
                .wrapContentSize(align = Alignment.BottomEnd)
                .height(40.dp)
        ) {
            Text(text = "Create Wallet")
        }



        Divider(
            color = Color.Red,
            thickness = 1.dp,
            modifier = modifier.padding(vertical = 20.dp)
        )

        if (isWalletAddressAvailable) {
            Text(
                text = "Your Wallet Address:",
                style = MaterialTheme.typography.h6.copy(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 7.dp)
            )

            Text(text = "0xkkaskdahjshdassadnsajkdnas", modifier = modifier.fillMaxWidth())
            Divider(
                color = Color.Red,
                thickness = 1.dp,
                modifier = modifier.padding(vertical = 20.dp)
            )
            ConnectToEthereum()

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEthereumWallet() {
    CreateEthereumWallet()
}