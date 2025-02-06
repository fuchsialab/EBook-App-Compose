package com.fuchsia.ebookapp.presentation.nav

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    object Home : Routes()

    @Serializable
    object BookByCategory : Routes()

    @Serializable
    data class BookByCategoryScreen(val category: String)

    @Serializable
    data class PdfViewScreen(val pdfUrl: String)
}