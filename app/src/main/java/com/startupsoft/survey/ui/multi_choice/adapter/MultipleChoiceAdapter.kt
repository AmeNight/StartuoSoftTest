package com.startupsoft.survey.ui.multi_choice.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.startupsoft.survey.R
import com.startupsoft.survey.extentions.inflateView
import com.startupsoft.survey.extentions.onClick
import com.startupsoft.survey.ui.multi_choice.MultipleChoiceType
import kotlinx.android.synthetic.main.cell_multiple_choice.view.*

class MultipleChoiceAdapter(
    private val onItemSelected: (MultipleChoiceType) -> Unit
) : RecyclerView.Adapter<MultipleChoiceViewHolder>() {

    private val choices: MutableList<MultipleChoiceViewModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MultipleChoiceViewHolder(
        parent.inflateView(R.layout.cell_multiple_choice), onItemSelected
    )

    override fun onBindViewHolder(holder: MultipleChoiceViewHolder, position: Int) {
        holder.bind(choices[position])
    }

    override fun getItemCount() = choices.size

    fun setChoices(choices: List<MultipleChoiceViewModel>) {
        this.choices.clear()
        this.choices.addAll(choices)
        notifyDataSetChanged()
    }

    fun removeSelection(type: MultipleChoiceType) {
        choices.forEachIndexed { index, model ->
            if (model.type == type) {
                choices[index] = model.copy(type = type)
                notifyItemChanged(index)
                return
            }
        }
    }
}

class MultipleChoiceViewHolder(
    v: View, private val onItemSelected: (MultipleChoiceType) -> Unit
) : RecyclerView.ViewHolder(v) {

    fun bind(model: MultipleChoiceViewModel) {
        itemView.multiChoice.title = model.title
        itemView.multiChoice.onClick { onItemSelected(model.type) }
    }
}

data class MultipleChoiceViewModel(
    val type: MultipleChoiceType,
    val title: String,
    val isSelected: Boolean = false
)