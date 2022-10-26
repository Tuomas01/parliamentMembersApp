package com.example.project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.project.database.parliamentmembers.ParliamentMembers
import com.example.project.databinding.MyFavouritesListItemBinding

/*
Adapter class for MyFavourites fragment's recyclerview.
both adapters are almost identical
*/
class MyFavouritesAdapter(private val onItemClicked: (ParliamentMembers) -> Unit) :
    ListAdapter<ParliamentMembers, MyFavouritesAdapter.ItemViewHolder>(DiffCallback) {

    //inflates and creates the items that will be on the recyclerview (my_favourites_list_item.xml file in this case)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            MyFavouritesListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    /*
    gets the Pms and calls the binding function to modify the views in my_favourites_list_items.xml file
    Also adds an onclick listener to the list items.
    */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    /*
    defines the binding variable for binding the list item views.
    inherits the Recyclerview.Viewholder class to enable the use of itemView
    */
    class ItemViewHolder(private var binding: MyFavouritesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /*
        adds values on the list item views here
        parameter pm to get the parliament members
        */
        fun bind(pm: ParliamentMembers) {
            binding.myFavouritesListItemInfo.text = "Name: ${pm.firstname + " " + pm.lastname} \n" +
                    "Seatnumber: ${pm.seatNumber}\n" +
                    "Party: ${pm.party}\n" +
                    "Is a minister? ${if (pm.minister) "Yes" else "No"}"
            binding.myFavouritesListItemPmImg.load("https://avoindata.eduskunta.fi/" + pm.pictureUrl)
        }
    }

    //companion object for DiffCallback
    //if the value changes, checks if the new value is the same as the old one
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