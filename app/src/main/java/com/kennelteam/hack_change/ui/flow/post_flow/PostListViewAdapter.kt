package com.kennelteam.hack_change.ui.flow.post_flow

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.kennelteam.hack_change.R
import com.kennelteam.hack_change.ui.flow.Post
import java.util.*

class PostListViewAdapter(items: ArrayList<Post>, ctx: Context) :
    ArrayAdapter<Post>(ctx, R.layout.post_list_view_item, items) {

    private class PostItemViewHolder {
        var postTheme: TextView? = null
        var userId: TextView? = null
        var text: TextView? = null
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view_ = view
        val viewHolder: PostItemViewHolder

        if (view_ == null) {
            val inflater = LayoutInflater.from(context)
            view_ = inflater.inflate(R.layout.post_list_view_item, viewGroup, false)

            viewHolder = PostItemViewHolder()
            viewHolder.postTheme = view_!!.findViewById<View>(R.id.UserName) as TextView
            viewHolder.userId = view_.findViewById<View>(R.id.topic) as TextView
            viewHolder.text = view_.findViewById<View>(R.id.text) as TextView
        } else {
            viewHolder = view_.tag as PostItemViewHolder
        }

        val post = getItem(i)
        viewHolder.postTheme!!.text = post!!.theme
        viewHolder.userId!!.text = post.userId
        viewHolder.text!!.text = post.text

        view_.tag = viewHolder

        return view_
    }
}
