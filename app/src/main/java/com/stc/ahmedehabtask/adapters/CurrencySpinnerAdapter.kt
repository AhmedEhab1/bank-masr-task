package com.stc.ahmedehabtask.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CurrencySpinnerAdapter(context: Context, resource: Int, private val currencies: List<String>) :
    ArrayAdapter<String>(context, resource, currencies) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        (view as TextView).text = currencies[position]
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        (view as TextView).text = currencies[position]
        return view
    }
}
