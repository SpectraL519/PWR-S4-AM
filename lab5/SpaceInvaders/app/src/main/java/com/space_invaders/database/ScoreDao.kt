package com.space_invaders.database

import androidx.room.*

@Dao
interface ScoreDao {
    @Query("SELECT EXISTS(SELECT * FROM score WHERE id = :id)")
    fun idExists(id: Int) : Boolean

    @Query("SELECT * FROM score WHERE id == 0")
    fun getHighScore(): Score

    @Insert
    fun insert(score: Score)

    @Update
    fun update(score: Score)
}