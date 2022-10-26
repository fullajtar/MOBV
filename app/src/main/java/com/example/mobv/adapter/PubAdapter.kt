package com.example.mobv.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv.R
import com.example.mobv.fragment.ListPubDirections
import com.example.mobv.model.Pub
import kotlinx.android.synthetic.main.fragment_list_pub.view.*
import kotlinx.android.synthetic.main.list_item_pub.view.*

/**
 * Adapter for the [RecyclerView] in [RegistrationFragment]. Displays [Pub] data object.
 */
class PubAdapter (
    private val context: Context,
    private val dataset: List<Pub>,
    private val findNavController: NavController
) : RecyclerView.Adapter<PubAdapter.ItemViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.listItemPub_name)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_pub, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text =  item.tags!!.name.toString()
        holder.textView.setOnClickListener {
            (holder.textView.setTextColor(Color.GREEN))
            findNavController.navigate(
                ListPubDirections.actionRegistrationToDetails( item )
            )
        } // click event

    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size

}