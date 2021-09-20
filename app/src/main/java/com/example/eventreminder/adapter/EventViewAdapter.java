package com.example.eventreminder.adapter;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventreminder.MainActivity;
import com.example.eventreminder.R;
import com.example.eventreminder.dao.EventDao;
import com.example.eventreminder.database.CommonDatabase;
import com.example.eventreminder.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventViewAdapter extends RecyclerView.Adapter<EventViewAdapter.EventViewHolder> {

    private List<Event> eventsList;
    private OnEventClickListener onEventClickListener;

    public interface OnEventClickListener{

        void onEventClick(int position);
    }

    public EventViewAdapter(List<Event> mItemList){
        this.eventsList = mItemList;
    }

    public void setOnEventClickListener(OnEventClickListener clickListener) {
        this.onEventClickListener = clickListener;
    }

    @Override
    public EventViewAdapter.EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row,parent,false);
        return new EventViewHolder(view, onEventClickListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(EventViewAdapter.EventViewHolder holder, final int position) {
        final Event event = eventsList.get(position);
        String text = event.getTitle() + " " + event.getEventTime();
        holder.title.setText(text);
        holder.description.setText(String.valueOf(event.getDescription()));
        int colorId = event.getSeverity().getColor();
        int color = holder.eventLayout.getContext().getResources().getColor(colorId);
        holder.eventLayout.setCardBackgroundColor(color);

        holder.deleteEvent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final EventDao contentDao = CommonDatabase.db.eventDao();
                contentDao.delete(eventsList.get(position));
                MainActivity.eventViewModel.getCurrentContent().setValue(new ArrayList<>());
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder{

        public TextView title, description;
        public ImageView deleteEvent;
        private CardView eventLayout;

        @SuppressLint("ResourceAsColor")
        public EventViewHolder(View eventView, final OnEventClickListener listener) {
            super(eventView);
            title = eventView.findViewById(R.id.title);
            description = eventView.findViewById(R.id.description);
            eventLayout =  eventView.findViewById(R.id.eventLayout);
            deleteEvent = eventView.findViewById(R.id.deleteEvent);

            eventView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onEventClick(position);
                        }
                    }
                }
            });
        }
    }
}