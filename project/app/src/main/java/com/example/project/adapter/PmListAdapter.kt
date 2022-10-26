package com.example.project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.example.project.database.parliamentmembers.ParliamentMembers
import com.example.project.databinding.ListItemBinding

/*
Adapter class for PmList fragment's recyclerview.
Both adapters are almost identical
*/
class PmListAdapter(private val onItemClicked: (ParliamentMembers) -> Unit) :
    ListAdapter<ParliamentMembers, PmListAdapter.ItemViewHolder>(DiffCallback) {

    //inflates and creates the item that will be on the recyclerview (list_item.xml file in this case)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    //gets the Pms and calls the binding function to modify the textView in list_items.xml file
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class ItemViewHolder(private var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /*
        adds values on the list item view here
        parameter pm to get the parliament members
        */
        fun bind(pm: ParliamentMembers) {
            binding.pmName.text = "${pm.firstname + " " + pm.lastname}"
            binding.listItemImgViewAllPms.load("https://avoindata.eduskunta.fi/" + pm.pictureUrl)
        }
    }

    /*
    companion object for DiffCallback
    if the value changes, checks if the new value is the same as the old one
    */
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ParliamentMembers>() {
            override fun areItemsTheSame(
                oldPm: ParliamentMembers,
                newPm: ParliamentMembers
            ): Boolean {
                return oldPm === newPm
            }

            override fun areContentsTheSame(
                oldPm: ParliamentMembers,
                newPm: ParliamentMembers
            ): Boolean {
                return oldPm.hetekaId == newPm.hetekaId
            }
        }
    }
}