package com.abhishek.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.myapplication.R
import com.abhishek.myapplication.model.SingleItemModel

class SingleItemAdapter(private var centerList: List<SingleItemModel>) :
    RecyclerView.Adapter<SingleItemAdapter.CenterRVViewHolder>() {

    // ViewHolder class to hold the views
    class CenterRVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val centerNameTV: TextView = itemView.findViewById(R.id.tv_center_name)
        val centerAddressTV: TextView = itemView.findViewById(R.id.tv_center_address)
        val centerTimingsTV: TextView = itemView.findViewById(R.id.tv_center_timing)
        val vaccineNameTV: TextView = itemView.findViewById(R.id.tv_vaccine_name)
        val ageLimitTV: TextView = itemView.findViewById(R.id.tv_age_limit)
        val feeTypeTV: TextView = itemView.findViewById(R.id.tv_fee_type)
        val availabilityTV: TextView = itemView.findViewById(R.id.tv_availability)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterRVViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single__item_desc, parent, false)
        return CenterRVViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CenterRVViewHolder, position: Int) {
        val currentItem = centerList[position]

        holder.centerNameTV.text = currentItem.centerName
        holder.centerAddressTV.text = currentItem.centerAddress
        holder.ageLimitTV.text = "Age Limit : ${currentItem.ageLimit}+"
        holder.availabilityTV.text = "Availability : ${currentItem.availableCapacity}"
        holder.centerTimingsTV.text =
            "From : ${currentItem.centerFromTiming} To : ${currentItem.centerToTiming}"
        holder.vaccineNameTV.text = currentItem.vaccineName
        holder.feeTypeTV.text = currentItem.feeType
    }

    override fun getItemCount(): Int {
        return centerList.size
    }

    // Method to update the data in the adapter
    fun updateData(newCenterList: List<SingleItemModel>) {
        centerList = newCenterList
        notifyDataSetChanged() // Notify the adapter to refresh the data
    }

    // Optional: Clear references if needed (e.g., if holding onto contexts or large data)
    fun clear() {
        // Clear the list to help with garbage collection
        centerList = emptyList()
        notifyDataSetChanged()
    }
}