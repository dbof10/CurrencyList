package com.ctech.memoir.di

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.memoir.home.ui.DemoActivity
import io.github.kakaocup.kakao.screen.Screen.Companion.idle
import io.github.kakaocup.kakao.screen.Screen.Companion.onScreen
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class HomeTest {


    @Before
    fun setup() {
        ActivityScenario.launch(DemoActivity::class.java)
    }

    @Test
    fun loadList() {
        onScreen<HomeScreen> {
            btLoad {
                click()
            }
            idle(2000L) // wait for APIs
            recycler {
                firstChild<HomeScreen.ItemCurrency> {
                    isVisible()
                }

                scrollToEnd()
            }
        }

    }

    @Test
    fun search() {
        onScreen<HomeScreen> {
            btLoad {
                click()
            }
            idle(2000L) // wait for APIs

            search {
                typeText("b")
            }
            recycler {
                firstChild<HomeScreen.ItemCurrency> {
                    isVisible()
                    symbol {
                        hasText("BTC")
                    }
                }
            }
        }

    }

    @Test
    fun searchClear() {
        onScreen<HomeScreen> {

            btLoad {
                click()
            }
            idle(2000L) // wait for APIs
            search {
                typeText("b")
            }
            idle(1000L)
            search {
                typeText("")
            }
            recycler {
                firstChild<HomeScreen.ItemCurrency> {
                    isVisible()
                    symbol {
                        hasText("BTC")
                    }
                }
            }
        }

    }

    @Test
    fun sort() {
        onScreen<HomeScreen> {

            btLoad {
                click()
            }
            idle(2000L) // wait for APIs

            btSort {
                click()
            }
            idle(200L)
            recycler {
                firstChild<HomeScreen.ItemCurrency> {
                    isVisible()
                    symbol {
                        hasText("BNB")
                    }
                }
            }
        }

    }
}