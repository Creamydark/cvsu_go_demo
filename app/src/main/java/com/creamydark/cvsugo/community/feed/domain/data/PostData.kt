package com.creamydark.cvsugo.community.feed.domain.data

import com.creamydark.cvsugo.community.feed.domain.PostVisibilityOptions
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class PostData(
    @DocumentId val postId: String = "",
    val title: String = "",
    val content:String = "",
    val authorId: String = "",
    @ServerTimestamp val theTimestamp: Date = Date(),
    val visibility: PostVisibilityOptions = PostVisibilityOptions.PUBLIC,
    val attachments: List<String> = emptyList()
)