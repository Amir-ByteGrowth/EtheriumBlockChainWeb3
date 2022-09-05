package com.example.etheriumblockchainweb3.screens.connecttoetherium

import android.content.Context
import android.view.View
import android.widget.Space
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService

@Composable
fun ConnectToEthereum(modifier: Modifier = Modifier,walletAddress: String) {

    val context = LocalContext.current
    var connected by remember {
        mutableStateOf(false)
    }
    var balance by remember {
        mutableStateOf("")
    }
    var web3 =
        Web3j.build(HttpService("https://mainnet.infura.io/v3/1ec2b66a02b14634b5565759f7faecc1"))
    Surface(
        color = Color.LightGray,
        shape = RoundedCornerShape(10.dp),

        ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Connect to Ethereum Network", style = MaterialTheme.typography.h5)
            Spacer(modifier = modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxWidth()
            ) {

                Column {
                    OutlinedButton(onClick = { connected = connectToEthNetwork(web3, context) }) {
                        Text(text = "Connect to node")
                    }
                    if (connected) {
                        Text(
                            text = "You are connected!",
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = modifier.padding(horizontal = 10.dp)
                        )
                    }
                }

                Spacer(
                    modifier = modifier
                        .height(40.dp)
                        .width(2.dp)
                        .background(color = Color.Red)
                )
                Column {
                    OutlinedButton(onClick = { balance = retrieveBalance(web3, context, walletAddress = walletAddress) }) {
                        Text(text = "Get balance")
                    }
                    if (balance.isNotEmpty())
                        Text(
                            text = "Your balance! $balance",
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = modifier.padding(horizontal = 10.dp)
                        )
                }


            }

        }
    }

}

fun toastAsync(message: String?, context: Context) {

    Toast.makeText(context, message, Toast.LENGTH_LONG).show()


}

fun connectToEthNetwork(web3: Web3j?, context: Context): Boolean {
    var connected = false

    toastAsync(" Now Connecting to Ethereum network", context)
    try {
        //if the client version has an error the user will not gain access if successful the user will get connnected
        val clientVersion = web3!!.web3ClientVersion().sendAsync().get()
        if (!clientVersion.hasError()) {
            connected = true
            toastAsync("Connected!", context)
        } else {
            toastAsync(clientVersion.error.message, context)
        }
    } catch (e: Exception) {
        toastAsync(e.message, context)
    }
    return connected
}

fun retrieveBalance(web3: Web3j?, context: Context, walletAddress: String): String {
    var balance = ""
    //get wallet's balance
    try {
        val balanceWei = web3!!.ethGetBalance(
            walletAddress,
            DefaultBlockParameterName.LATEST
        ).sendAsync()
            .get()

//        txtbalance.text = getString(R.string.your_balance) + balanceWei.balance
        balance = balanceWei.balance.toString()
    } catch (e: java.lang.Exception) {
        toastAsync("balance failed", context)
    }

    return balance
}

@Preview(showBackground = true)
@Composable
fun PreviewConnectionToEthereum() {
    ConnectToEthereum(walletAddress = "")
}