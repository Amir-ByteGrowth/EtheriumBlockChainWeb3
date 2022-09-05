package com.example.etheriumblockchainweb3.screens.sendbalance

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.tx.Transfer
import org.web3j.utils.Convert
import java.math.BigDecimal

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CreateSendBalance(web3: Web3j, credentials: Credentials, modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val context = LocalContext.current
    var balance by remember {
        mutableStateOf("")
    }
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
                modifier = modifier
                    .fillMaxWidth()
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .onFocusEvent { focusState ->
                        if (focusState.isFocused) {
                            coroutineScope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    },
                value = balance,
                onValueChange = { balance = it },
                label = { Text(text = "Amount") },
                placeholder = {
                    Text(
                        text = "Please Enter Ethereum amount"
                    )
                }

            )
            Spacer(modifier = modifier.height(20.dp))
            Button(onClick = {
                if (balance.isNotEmpty()) {
                    val amount = balance.toDouble()
                    makeTransaction(context, web3, credentials,amount)
                }
            }) {
                Text(text = "Send")
            }
        }
    }
}


@Throws(java.lang.Exception::class)
fun makeTransaction(context: Context, web3: Web3j, credentials: Credentials, value: Double) {
    // get the amout of eth value the user wants to send


    try {
        val receipt = Transfer.sendFunds(
            web3,
            credentials,
            "0x2a21e3d18120f221efbc7e155458b5e8af2fba85",
            BigDecimal.valueOf(value),
            Convert.Unit.ETHER
        ).send()
        Toast.makeText(
            context,
            "Transaction successful: " + receipt.transactionHash,
            Toast.LENGTH_LONG
        ).show()
    } catch (e: java.lang.Exception) {
        toastAsync("low balance",context)
    }
}

fun toastAsync(message: String?, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

@Preview(showBackground = true)
@Composable
fun PreviewSendBalance() {
//    CreateSendBalance()
}