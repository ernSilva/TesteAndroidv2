package com.emerson.bankapp.presentation.home.list

import com.emerson.bankapp.domain.models.Statement
import androidx.recyclerview.widget.DiffUtil

class StatementDiffUtil : DiffUtil.ItemCallback<Statement>() {
    override fun areItemsTheSame(oldItem: Statement, newItem: Statement): Boolean {
        return oldItem.title == newItem.title
                && oldItem.date == newItem.date
                && oldItem.value == newItem.value
    }

    override fun areContentsTheSame(oldItem: Statement, newItem: Statement): Boolean {
        return oldItem == newItem
    }
}