package com.example.etheriumblockchainweb3.screens.createwallet

import android.content.Context
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.etheriumblockchainweb3.screens.connecttoetherium.ConnectToEthereum
import com.example.etheriumblockchainweb3.screens.sendbalance.CreateSendBalance
import com.example.etheriumblockchainweb3.ui.theme.Teal200
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import java.io.File
import java.security.Provider
import java.security.Security

var credentials: Credentials? = null

@Composable
fun CreateEthereumWallet(modifier: Modifier = Modifier) {

    var web3: Web3j? = null
    var file: File? = null
    var Walletname: String? = null
//    var credentials: Credentials? = null
    var context = LocalContext.current

    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(policy)
//enter your own infura api key below
    //enter your own infura api key below
    web3 = Web3j.build(HttpService("1ec2b66a02b14634b5565759f7faecc1"))

    setupBouncyCastle()

    var etheriumwalletPath = "";

    file = File(context.filesDir.toString() + etheriumwalletPath);// the etherium wallet location
    //create the directory if it does not exist
    if (!file.mkdirs()) {
        file.mkdirs();
    } else {
        Toast.makeText(
            context, "Directory already created",
            Toast.LENGTH_LONG
        ).show();

    }


    var isWalletAddressAvailable by remember {
        mutableStateOf(false)
    }
    var walletName by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var walledAddress by remember {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Create Ethereum Wallet",
            style = MaterialTheme.typography.h5,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.height(10.dp))
        OutlinedTextField(
            value = walletName,
            onValueChange = { walletName = it },
            label = { Text(text = "Wallet Path") },
            placeholder = { Text(text = "Enter wallet name") },
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = modifier.height(5.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            placeholder = { Text(text = "Enter password to wallet") },
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = modifier.height(25.dp))
        Button(
            onClick = {
                walledAddress = createWallet(
                    walletName = walletName,
                    password = password,
                    context = context,
                    file = file
                )
            }, modifier = modifier
                .wrapContentSize(align = Alignment.BottomEnd)
                .height(40.dp)
        ) {
            Text(text = "Create Wallet")
        }



        Divider(
            color = Color.Transparent,
            thickness = 1.dp,
            modifier = modifier.padding(vertical = 10.dp)
        )
        Log.d("walletAddress", walledAddress)

        isWalletAddressAvailable = walledAddress.isNotEmpty()

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

            Text(text = walledAddress, modifier = modifier.fillMaxWidth())
            Divider(
                color = Color.Transparent,
                thickness = 1.dp,
                modifier = modifier.padding(vertical = 20.dp)
            )
            ConnectToEthereum(walletAddress = walledAddress)

            Divider(
                color = Color.Transparent,
                thickness = 1.dp,
                modifier = modifier.padding(vertical = 20.dp)
            )
            CreateSendBalance(web3 = web3, credentials= credentials!!)

        }
    }
}


fun toastAsync(message: String?, context: Context) {

    Toast.makeText(context, message, Toast.LENGTH_LONG).show()


}

//set up the security provider
private fun setupBouncyCastle() {
    val provider: Provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)
        ?: // Web3j will set up a provider  when it's used for the first time.
        return
    if (provider == BouncyCastleProvider::class.java) {
        return
    }
    //There is a possibility  the bouncy castle registered by android may not have all ciphers
    //so we  substitute with the one bundled in the app.
    Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
    Security.insertProviderAt(BouncyCastleProvider(), 1)
}

fun createWallet(password: String, walletName: String, context: Context, file: File): String {

    try {
        // generating the etherium wallet
        var Walletname = WalletUtils.generateLightNewWalletFile(password, file)
        toastAsync("Wallet generated wallet name is ", context)
        credentials = WalletUtils.loadCredentials(password, "$file/$Walletname")
        return if (credentials != null) {
            credentials!!.address
        } else {
            ""
        }
//            txtaddress.setText(getString(R.string.your_address) + credentials?.address)
    } catch (e: Exception) {
        toastAsync("failed", context)
    }
    return ""
}


@Preview(showBackground = true)
@Composable
fun PreviewEthereumWallet() {
    CreateEthereumWallet()
}