package com.irfan.cleaningserviceapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.irfan.cleaningserviceapp.adapter.SupportAdapter
import com.irfan.cleaningserviceapp.model.SupportOption

class SupportFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_support, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerSupport)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val supportList = listOf(
            SupportOption(
                title = "Email Support",
                subtitle = "Send us your query via email",
                iconResId = android.R.drawable.ic_dialog_email
            ),
            SupportOption(
                title = "Call Support",
                subtitle = "Talk to our support team",
                iconResId = android.R.drawable.ic_menu_call
            ),
            SupportOption(
                title = "FAQ",
                subtitle = "Frequently asked questions",
                iconResId = android.R.drawable.ic_menu_help
            ),
            SupportOption(
                title = "WhatsApp Support",
                subtitle = "Chat with us on WhatsApp",
                iconResId = android.R.drawable.ic_menu_send
            ),
            SupportOption(
                title = "Facebook Support",
                subtitle = "Message us on Facebook",
                iconResId = android.R.drawable.ic_menu_share
            )
        )

        recyclerView.adapter = SupportAdapter(supportList)

        return view
    }
}