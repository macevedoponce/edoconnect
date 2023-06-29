package com.acevedo.educonnect.estudiante.ui.Apuntes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.acevedo.educonnect.estudiante.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder>{
    Context context;
    List<Note> list;
    NotesClickListener listener;

    public NotesAdapter(Context context, List<Note> list, NotesClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }


    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvTitle.setSelected(true);

        holder.tvNotes.setText(list.get(position).getNotes());

        holder.tvDate.setText(list.get(position).getDate());
        holder.tvDate.setSelected(true);

        if(list.get(position).isPinned()){
            holder.ivPin.setImageResource(R.drawable.pin);
        }else{
            holder.ivPin.setImageResource(0);
        }
        int colorCode = getRandomColor();
        holder.cvNotesContainer.setCardBackgroundColor(holder.itemView.getResources().getColor(colorCode,null));
        holder.cvNotesContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });
        holder.cvNotesContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                listener.OnLongClick(list.get(holder.getAdapterPosition()), holder.cvNotesContainer);
                return true;
            }
        });
    }

    private int getRandomColor(){
        List<Integer> colorCode = new ArrayList<>();

        colorCode.add(com.acevedo.educonnect.commonresources.R.color.c1);
        colorCode.add(com.acevedo.educonnect.commonresources.R.color.c2);
        colorCode.add(com.acevedo.educonnect.commonresources.R.color.c3);
        colorCode.add(com.acevedo.educonnect.commonresources.R.color.c4);
        colorCode.add(com.acevedo.educonnect.commonresources.R.color.c5);

        Random random = new Random();
        int ranColor = random.nextInt(colorCode.size());
        return colorCode.get(ranColor);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class NotesViewHolder extends RecyclerView.ViewHolder{

    CardView cvNotesContainer;
    TextView tvNotes,tvTitle, tvDate;
    ImageView ivPin;
    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        cvNotesContainer = itemView.findViewById(R.id.cvNotesContainer);
        tvNotes = itemView.findViewById(R.id.tvNotes);
        tvDate = itemView.findViewById(R.id.tvDate);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        ivPin = itemView.findViewById(R.id.ivPin);
    }
}
