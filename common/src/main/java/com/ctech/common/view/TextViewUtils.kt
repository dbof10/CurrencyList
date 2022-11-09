package com.ctech.common.view

import android.view.View
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


fun View.onClicked(debounce: Long = 1000L): Flow<View> {

    return callbackFlow {

        setOnClickListener(DebouncingOnClickListener(debounce) {
            trySend(it)
        })

        awaitClose {
            cancel()
        }
    }

}

fun TextView.afterTextChange(): Flow<String> {

    return callbackFlow {

        addTextChangedListener(afterTextChanged = {
            trySend(it!!.toString())
        })

        awaitClose {
            cancel()
        }
    }

}
