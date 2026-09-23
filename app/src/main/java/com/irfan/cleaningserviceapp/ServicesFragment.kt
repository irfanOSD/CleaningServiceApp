package com.irfan.cleaningserviceapp

import android.os.Bundle
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.irfan.cleaningserviceapp.adapter.ServiceAdapter
import com.irfan.cleaningserviceapp.model.Service

class ServicesFragment : Fragment(R.layout.fragment_services) {

    companion object {
        private const val ARG_CATEGORY_ID = "arg_category_id"

        // Category id দিয়ে ServicesFragment বানানোর জন্য (filter করা লিস্ট দেখাতে)
        fun newInstance(categoryId: String): ServicesFragment {
            val fragment = ServicesFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY_ID, categoryId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryId = arguments?.getString(ARG_CATEGORY_ID) ?: ""
        val allServices = getSampleServices()

        // categoryId খালি থাকলে সব সার্ভিস দেখাবে, নাহলে শুধু সেই category-র সার্ভিস দেখাবে
        val serviceList = if (categoryId.isEmpty()) {
            allServices
        } else {
            allServices.filter { it.categoryId == categoryId }
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.servicesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ServiceAdapter(serviceList) { selectedService ->
            val intent = Intent(requireContext(), ServiceDetailActivity::class.java)
            intent.putExtra(ServiceDetailActivity.EXTRA_SERVICE_NAME, selectedService.name)
            intent.putExtra(ServiceDetailActivity.EXTRA_SERVICE_DESCRIPTION, selectedService.description)
            intent.putExtra(ServiceDetailActivity.EXTRA_SERVICE_ICON, selectedService.iconResId)
            startActivity(intent)
        }
    }

    private fun getSampleServices(): List<Service> {
        return listOf(
            Service(1, "Home Cleaning", R.drawable.ic_app_logo, "Complete home cleaning service", "home_cleaning"),
            Service(2, "Deep Cleaning", R.drawable.ic_app_logo, "Thorough deep cleaning for your space", "home_cleaning"),
            Service(3, "Office Cleaning", R.drawable.ic_app_logo, "Professional office cleaning service", "office_cleaning"),
            Service(4, "Carpet Cleaning", R.drawable.ic_app_logo, "Deep carpet cleaning and stain removal", "carpet_cleaning"),
            Service(5, "Sofa Cleaning", R.drawable.ic_app_logo, "Sofa and upholstery cleaning", "home_cleaning"),
            Service(6, "Bathroom Cleaning", R.drawable.ic_app_logo, "Deep bathroom sanitization and cleaning", "kitchen_cleaning"),
            Service(7, "Pest Control", R.drawable.ic_app_logo, "Effective pest control treatment", "window_cleaning")
        )
    }
}