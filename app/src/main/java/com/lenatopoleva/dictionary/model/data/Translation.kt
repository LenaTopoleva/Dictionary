package com.lenatopoleva.dictionary.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Translation(
    @Expose
    @field:SerializedName("text")
    val translation: String?)
