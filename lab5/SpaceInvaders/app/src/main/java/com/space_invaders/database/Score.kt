package com.space_invaders.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Score (
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "score") var score: Int,
)