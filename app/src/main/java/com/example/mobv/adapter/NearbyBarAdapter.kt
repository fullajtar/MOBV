package com.example.mobv.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv.R
import com.example.mobv.api.BodyCheckInBar
import com.example.mobv.model.Pub
import com.example.mobv.viewmodel.CheckIntoBarViewModel
import kotlin.math.roundToInt

class NearbyBarAdapter(
    private val context: Context,
    private val dataset: List<Pub>,
    private val findNavController: NavController,
    private val viewmodel: CheckIntoBarViewModel
) : RecyclerView.Adapter<NearbyBarAdapter.ItemViewHolder>() {
    var selectedItemPos = 0
    var lastItemSelectedPos = 0
    var data = dataset

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.listItemNearbyBar_name)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_nearbybar, parent, false)

        return ItemViewHolder(adapterLayout)
    }


    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (position == selectedItemPos){
            (holder.textView.setTextColor(Color.GREEN))
        }
        else{
            (holder.textView.setTextColor(Color.WHITE))
        }
        val item = data[position]
        if (item.tags?.name != null) {
            val text = "${item.tags?.amenity}: ${item.tags?.name} \n Distance: ${item.disFromLastPosition?.roundToInt()} m"
            holder.textView.text =  text
            holder.textView.setOnClickListener {
                selectedItemPos = position
                if(lastItemSelectedPos == -1){
                    lastItemSelectedPos = position
                }
                else {
                    notifyItemChanged(lastItemSelectedPos)
                    lastItemSelectedPos = position
                }
                notifyItemChanged(selectedItemPos)
                viewmodel.selected.postValue(
                    BodyCheckInBar(
                        item.id?.toString(),
                        item.tags?.name,
                        item.tags?.amenity,
                        item.lat?.toDouble(),
                        item.lon?.toDouble()
                    )
                )

            } // click event
        } else {
            holder.itemView.visibility = View.GONE
        }

    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = data.size

    private fun getActualPostion(position: Int): Int {
        //your logic to skip the given postion
        if(position == 4){
            return position + 2
        }
        return position
    }

    fun update(dataset: List<Pub>){
        data = dataset
        notifyDataSetChanged()
    }
}