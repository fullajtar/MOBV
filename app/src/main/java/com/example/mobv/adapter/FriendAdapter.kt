package com.example.mobv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv.R
import com.example.mobv.api.FriendResponse
import kotlinx.android.synthetic.main.fragment_friend_list.view.*
import kotlinx.android.synthetic.main.list_item_friend.view.*



class FriendAdapter(
    private val context: Context,
    private val dataset: List<FriendResponse>,
    private val findNavController: NavController
) : RecyclerView.Adapter<FriendAdapter.ItemViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.listItemFriend_name)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_friend, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        val text = "${item.user_name} \n bar: ${item.bar_name}"
        holder.textView.text =  text
//        holder.textView.setOnClickListener {
//            (holder.textView.setTextColor(Color.GREEN))
//////            navigate to detail of pub
////            findNavController.navigate(
////                ListPubDirections.actionListPubToDetails( item )
////            )
//        } // click event

    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size

}