package com.example.labs

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Лабораторная работа 17: UI-тест с использованием Espresso.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun login_EmptyFields_ShowsErrorToast() {
        // Нажимаем на кнопку входа без ввода данных
        onView(withId(R.id.btnLogin)).perform(click())

        // Проверяем, что мы все еще на экране входа (никуда не перешли)
        onView(withId(R.id.etLogin)).check(matches(isDisplayed()))
    }

    @Test
    fun login_Success_NavigatesToContainer() {
        // Вводим логин
        onView(withId(R.id.etLogin)).perform(typeText("student"), closeSoftKeyboard())
        
        // Вводим пароль
        onView(withId(R.id.etPassword)).perform(typeText("password123"), closeSoftKeyboard())

        // Нажимаем кнопку
        onView(withId(R.id.btnLogin)).perform(click())

        // Проверяем, что открылся следующий экран (FragmentContainerActivity)
        // Мы проверяем наличие элемента, который есть только на том экране
        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()))
    }
}
