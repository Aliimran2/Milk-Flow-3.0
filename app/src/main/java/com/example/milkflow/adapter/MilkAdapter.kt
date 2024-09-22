package com.example.milkflow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.milkflow.databinding.MilkpersonItemBinding
import com.example.milkflow.model.PersonModel


class MilkPersonAdapter(
    private val onDeletePerson: (PersonModel) -> Unit,
    private val onEditPerson: (PersonModel) -> Unit
) :
    ListAdapter<PersonModel,MilkPersonAdapter.MilkPersonVH>(PersonDiffUtil()) {

    class MilkPersonVH(private val itemBinding: MilkpersonItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {



        fun bind(person: PersonModel, onDeletePerson: (PersonModel) -> Unit, onEditPerson: (PersonModel) -> Unit) {
            itemBinding.personModel = person


            itemBinding.nameItem.setOnClickListener {
                onEditPerson(person)
            }
            itemBinding.personQuantity.setOnClickListener {
                onEditPerson(person)
            }

            itemBinding.root.setOnLongClickListener {
                onDeletePerson(person)
                true
            }

        }

        companion object {
            fun inflateFrom(parent: ViewGroup): MilkPersonVH {
                val inflater = LayoutInflater.from(parent.context)
                val binding = MilkpersonItemBinding.inflate(inflater, parent, false)
                return MilkPersonVH(binding)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MilkPersonVH =
        MilkPersonVH.inflateFrom(parent)


    override fun onBindViewHolder(holder: MilkPersonVH, position: Int) {
        val currentPerson = getItem(position)
        holder.bind(currentPerson, onDeletePerson, onEditPerson)


    }
}


