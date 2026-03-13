package com.example.labs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class DetailsFragment : Fragment() {

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
        
        val btnBack = view.findViewById<Button>(R.id.btnBackFromDetails)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val tvName = view.findViewById<TextView>(R.id.tvDetailName)
        val name = arguments?.getString(ARG_NAME) ?: "Не выбрано"
        tvName.text = name

        return view
    }
}
