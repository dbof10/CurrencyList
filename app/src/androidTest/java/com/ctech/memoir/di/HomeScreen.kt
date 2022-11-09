package com.ctech.memoir.di

import android.view.View
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.screen.Screen
import org.hamcrest.Matcher
import com.memoir.home.R
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KTextView

class HomeScreen : Screen<HomeScreen>() {


    class ItemCurrency(parent: Matcher<View>) : KRecyclerItem<ItemCurrency>(parent) {
        val symbol: KTextView = KTextView(parent) {
            withId(R.id.tvSymbol)
        }
    }

    val recycler: KRecyclerView = KRecyclerView({
        withId(R.id.rvCurrency)
    }, itemTypeBuilder = {
        itemType(::ItemCurrency)
    })

    val search : KEditText = KEditText {
        withId(R.id.etSearch)
    }

    val btLoad : KEditText = KEditText {
        withId(R.id.btLoad)
    }

    val btSort : KEditText = KEditText {
        withId(R.id.btSort)
    }
}