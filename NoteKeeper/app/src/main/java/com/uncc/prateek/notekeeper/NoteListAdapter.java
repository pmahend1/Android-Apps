package com.uncc.prateek.notekeeper;

/**
 * Created by Prateek on 27-02-2017.
 */


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;


import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class NoteListAdapter extends ArrayAdapter<Note> {
    Context context;
    int resource;
    String logText = "NoteListAdapter";
    //DatabaseDataManager dm ;
    DataManager dm ;


    ArrayList<Note> appList = new ArrayList<>();
    Set<String> favSet = new HashSet<String>();
    boolean favouritesAct = false;


    public NoteListAdapter(Context context, int resource, ArrayList<Note> objects,DataManager dm) {
        super(context, resource, objects);
        this.appList = objects;
        this.context = context;
        this.resource = resource;
        //dm = new DatabaseDataManager(context);
        this.dm = dm;
        //new DataManager(context);

    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Note note    = null;
        if (convertView == null) {
            if(dm==null){
                dm = new DataManager(context);
            }
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.tvNoteText = (TextView) convertView.findViewById(R.id.textView_noteText);
            holder.tvPriority = (TextView) convertView.findViewById(R.id.textView_priority);
            holder.tvTime = (TextView) convertView.findViewById(R.id.textView_time);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        try {
            note = appList.get(position);
            if (note != null) {
                Log.d(logText, "Note inside adapter : " + note.toString());
                holder.tvNoteText .setText(note.getNote());
                holder.tvPriority.setText(note.getPriority());
                String timeText =null;
                try{
                    DateFormat formatter = new SimpleDateFormat();
                    Date date = formatter.parse(note.getTime());
                    PrettyTime t = new PrettyTime(new Date(6000));
                    timeText = t.format(date) + "ago";
                }catch (Exception e){
                    Log.d("ed","Exc in time setting" +e.toString()+"");
                }

                if(timeText!=null){
                    holder.tvTime.setText(timeText);
                }else{
                    holder.tvTime.setText(note.getTime());
                }


                String status = note.getStatus();
                if(status.equals("pending")){
                    holder.checkBox.setChecked(false);
                }else if(status.equals("completed")){
                    holder.checkBox.setChecked(true);
                }else{
                    Log.d("Log","Check box to NLL");
                }

                final ViewHolder finalHolder = holder;
                final Note finalNote = note;
                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isChecked = finalHolder.checkBox.isChecked();
                        if(isChecked){
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            //Yes button clicked
                                            finalNote.setPriority("completed");
                                            Log.d("finalNote ",finalNote.toString());
                                            if(dm!=null){
                                                dm.updateNote(finalNote);
                                            }else{
                                    Log.d("dd","dm is NULL");
                                            }

                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            Log.d("alert Add", "No clicked ");
                                            //finalHolder.star.setChecked(false);
                                            finalHolder.checkBox.setChecked(false);
                                            break;
                                    }
                                }
                            };
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Change Status");
                            builder.setMessage("Do you want to mark is as completed?").setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener).show();



                        }else{
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            //Yes button clicked
                                            finalNote.setPriority("pending");
                                            dm.updateNote(finalNote);
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            Log.d("alert Add", "No clicked ");
                                            finalHolder.checkBox.setChecked(true);
                                            break;
                                    }
                                }
                            };
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Change Status");
                            builder.setMessage("Do you want to mark is as pending?").setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener).show();


                        }

                    }
                });
            }

        } catch (Exception e) {
            Log.d(logText, e.toString());
            Log.d(logText, e.getStackTrace() + "");
        }


        return convertView;
    }


    static class ViewHolder {
        TextView tvNoteText;
        TextView tvPriority;
        TextView tvTime;
        CheckBox checkBox;
    }


}