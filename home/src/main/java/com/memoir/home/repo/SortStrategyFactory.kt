package com.memoir.home.repo

import com.memoir.home.model.SortBy

object SortStrategyFactory {
    
    fun get(by: SortBy) : SortStrategy {

      return  when (by) {
            SortBy.Name -> {
                SortByName()
            }
            SortBy.MarketCap -> {
                SortByName()
    
            }
            else -> {
                SortByName()
            }
        }
    }
    
}