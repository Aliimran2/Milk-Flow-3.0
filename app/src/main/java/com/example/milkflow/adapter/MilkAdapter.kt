package com.example.milkflow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.milkflow.databinding.MilkpersonItemBinding
import com.example.milkflow.model.ExpenseModel
import com.example.milkflow.model.PersonModel
import com.example.milkflow.utils.ColorsUtil


class MilkPersonAdapter(
    private val onDeletePerson: (PersonModel) -> Unit,
    private val onEditPerson: (PersonModel) -> Unit
) :
    ListAdapter<PersonModel, MilkPersonAdapter.MilkPersonVH>(PersonDiffUtil()) {

    class MilkPersonVH(val itemBinding: MilkpersonItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


        fun bind(
            person: PersonModel,
            onDeletePerson: (PersonModel) -> Unit,
            onEditPerson: (PersonModel) -> Unit
        ) {
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

            itemBinding.executePendingBindings()

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

        holder.itemBinding.cardView.background.setTint(ColorsUtil.getRandomColor())


    }


}


