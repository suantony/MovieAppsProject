package com.example.suantony.movieappsproject.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
	val id: Int? = null,
	val page: Int? = null,
	@field:SerializedName("total_pages")
	val totalPages: Int,
	val results: List<ReviewItem>,
	val totalResults: Int? = null
)

data class AuthorDetails(
	val avatarPath: String? = null,
	val name: String? = null,
	val rating: Int? = null,
	val username: String? = null
)

data class ReviewItem(
	val authorDetails: AuthorDetails? = null,
	val updatedAt: String? = null,
	val author: String? = null,
	val createdAt: String? = null,
	val id: String? = null,
	val content: String? = null,
	val url: String? = null
)

