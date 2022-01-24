package com.example.movieapp.data.model.cast

data class CastResponse(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)