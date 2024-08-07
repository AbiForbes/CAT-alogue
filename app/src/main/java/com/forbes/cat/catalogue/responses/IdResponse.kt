package com.forbes.cat.catalogue.responses

data class IdResponse(
    val breeds: List<Breed>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)