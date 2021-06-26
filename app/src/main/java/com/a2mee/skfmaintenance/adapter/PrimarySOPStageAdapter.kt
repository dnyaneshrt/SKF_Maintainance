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
import com.a2mee.skfmaintenance.response.CompletedSOPByEnginnerResponse
import com.a2mee.skfmaintenance.response.InProcessSOPByEnginnerResponse
import com.a2mee.skfmaintenance.response.PendingSOPByEnginnerResponse
import com.a2mee.skfmaintenance.response.PrimarySOPStagesBySOPResponse
import com.a2mee.skfmaintenance.ui.PrimarySOPStageActivity

class PrimarySOPStageAdapter(
    val context: Context,
    val primarySOPStagesBySOPList: List<PrimarySOPStagesBySOPResponse>?
) : RecyclerView.Adapter<PrimarySOPStageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_primary_sop_stage, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvStageNo.text = "" + primarySOPStagesBySOPList!!.get(position).stageNo + ". "
        holder.tvStageName.text = primarySOPStagesBySOPList.get(position).stageName
   }

    override fun getItemCount(): Int = primarySOPStagesBySOPList!!.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvStageNo: TextView = itemView.findViewById(R.id.tv_stage_no)
        var tvStageName: TextView = itemView.findViewById(R.id.tv_stage_name)
    }
}