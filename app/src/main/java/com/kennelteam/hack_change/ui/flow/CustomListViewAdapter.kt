package com.kennelteam.hack_change.ui.flow

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.changellenge_client.R
import java.util.*

class CustomListViewAdapter(items: ArrayList<Attraction>, ctx: Context) :
    ArrayAdapter<Attraction>(ctx, R.layout.custom_list_view_item, items) {

    //view holder is used to prevent findViewById calls
    private class AttractionItemViewHolder {
        internal var image: ImageView? = null
        internal var title: TextView? = null
        internal var description: TextView? = null
        internal var hours: TextView? = null
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view

        val viewHolder: AttractionItemViewHolder

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.custom_list_view_item, viewGroup, false)

            viewHolder = AttractionItemViewHolder()
            viewHolder.title = view!!.findViewById<View>(R.id.title) as TextView
            viewHolder.description = view.findViewById<View>(R.id.description) as TextView
            viewHolder.hours = view.findViewById<View>(R.id.hours) as TextView
            //shows how to apply styles to views of item for specific items
            if (i == 3)
                viewHolder.hours!!.setTextColor(Color.DKGRAY)
            viewHolder.image = view.findViewById<View>(R.id.image) as ImageView
        } else {
            //no need to call findViewById, can use existing ones from saved view holder
            viewHolder = view.tag as AttractionItemViewHolder
        }

        val attraction = getItem(i)
        viewHolder.title!!.text = attraction!!.title
        viewHolder.description!!.text = attraction.description
        viewHolder.hours!!.text = attraction.hours
        viewHolder.image!!.setImageResource(attraction.image)

        //shows how to handle events of views of items
        viewHolder.image!!.setOnClickListener {
            Toast.makeText(context, "Clicked image of " + attraction.title,
                Toast.LENGTH_SHORT).show()
        }

        view.tag = viewHolder

        return view
    }
}

class Attraction {
    var title: String? = null
    var description: String? = null
    var hours: String? = null
    var image: Int = 0
}
