package com.example.cleanarchitecture.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cleanarchitecture.presentation.theme.CleanArchitectureTheme

class MainActivity : ComponentActivity() {


    val imageViewModel: ImageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CleanArchitectureTheme {
                val uiState by imageViewModel.uiState.collectAsState()
                var query by rememberSaveable { mutableStateOf("") }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Column(modifier = Modifier.padding(innerPadding)) {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Search...") },
                            value = query,
                            onValueChange = {
                                query = it
                                imageViewModel.updateQuery(query)
                            }
                        )

                        if (uiState.isLoading) {

                            Box(modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                                ){
                                CircularProgressIndicator()
                            }
                        }

                        if(uiState.error.isNotEmpty()){
                            Box( modifier = Modifier.padding(innerPadding).fillMaxSize(), contentAlignment = Alignment.Center){
                                Text(text = uiState.error)

                            }

                        }
                    uiState.data?.let {
                        LazyColumn( modifier = Modifier.padding(innerPadding).fillMaxSize()) {

                            items(it){

                                AsyncImage(
                                    model = it.imageUrl,
                                    modifier = Modifier.padding(12.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .fillMaxSize()
                                        .height(300.dp),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop

                                )
                            }

                        }
                    }


                    }
                }
            }
        }
    }
}

