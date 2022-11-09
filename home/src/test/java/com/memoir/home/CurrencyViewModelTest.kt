package com.memoir.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.memoir.TestCoroutineScopeProvider
import com.memoir.home.model.CurrencyInfo
import com.memoir.home.repo.HomeRepository
import com.memoir.home.viewmodel.CurrencyItemViewModel
import com.memoir.home.viewmodel.CurrencyViewModel

import com.memoir.home.viewmodel.HomeViewState
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class CurrencyViewModelTest {
    @Mock
    lateinit var repository: HomeRepository

    lateinit var viewModel: CurrencyViewModel

    private val scopeProvider = TestCoroutineScopeProvider()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = CurrencyViewModel(repository, scopeProvider)
    }

    @Test
    fun loadAllSuccess() = runTest {
        val list = listOf(
            CurrencyInfo("1", "bitcoin", "btc"),
            CurrencyInfo("2", "ether", "eth")
        )
        whenever(repository.fetch())
            .thenReturn(list)
        viewModel.fetch()
        Assert.assertEquals(
            HomeViewState(
                listOf(
                    CurrencyItemViewModel("1", "bitcoin", "btc", "b"),
                    CurrencyItemViewModel("2", "ether", "eth", "e"),
                )
            ), viewModel.store.state
        )

    }

    @Test
    fun loadAnyError() = runTest {
        val exp = RuntimeException()
        whenever(repository.fetch()).thenThrow(exp)
        viewModel.fetch()

        Assert.assertEquals(HomeViewState(null, null, false, exp), viewModel.store.state)
    }

    @Test
    fun search() = runTest {
        val list = listOf(
            CurrencyInfo("1", "bitcoin", "btc"),
            CurrencyInfo("2", "ether", "eth")
        )
        whenever(repository.fetch())
            .thenReturn(list)
        viewModel.fetch()

        viewModel.search("b")


        Assert.assertEquals(
            HomeViewState(
                listOf(
                    CurrencyItemViewModel(
                        "1",
                        "bitcoin",
                        "btc",
                        "b"
                    ),
                    CurrencyItemViewModel(
                        "2",
                        "ether",
                        "eth",
                        "e"
                    ),
                ),
                listOf(
                    CurrencyItemViewModel(
                        "1",
                        "bitcoin",
                        "btc",
                        "b"
                    )
                )
            ), viewModel.store.state
        )
    }

    @Test
    fun sort() = runTest {
        val list = listOf(
            CurrencyInfo("1", "ether", "eth"),
            CurrencyInfo("2", "cardano", "ada")
        )
        whenever(repository.fetch())
            .thenReturn(list)
        viewModel.fetch()

        viewModel.sort()

        Assert.assertEquals(
            HomeViewState(
                listOf(
                    CurrencyItemViewModel(
                        "2",
                        "cardano",
                        "ada",
                        "a"
                    ),
                    CurrencyItemViewModel(
                        "1",
                        "ether",
                        "eth",
                        "e"
                    ),
                ), null
            ), viewModel.store.state
        )
    }

}