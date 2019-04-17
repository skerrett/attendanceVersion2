package com.example.attendanceversion2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder>{

    private Context mContext;
    private ArrayList<LessonResponse> LessonList;
    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int postion);
    }

    public void setOnItemClickListnerer(onItemClickListener listnerer){
        mListener = listnerer;
    }

    public LessonAdapter(Context context, ArrayList<LessonResponse> lessonList){
        this.mContext = context;
        this.LessonList = lessonList;
    }


    @Override
    public LessonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.lesson_item, parent,false);
        return new LessonViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return LessonList.size();
    }

    @Override
    public void onBindViewHolder(LessonViewHolder holder, int position) {
        LessonResponse currentItem = LessonList.get(position);


        String date = currentItem.getDate();
        int id = currentItem.getId();
        String name = currentItem.getName();

        holder.name.setText(name);
        holder.date.setText(" "+date);
        holder.id.setText(" "+Integer.toString(id));

    }



    public class  LessonViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView date;
        public TextView id;



        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_view_name);
            date = itemView.findViewById(R.id.datetextView);
            id = itemView.findViewById(R.id.lessonIDtextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener !=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}


