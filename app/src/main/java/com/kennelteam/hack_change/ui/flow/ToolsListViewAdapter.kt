package com.kennelteam.hack_change.ui.flow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.kennelteam.hack_change.R

class ToolsListViewAdapter(items: Array<Tool>, ctx: Context) :
    ArrayAdapter<Tool>(ctx, R.layout.tools_list_view_item, items) {

    private class ToolItemViewHolder {
        var title: TextView? = null
        var description: TextView? = null
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val viewHolder: ToolItemViewHolder

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.tools_list_view_item, viewGroup, false)

            viewHolder = ToolItemViewHolder()
            viewHolder.title = view!!.findViewById<View>(R.id.tool_title) as TextView
            viewHolder.description = view.findViewById<View>(R.id.tool_description) as TextView
        } else {
            viewHolder = view.tag as ToolItemViewHolder
        }

        val tool = getItem(i)
        viewHolder.title!!.text = tool!!.title
        viewHolder.description!!.text = tool.description

        view.tag = viewHolder

        return view
    }
}
