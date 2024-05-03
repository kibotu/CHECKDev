package com.haw.takonappcompose.json

import kotlinx.serialization.json.Json


val json: Json by lazy {
    Json {
        ignoreUnknownKeys = true
    }
}

val jsonPrettyPrint: Json by lazy {
    Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }
}
