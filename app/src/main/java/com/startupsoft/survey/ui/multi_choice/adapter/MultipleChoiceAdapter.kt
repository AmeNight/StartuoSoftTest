package com.startupsoft.survey.ui.multi_choice.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.startupsoft.survey.R
import com.startupsoft.survey.extentions.inflateView
import com.startupsoft.survey.ui.multi_choice.MultipleChoiceType
import kotlinx.android.synthetic.main.cell_multiple_choice.view.*

class MultipleChoiceAdapter(
    private val onItemSelected: (MultipleChoiceType, Boolean) -> Unit
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

    fun setSelected(type: MultipleChoiceType, selected: Boolean) {
        choices.forEachIndexed { index, model ->
            if (model.type == type) {
                choices[index] = model.copy(isSelected = selected)
                notifyItemChanged(index)
                return
            }
        }
    }
}

class MultipleChoiceViewHolder(
    v: View, private val onItemSelected: (MultipleChoiceType, Boolean) -> Unit
) : RecyclerView.ViewHolder(v) {

    fun bind(model: MultipleChoiceViewModel) {
        itemView.multiChoice.title = model.title
        itemView.multiChoice.checked = model.isSelected
        itemView.multiChoice.onItemClick {
            onItemSelected(model.type, it)
        }
    }
}

data class MultipleChoiceViewModel(
    val type: MultipleChoiceType,
    val title: String,
    val isSelected: Boolean = false
)