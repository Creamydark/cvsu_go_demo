package com.creamydark.cvsugo.community.feed.domain.repository

import android.net.Uri
import com.creamydark.cvsugo.community.feed.domain.data.PostData
import kotlinx.coroutines.flow.Flow

interface FeedRepository{
    suspend fun createPost(data: PostData,images:List<Uri>):Flow<Result<String>>
    suspend fun deletePost(data: PostData):Flow<Result<String>>
    suspend fun getPosts():Flow<Result<List<PostData>>>
    suspend fun getSpecificPost(postId:String):Flow<Result<PostData>>
    suspend fun updatePost(data: PostData):Flow<Result<String>>
}