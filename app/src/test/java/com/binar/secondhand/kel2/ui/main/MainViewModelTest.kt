package com.binar.secondhand.kel2.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.secondhand.kel2.rule.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = MainViewModel()
    }

    @Test
    fun setNotificationCount()= runTest{

        viewModel.setNotificationCount(1)

        kotlin.test.assertNotNull(viewModel.notificationCount)
        kotlin.test.assertEquals(viewModel.notificationCount.value, 1)
    }
}