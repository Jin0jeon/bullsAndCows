package com.example.bullsandcows

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CustomGridAdapter(private val context: Context, private val items: List<GridItem>) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false)

        val btnNum = view.findViewById<TextView>(R.id.btnNum)
        btnNum.text = items[position].text
        btnNum.textSize = 40F
        val item = items[position]
        btnNum.setOnClickListener { item.action() } // 버튼 클릭 시 실행


        return view
    }
}
