package com.example.milkflow.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.milkflow.model.PersonModel

class PersonDiffUtil : DiffUtil.ItemCallback<PersonModel>() {
    override fun areItemsTheSame(oldItem: PersonModel, newItem: PersonModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PersonModel, newItem: PersonModel): Boolean {
        return oldItem == newItem
    }
}