package com.example.labs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * Фрагмент с деталями пользователя.
 */
class DetailsFragment : Fragment() {

    private val TAG = "FragmentLifecycle"

    companion object {
        private const val ARG_NAME = "userName"

        fun newInstance(userName: String): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putString(ARG_NAME, userName)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        
        // 1. Находим кнопку и вешаем обработчик
        val btnBack = view.findViewById<Button>(R.id.btnBackFromDetails)
        btnBack.setOnClickListener {
            // Эквивалент нажатия системной кнопки "Назад"
            // Вернет нас к предыдущему фрагменту в стеке (к списку)
            parentFragmentManager.popBackStack()
        }

        val tvName = view.findViewById<TextView>(R.id.tvDetailName)
        val name = arguments?.getString(ARG_NAME) ?: "Не выбрано"
        tvName.text = name

        return view
    }
}
