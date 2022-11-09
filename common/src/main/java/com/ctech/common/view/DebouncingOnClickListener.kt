package com.ctech.common.view

import android.os.Handler
import android.os.Looper
import android.view.View


class DebouncingOnClickListener(
    private val intervalMillis: Long,
    private val doClick: ((View) -> Unit)
) : View.OnClickListener {
    private val MAIN: Handler = Handler(Looper.getMainLooper())

    private var enabled = true
    private val ENABLE_AGAIN = { enabled = true }

    override fun onClick(v: View) {
        if (enabled) {
            enabled = false
            MAIN.postDelayed(ENABLE_AGAIN, intervalMillis)
            doClick(v)
        }
    }

}