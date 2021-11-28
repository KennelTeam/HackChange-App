package com.kennelteam.hack_change.ui.flow.comment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.kennelteam.hack_change.R
import com.kennelteam.hack_change.ui.flow.Comment
import java.util.*

class CommentListViewAdapter(items: ArrayList<Comment>, ctx: Context) :
    ArrayAdapter<Comment>(ctx, R.layout.comment_list_view_item, items) {

    private class CommentItemViewHolder {
        var userName: TextView? = null
        var text: TextView? = null
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val viewHolder: CommentItemViewHolder

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.comment_list_view_item, viewGroup, false)

            viewHolder = CommentItemViewHolder()
            viewHolder.userName = view!!.findViewById<View>(R.id.UserName) as TextView
            viewHolder.text = view.findViewById<View>(R.id.text) as TextView
        } else {
            viewHolder = view.tag as CommentItemViewHolder
        }

        val comment = getItem(i)
        viewHolder.userName!!.text = comment!!.userName
        viewHolder.text!!.text = comment.text

        view.tag = viewHolder

        return view
    }
}
