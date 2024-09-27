package com.example.milkflow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.milkflow.R
import com.example.milkflow.databinding.ExpenseItemBinding
import com.example.milkflow.model.ExpenseModel
import com.example.milkflow.utils.ColorsUtil
import com.example.milkflow.utils.myToast

class ExpenseAdapter(
    private val onDeleteExpense: (ExpenseModel) -> Unit,
    private val onEditExpense: (ExpenseModel) -> Unit
) : ListAdapter<ExpenseModel, ExpenseAdapter.ExpenseVH>(ExpenseDiffUtils()) {


    class ExpenseVH(val itemBinding: ExpenseItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(
            expenseModel: ExpenseModel,
            onDeleteExpense: (ExpenseModel) -> Unit,
            onEditExpense: (ExpenseModel) -> Unit
        ) {

            itemBinding.nameItem.setOnClickListener {
                onEditExpense(expenseModel)

            }

            itemBinding.amount.setOnClickListener {
                onEditExpense(expenseModel)

            }

            itemBinding.expenseModel = expenseModel

            itemBinding.root.setOnLongClickListener {
                onDeleteExpense(expenseModel)
                true
            }



            itemBinding.executePendingBindings()


        }

        companion object {
            fun inflateFrom(parent: ViewGroup): ExpenseVH {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ExpenseItemBinding.inflate(inflater, parent, false)
                return ExpenseVH(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseVH =
        ExpenseVH.inflateFrom(parent)

    override fun onBindViewHolder(holder: ExpenseVH, position: Int) {
        val currentItem = getItem(position)

        holder.bind(currentItem, onDeleteExpense,onEditExpense)
        holder.itemBinding.cardViewExp.background.setTint(ColorsUtil.getRandomColorFromPalette())

    }
}

class ExpenseDiffUtils : DiffUtil.ItemCallback<ExpenseModel>() {
    override fun areItemsTheSame(oldItem: ExpenseModel, newItem: ExpenseModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ExpenseModel, newItem: ExpenseModel): Boolean {
        return oldItem == newItem
    }

}