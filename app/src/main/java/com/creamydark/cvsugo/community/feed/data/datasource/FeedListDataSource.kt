package com.creamydark.cvsugo.community.feed.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.creamydark.cvsugo.community.feed.domain.data.PostData
import com.creamydark.cvsugo.core.util.POSTS
import com.creamydark.cvsugo.core.util.USERS
import com.creamydark.cvsugo.googleAuth.domain.dataclass.UserData
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FeedListDataSource: PagingSource<QuerySnapshot, PostData>() {

    val firestore = Firebase.firestore

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, PostData> {
        return try {
            // Determine the starting point for the query
            val query = firestore.collection(POSTS)
                .limit(params.loadSize.toLong())
                .orderBy("theTimestamp",Query.Direction.DESCENDING)
                .apply {
                    // Start after the last document snapshot if it exists
                    params.key?.let { startAfter(it) }
                }

            // Fetch the current page of data
            val currentPage = query.get().await()

            // Determine the last document snapshot for the next page query
            val lastDocumentSnapshot = currentPage.documents.lastOrNull()

            // Prepare the next page query if there's a last document snapshot
            val nextPage = lastDocumentSnapshot?.let {
                firestore.collection(POSTS)
                    .limit(params.loadSize.toLong())
                    .orderBy("theTimestamp",Query.Direction.DESCENDING)

                    .startAfter(it)

                    .get()
                    .await()
            }

            val data = currentPage.toObjects(PostData::class.java)

            val updatedData = data.map { postData ->
                val userDocument = firestore.collection(USERS).document(postData.userId).get().await()
                val userData = userDocument.toObject(UserData::class.java)
                postData.copy(userData = userData ?: UserData())
            }

            LoadResult.Page(
                data = updatedData,
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<QuerySnapshot, PostData>): QuerySnapshot? {
        return null
    }

}