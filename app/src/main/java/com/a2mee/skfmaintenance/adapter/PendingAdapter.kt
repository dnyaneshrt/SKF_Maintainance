package com.a2mee.skfmaintenance.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.a2mee.skfmaintenance.R
import com.a2mee.skfmaintenance.response.InProcessSOPByEnginnerResponse
import com.a2mee.skfmaintenance.response.PendingSOPByEnginnerResponse
import com.a2mee.skfmaintenance.ui.PrimarySOPStageActivity

class PendingAdapter(
    val context: Context,
    val pendingSOPByEnginnerList: List<PendingSOPByEnginnerResponse>?
) : RecyclerView.Adapter<PendingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_in_process, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvCustomerName.text = " " + pendingSOPByEnginnerList!!.get(position).customerCode + " : " + pendingSOPByEnginnerList!!.get(position).customerName
        holder.tvSpindle.text = " " + pendingSOPByEnginnerList.get(position).spindleBrand + " : " + pendingSOPByEnginnerList.get(position).spindleModel
        holder.tvIssue.text = " " + pendingSOPByEnginnerList.get(position).issue
        holder.tvInProgress.text = " " + pendingSOPByEnginnerList.get(position).noOfStageComplete + "/" + pendingSOPByEnginnerList.get(position).noOfstage + " " + context.getString(R.string.stage_completed)

        holder.cvHeader.setOnClickListener({

            val intent = Intent(context, PrimarySOPStageActivity::class.java).apply {
                this.putExtra("sop_id", pendingSOPByEnginnerList.get(position).sopId)
            }
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int = pendingSOPByEnginnerList!!.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvCustomerName: TextView = itemView.findViewById(R.id.tv_customer_name)
        var tvSpindle: TextView = itemView.findViewById(R.id.tv_spindle)
        var tvIssue: TextView = itemView.findViewById(R.id.tv_issue)
        var tvInProgress: TextView = itemView.findViewById(R.id.tv_progress)
        var cvHeader: CardView = itemView.findViewById(R.id.cv_header)
    }
}