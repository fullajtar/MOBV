package com.example.mobv.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv.R
import com.example.mobv.api.FriendResponse
import com.example.mobv.fragment.FriendListDirections
import com.example.mobv.model.Bar


class FriendAdapter(
    private val context: Context,
    private val dataset: List<FriendResponse>,
    private val findNavController: NavController
) : RecyclerView.Adapter<FriendAdapter.ItemViewHolder>() {
    private var data: List<FriendResponse> = dataset

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
        val item = data[position]
        val text = "${item.user_name} \n bar: ${item.bar_name}"
        holder.textView.text =  text
        holder.textView.setOnClickListener {
//            navigate to detail of pub
            if (item.bar_id != null){
                val bar = Bar(item.bar_id, item.bar_name, item.bar_lat, item.bar_lon, null, null)
                Log.d("testingOut: ", "bar: ${bar}")
                findNavController.navigate(
                    FriendListDirections.actionFriendListToDetail(bar)
                )
            }
            else{
                Toast.makeText(context ,"Friend is not in a bar!", Toast.LENGTH_SHORT).show()
            }

        } // click event

    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = data.size

    fun update(dataset: List<FriendResponse>){
        data = dataset
        notifyDataSetChanged()
    }

}