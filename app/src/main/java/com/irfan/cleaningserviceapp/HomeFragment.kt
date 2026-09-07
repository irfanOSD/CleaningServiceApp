package com.irfan.cleaningserviceapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.irfan.cleaningserviceapp.adapter.CategoryAdapter
import com.irfan.cleaningserviceapp.adapter.PopularServiceAdapter
import com.irfan.cleaningserviceapp.model.PopularService
import com.irfan.cleaningserviceapp.model.ServiceCategory

class HomeFragment : Fragment() {

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
            Toast.makeText(requireContext(), "Clicked: ${selectedCategory.name}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupPopularServiceList(view: View) {
        val serviceRecyclerView = view.findViewById<RecyclerView>(R.id.popularServiceRecyclerView)

        // সাময়িক Dummy Data (পরে Firebase থেকে আসবে)
        val serviceList = listOf(
            PopularService("s1", "Deep Home Cleaning", R.drawable.ic_app_logo, "৳ 1200", 4.8f),
            PopularService("s2", "Office Cleaning", R.drawable.ic_app_logo, "৳ 1800", 4.6f),
            PopularService("s3", "Sofa Cleaning", R.drawable.ic_app_logo, "৳ 800", 4.5f),
            PopularService("s4", "Bathroom Cleaning", R.drawable.ic_app_logo, "৳ 600", 4.7f)
        )

        serviceRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        serviceRecyclerView.adapter = PopularServiceAdapter(serviceList) { selectedService ->
            Toast.makeText(requireContext(), "Clicked: ${selectedService.name}", Toast.LENGTH_SHORT).show()
        }
    }
}