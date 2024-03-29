package com.example.electronics_service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private ArrayList<Appointment> mAppointmentsData;
    private ArrayList<Appointment> mAppointmentsDataAll;
    private Context mContext;
    private int lastPosition = -1;

    AppointmentAdapter(Context context, ArrayList<Appointment> appointmentsData) {
        this.mAppointmentsData = appointmentsData;
        this.mAppointmentsDataAll = appointmentsData;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_appointments, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.ViewHolder holder, int position) {
        Appointment currentAppointment = mAppointmentsData.get(position);

        holder.bindTo(currentAppointment);

        if(holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.recycler_animation);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mAppointmentsData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mDeviceText;
        private TextView mDateText;
        private TextView mDescriptionText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mDeviceText = itemView.findViewById(R.id.deviceTextView);
            mDateText = itemView.findViewById(R.id.dateTextView);
            mDescriptionText = itemView.findViewById(R.id.descriptionTextView);

        }

        public void bindTo(Appointment currentAppointment) {
            mDeviceText.setText(currentAppointment.getDevice());
            mDateText.setText(currentAppointment.getDate());
            mDescriptionText.setText(currentAppointment.getDescription());

            itemView.findViewById(R.id.update).setOnClickListener(view ->
                    ((MyAppointmentsActivity)mContext).updateAppointment(currentAppointment));

            itemView.findViewById(R.id.delete).setOnClickListener(view ->
                    ((MyAppointmentsActivity)mContext).deleteAppointment(currentAppointment));
        }
    }
}


