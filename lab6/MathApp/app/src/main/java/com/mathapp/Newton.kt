package com.mathapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Newton {
    @GET("{operation}/{expression}")
    fun calculate(
        @Path("operation", encoded = true) operation: String,
        @Path("expression", encoded = true) expression: String
    ) : Call<NewtonDO>
}

data class NewtonDO(
    var operation: String,
    var expression: String,
    var result: String
)
