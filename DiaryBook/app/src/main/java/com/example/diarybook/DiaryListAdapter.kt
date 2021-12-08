package com.example.diarybook

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.diary_list_item.view.*

class DiaryListAdapter(context: Context, val resource: Int, data: List<DiaryItem>) :
    ArrayAdapter<DiaryItem>(context, resource, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =  LayoutInflater.from(context).inflate(resource, parent, false)
        val title: TextView = view.title
        val date: TextView = view.date
        val diary = getItem(position)
        if (diary != null) {
            title.text = diary.title
            date.text = diary.create_date
        }

        return view
    }

}