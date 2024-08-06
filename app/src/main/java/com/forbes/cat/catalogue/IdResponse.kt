package com.forbes.cat.catalogue

data class IdResponse(
    val breeds: List<Breed>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)