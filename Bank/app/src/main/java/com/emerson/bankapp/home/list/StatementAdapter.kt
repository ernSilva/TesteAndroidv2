package com.emerson.bankapp.presentation.home.list

import com.emerson.bankapp.domain.models.Statement
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class StatementAdapter() : ListAdapter<Statement, StatementViewHolder>(StatementDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatementViewHolder {
        return StatementViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: StatementViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}