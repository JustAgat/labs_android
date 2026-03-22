package com.example.labs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Лабораторная работа 19: Рефакторинг на MVVM.
 * Теперь фрагмент только отображает данные, а логика находится в ViewModel.
 */
class PostListFragment : Fragment() {

    private lateinit var rvPosts: RecyclerView
    private lateinit var progressBar: ProgressBar
    
    // Получаем ViewModel (Presentation слой)
    private val viewModel: PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_list, container, false)
        
        rvPosts = view.findViewById(R.id.rvPosts)
        progressBar = view.findViewById(R.id.progressBar)
        rvPosts.layoutManager = LinearLayoutManager(context)

        view.findViewById<Button>(R.id.btnBackFromPosts).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        
        setupObservers()
        
        // Загружаем данные только если список пуст (чтобы не перегружать при повороте экрана)
        if (viewModel.posts.value == null) {
            viewModel.loadPosts()
        }
        
        return view
    }

    private fun setupObservers() {
        // Наблюдаем за списком постов
        viewModel.posts.observe(viewLifecycleOwner) { posts ->
            rvPosts.adapter = PostAdapter(posts)
        }

        // Наблюдаем за состоянием загрузки
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Наблюдаем за ошибками
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, "Ошибка: $it", Toast.LENGTH_LONG).show()
            }
        }
    }
}
