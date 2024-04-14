package com.motaqi.elmehdi.moviesapplication.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.motaqi.elmehdi.moviesapplication.extension.toImageUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesListingScreen(){
    Scaffold(
        topBar = {
            TopAppBar(title = { 
                Text(text = "MoviesByMovieDB")
            })
        }
    ) {
        val theMovieListingViewModel: TheMovieListingViewModel = hiltViewModel()
        val moviesItems = theMovieListingViewModel.theMovieListingState.collectAsLazyPagingItems()
        Column(
            modifier = Modifier.padding(paddingValues = it)
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onInputChange = {
                theMovieListingViewModel.searchEvent(it)
            })
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                content = {
                    items(count = moviesItems.itemCount){

                        MovieItem(
                            imageUrl = moviesItems[it]?.poster_path?.toImageUrl()!!,
                            title = moviesItems[it]?.name!!,
                            voteAverage = moviesItems[it]?.vote_average.toString(),
                            voteCount = moviesItems[it]?.vote_count.toString()
                        )

                    }
                    moviesItems.apply {
                        when{
                            loadState.refresh is LoadState.Loading -> {
                                item {
                                    CircularProgressIndicator(progress = 0.5F)
                                }
                            }

                            loadState.refresh is LoadState.Error -> {

                            }

                            loadState.append is LoadState.Loading -> {
                                items(moviesItems.itemCount){
                                    MovieItem(
                                        imageUrl = moviesItems[it]?.poster_path?.toImageUrl()!!,
                                        title = moviesItems[it]?.name!!,
                                        voteAverage = moviesItems[it]?.vote_average.toString(),
                                        voteCount = moviesItems[it]?.vote_count.toString()
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieItem(imageUrl: String?, voteAverage: String, voteCount: String, title: String){
    Card(
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black,
        ),onClick = {  }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
        ) {
            AsyncImage(
                modifier = Modifier.wrapContentSize(align = Alignment.Center),
                model = imageUrl,
                contentDescription = null)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(50F)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null, tint = Color.Yellow
                    )
                    Text(text = voteAverage, color = Color.White)
                }
                Row(
                    modifier = Modifier.weight(50F)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null, tint = Color.White
                    )
                    Text(text = voteCount, color = Color.White)
                }
            }
            Text(text = title, color = Color.White)
        }
    }

}

@Composable
fun SearchBar(modifier: Modifier = Modifier, onInputChange: (String) -> Unit){
    val inPutValue = remember {
        mutableStateOf("")
    }
    TextField(
        modifier = modifier,
        value = inPutValue.value,
        label = {
                Text(text = "Search")
        },
        onValueChange = {
        inPutValue.value = it
        onInputChange(it)
    })
}