package com.rant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class tasksAdapter (private val tasks_: ArrayList<tasks>) : RecyclerView.Adapter<tasksViewHolder>() {
    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): tasksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return tasksViewHolder(view)
    }
    override fun onBindViewHolder(holder: tasksViewHolder, position: Int) {

        val task_ = tasks_[position]
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, task_)
        }

        holder.bind(task_)
    }
    fun setOnClickListener(listener: OnClickListener?) {
        this.onClickListener = listener
    }

    // Interface for the click listener
    interface OnClickListener {
        fun onClick(position: Int, model: tasks)
    }

    override fun getItemCount(): Int {
        return tasks_.size
    }
}