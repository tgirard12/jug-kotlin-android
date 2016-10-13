package com.tgirard12.jugkotlinandroid

data class Search(
        val total_count: Int,
        val isIncomplete_results: Boolean,
        val items: List<SearchItem>?
)
