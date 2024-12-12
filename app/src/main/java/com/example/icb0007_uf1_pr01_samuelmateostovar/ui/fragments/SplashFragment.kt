package com.example.icb0007_uf1_pr01_samuelmateostovar.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import androidx.compose.ui.tooling.preview.Preview
import com.example.icb0007_uf1_pr01_samuelmateostovar.R

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val navController = findNavController()
        return ComposeView(requireContext()).apply {
            setContent {
                SplashComposable(navController)
            }
        }
    }
}

@Composable
fun SplashComposable(navController: NavController) {

    LaunchedEffect(key1 = true) {
        delay(4000)
        navController.navigate(R.id.action_splashFragment_to_loginFragment)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        val config = LocalConfiguration.current
        val isLandscape = config.orientation == Configuration.ORIENTATION_LANDSCAPE

        fun applyImageSize(portraitSize : Float, landscapeSize : Float): Dp {
            val width = config.screenWidthDp.dp
            return if (isLandscape) width * landscapeSize else width * portraitSize
        }

        val sizeLogoCohete = applyImageSize(1f,0.3f)
        val sizeSpacex = applyImageSize(0.25f, 0.1f)

        Image(
            painter = painterResource(id = R.drawable.logo_cohete),
            contentDescription = "LogoCohete",
            modifier = Modifier
                .align(Alignment.Center)
                .size(sizeLogoCohete)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            Text(
                "POWERED BY",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = sizeSpacex / 3)
                    .offset(x = (-20).dp)
            )
            Image(
                painter = painterResource(id = R.drawable.spacex),
                contentDescription = "SpaceX",
                modifier = Modifier
                    .size(sizeSpacex)
                    .offset(x = 25.dp)
            )
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    val previewNavController = rememberNavController()
    SplashComposable(previewNavController)
}
