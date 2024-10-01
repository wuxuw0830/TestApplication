package com.tw.testapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.tw.testapplication.model.GroupInfo
import com.tw.testapplication.ui.theme.TestApplicationTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            TestApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    // MainScreen(viewModel = viewModel)
                    //PatternScreen(viewModel.getPatterns)
                    MainScreen(viewModel, patterns = viewModel.getPatterns)
                }
            }
        }
    }
}


@Composable
fun MainScreen(viewModel: MainViewModel, patterns: List<GroupInfo>) {
    var color = viewModel.color.observeAsState()
    //    var name by mutableStateOf("composable")
    Column(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .background(color.value ?: Color.DarkGray)
        )
        BottomSheetScreen(patterns, onClick = {
            viewModel.color.postValue(it)
        })
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetScreen(patterns: List<GroupInfo>, onClick: (Color) -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(true) }

    //
    Scaffold(
        Modifier.fillMaxSize(0.5f)

    ) { contentPadding ->

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                PatternScreen(patterns, onClick)
            }
        }
    }
}

@Composable
fun PatternScreen(processedPatterns: List<GroupInfo>, onClick: (Color) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        items(processedPatterns) { group ->
            Text(
                text = group.hue,
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleLarge
            )
            Row(
                modifier = Modifier
                    .padding(8.dp)
            ) {

                group.patterns.forEach { pattern ->
                    CircleScreen(color = Color(pattern.color.toInt()), content = "${pattern.level}", onClick)
                }
            }
        }
    }
}

@Composable
fun CircleScreen(color: Color = Color.DarkGray, content: String = "test", onClick: (Color) -> Unit) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .padding(1.dp)
            .clip(CircleShape)
            .background(color)
            .clickable {
                Log.d("[Test]", "click")
                onClick.invoke(color)
            }
    ) {
        Text(text = content, fontSize = 12.sp)
    }

}

