package com.example.draganddrop.data.model

import java.util.*

data class City(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val isSelected: Boolean
)
