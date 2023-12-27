package com.example.finnishmilitaryranksandroid

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finnishmilitaryranksandroid.data.Rank
import com.example.finnishmilitaryranksandroid.ui.theme.FinnishMilitaryRanksAndroidTheme

private val data = listOf<Rank>(
    Rank(1, R.drawable.upseerioppilas, "Upseerioppilas"),
    Rank(2, R.drawable.alikersantti, "Alikersantti"),
    Rank(3, R.drawable.korpraali, "Korpraali"),
    Rank(4, R.drawable.aliupseerioppilas, "Aliupseerioppilas"),
    Rank(5, R.drawable.sotamies, "Sotamies"),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val (initialLeftRank, initialRightRank) = selectRandomRanks()

        var rankLeft by mutableStateOf<Rank>(initialLeftRank)
        var rankRight by mutableStateOf<Rank>(initialRightRank)

        setContent {
            FinnishMilitaryRanksAndroidTheme {
               MainScreenContent(
                   rankLeft = rankLeft,
                   rankRight = rankRight,
                   onUpdateRanks = { newRankLeft, newRankRight ->
                       rankLeft = newRankLeft
                       rankRight = newRankRight
                   }
               )
            }
        }
    }
}

@Composable
fun MainScreenContent(
    rankLeft: Rank,
    rankRight: Rank,
    onUpdateRanks: (Rank, Rank) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF2e3b11))
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.puolustusvoimat),
                contentDescription = "Puolustusvoimat",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 16.dp)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
            ) {
                Text("Puolustusvoimat",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif)
                Text("Kumpi on korkeampi?",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(32.dp)
                    .clickable {
                        checkSelected(
                            context = context,
                            selected = rankLeft.id,
                            other = rankRight.id
                        )

                        val (newLeftRank, newRightRank) = selectRandomRanks()
                        onUpdateRanks(newLeftRank, newRightRank)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = rankLeft.image),
                    contentDescription = rankLeft.text,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )

                Text(rankLeft.text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(32.dp)
                    .clickable {
                        checkSelected(
                            context = context,
                            selected = rankRight.id,
                            other = rankLeft.id
                        )

                        val (newLeftRank, newRightRank) = selectRandomRanks()
                        onUpdateRanks(newLeftRank, newRightRank)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = rankRight.image),
                    contentDescription = rankRight.text,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                )
                Text(rankRight.text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            Button(
                onClick = {
                    val (newLeftRank, newRightRank) = selectRandomRanks()
                    onUpdateRanks(newLeftRank, newRightRank)
                },
                modifier = Modifier
                    .width(160.dp)
                    .padding(32.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF2e3b11))
            ) {
                Text("Skip",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif)
            }
        }
    }
}

private fun selectRandomRanks(): Pair<Rank, Rank> {
    val shuffledRankData = data.shuffled()

    val rank1 = shuffledRankData[0]
    val rank2 = shuffledRankData.first { it != rank1}

    return Pair(rank1, rank2)
}

private fun checkSelected(context: Context, selected: Int, other: Int) {
    if (selected < other) {
        Toast.makeText(context, "Oikein", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(context, "Väärin", Toast.LENGTH_SHORT).show()
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    FinnishMilitaryRanksAndroidTheme {
        MainScreenContent(
            rankLeft = Rank(id = 1, R.drawable.upseerioppilas, "Upseerioppilas"),
            rankRight = Rank(id = 2, R.drawable.alikersantti, "Alikersantti"),
            onUpdateRanks = { _, _ ->
            }
        )
    }
}
