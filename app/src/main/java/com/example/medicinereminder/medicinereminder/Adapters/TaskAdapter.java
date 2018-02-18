package com.example.medicinereminder.medicinereminder.Adapters;/*
package com.example.medicinereminder.medicinereminder.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicinereminder.medicinereminder.Models.Task;
import com.example.medicinereminder.medicinereminder.R;
import com.example.medicinereminder.medicinereminder.Utils.DatabaseHelper;

import java.util.ArrayList;

*/

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicinereminder.medicinereminder.Models.Task;
import com.example.medicinereminder.medicinereminder.R;
import com.example.medicinereminder.medicinereminder.Utils.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by sukrit on 10/2/18.
 *//*


public class TaskAdapter extends ArrayAdapter<Task> {

    ArrayList<Task> arrayList;
    Context context;
    DatabaseHelper db;

    public TaskAdapter(@NonNull Context context, int resource, ArrayList<Task> arrayList, Context context1) {
        super(context, resource);
        this.arrayList = arrayList;
        this.context = context1;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        db = new DatabaseHelper(context);
        LayoutInflater li= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int layoutType;
        layoutType= R.layout.list_item;
        View viewItem = li.inflate(layoutType,parent,false);
        return new TaskViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(final TaskAdapter.TaskViewHolder holder, final int position) {
        final Task thisTask = arrayList.get(position);
        holder.taskName.setText(thisTask.getTaskName());
        holder.taskDate.setText(thisTask.getDateString());
        holder.taskTime.setText(thisTask.getTimeString());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.checkBox.setImageResource(R.drawable.checked);
                MediaPlayer mediaPlayer=MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
                Log.d("TAG", "check: "+mediaPlayer.isPlaying());
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                holder.checkBox.setClickable(false);
                db.deleteTask(thisTask.getTaskName().trim());
                Log.d("ABB", "onClick: "+thisTask.getTaskName());

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_delete_todo_dialog);
                dialog.setCancelable(true);
                dialog.show();
                dialog.findViewById(R.id.yesButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        arrayList.remove(holder.getAdapterPosition());
                        db.deleteTask(thisTask.getTaskName().trim());
                        notifyDataSetChanged();
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,arrayList.size());

                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.noButtton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskName,taskDate,taskTime;
        View testView;
        ImageView checkBox;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskName=itemView.findViewById(R.id.reminderDetails);
            taskDate=itemView.findViewById(R.id.whichDay);
            taskTime=itemView.findViewById(R.id.whatTime);
            checkBox=itemView.findViewById(R.id.checkBox);
            testView=itemView;
        }
    }
}
*/
public class TaskAdapter extends ArrayAdapter<Task> {
    ArrayList<Task> arrayList;
    Context context;
    DatabaseHelper db;
    LayoutInflater li;

    public TaskAdapter(@NonNull Context context, int resource, ArrayList<Task> arrayList) {
        super(context, resource , arrayList);
        this.context=context;
        this.arrayList=arrayList;
        db = new DatabaseHelper(context);
        li= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Task thisTask = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        TaskViewHolder holder = null; // view lookup cache stored in tag

        View result;

        if (convertView == null) {
            convertView=li.inflate(R.layout.list_item,null);
            holder=new TaskViewHolder();

            holder.taskName=convertView.findViewById(R.id.reminderDetails);
            holder.taskDate=convertView.findViewById(R.id.whichDay);
            holder.taskTime=convertView.findViewById(R.id.whatTime);
            holder.checkBox=convertView.findViewById(R.id.checkBox);

            result=convertView;

            convertView.setTag(holder);

            holder.taskName.setText(thisTask.getTaskName());
            holder.taskDate.setText(thisTask.getDateString());
            holder.taskTime.setText(thisTask.getTimeString());

            final TaskViewHolder finalHolder = holder;
            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finalHolder.checkBox.setImageResource(R.drawable.checked);
                    MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
                    Log.d("TAG", "check: " + mediaPlayer.isPlaying());
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    finalHolder.checkBox.setClickable(false);
                    db.deleteTask(thisTask.getTaskName().trim());
                    Log.d("ABB", "onClick: " + thisTask.getTaskName());

                }
            });

            result.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.custom_delete_todo_dialog);
                    dialog.setCancelable(true);
                    dialog.show();
                    dialog.findViewById(R.id.yesButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            arrayList.remove(getItem(position));
                            db.deleteTask(thisTask.getTaskName().trim());
                            notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(getContext(), "sdfghjmdfghbjsdfghjdfgh", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.findViewById(R.id.noButtton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    return true;
                }
            });

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (TaskViewHolder) convertView.getTag();
            result = convertView;
        }
        return convertView;
    }
    @Override
    public int getCount()
    {
        return arrayList.size();
    }
    @Override
    public Task getItem(int position)
    {
        return arrayList.get(position);
    }

    class TaskViewHolder{
        TextView taskName,taskDate,taskTime;
        ImageView checkBox;

    }
}