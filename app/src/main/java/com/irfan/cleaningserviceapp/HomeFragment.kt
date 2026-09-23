package com.irfan.cleaningserviceapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.irfan.cleaningserviceapp.adapter.CategoryAdapter
import com.irfan.cleaningserviceapp.adapter.PopularServiceAdapter
import com.irfan.cleaningserviceapp.model.PopularService
import com.irfan.cleaningserviceapp.model.ServiceCategory

class HomeFragment : Fragment() {

    // পুরো Popular Service লিস্ট (search filter করার জন্য মনে রাখা দরকার)
    private var fullPopularServiceList: List<PopularService> = emptyList()
    private lateinit var popularServiceRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // fragment_home.xml layout-টা load করা হচ্ছে
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategoryList(view)
        setupPopularServiceList(view)
        setupBookNowButton(view)
        setupSearchBar(view)
    }

    private fun setupCategoryList(view: View) {
        val categoryRecyclerView = view.findViewById<RecyclerView>(R.id.categoryRecyclerView)

        // সাময়িক Dummy Data (পরে Firebase থেকে আসবে)
        val categoryList = listOf(
            ServiceCategory("home_cleaning", "Home Cleaning", R.drawable.ic_app_logo),
            ServiceCategory("office_cleaning", "Office Cleaning", R.drawable.ic_app_logo),
            ServiceCategory("carpet_cleaning", "Carpet Cleaning", R.drawable.ic_app_logo),
            ServiceCategory("window_cleaning", "Window Cleaning", R.drawable.ic_app_logo),
            ServiceCategory("kitchen_cleaning", "Kitchen Cleaning", R.drawable.ic_app_logo)
        )

        categoryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        categoryRecyclerView.adapter = CategoryAdapter(categoryList) { selectedCategory ->
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, ServicesFragment.newInstance(selectedCategory.id))
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setupPopularServiceList(view: View) {
        popularServiceRecyclerView = view.findViewById(R.id.popularServiceRecyclerView)

        // সাময়িক Dummy Data (পরে Firebase থেকে আসবে)
        fullPopularServiceList = listOf(
            PopularService("s1", "Deep Home Cleaning", R.drawable.ic_app_logo, "৳ 1200", 4.8f),
            PopularService("s2", "Office Cleaning", R.drawable.ic_app_logo, "৳ 1800", 4.6f),
            PopularService("s3", "Sofa Cleaning", R.drawable.ic_app_logo, "৳ 800", 4.5f),
            PopularService("s4", "Bathroom Cleaning", R.drawable.ic_app_logo, "৳ 600", 4.7f)
        )

        popularServiceRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        showPopularServices(fullPopularServiceList)
    }

    private fun showPopularServices(serviceList: List<PopularService>) {
        popularServiceRecyclerView.adapter = PopularServiceAdapter(serviceList) { selectedService ->
            val description = "Price: ${selectedService.price} | Rating: ★ ${selectedService.rating}"

            val intent = Intent(requireContext(), ServiceDetailActivity::class.java)
            intent.putExtra(ServiceDetailActivity.EXTRA_SERVICE_NAME, selectedService.name)
            intent.putExtra(ServiceDetailActivity.EXTRA_SERVICE_DESCRIPTION, description)
            intent.putExtra(ServiceDetailActivity.EXTRA_SERVICE_ICON, selectedService.imageResId)
            startActivity(intent)
        }
    }

    private fun setupSearchBar(view: View) {
        val searchEditText = view.findViewById<EditText>(R.id.searchEditText)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString()?.trim() ?: ""

                val filteredList = if (query.isEmpty()) {
                    fullPopularServiceList
                } else {
                    fullPopularServiceList.filter { it.name.contains(query, ignoreCase = true) }
                }

                showPopularServices(filteredList)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupBookNowButton(view: View) {
        val bookNowButton = view.findViewById<View>(R.id.bookNowButton)

        bookNowButton.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, ServicesFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}