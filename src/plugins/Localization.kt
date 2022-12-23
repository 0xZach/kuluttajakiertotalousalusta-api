package com.turku.plugins

import com.turku.constants.LANGUAGES
import com.github.aymanizz.ktori18n.I18n
import io.ktor.server.application.*
import java.util.*

fun Application.configureLocalization() {
    install(I18n){
        supportedLocales = LANGUAGES.map(Locale::forLanguageTag)
    }
}