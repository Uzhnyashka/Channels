package com.bobyk.channels.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.bobyk.channels.ChannelContract;
import com.bobyk.channels.R;

import java.util.Calendar;

/**
 * Created by bobyk on 25/07/16.
 */
public class ScheduleAdapter extends CursorAdapter {
    LayoutInflater layoutInflater;

    public ScheduleAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.schedule_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder();
        TextView tvProgramName = (TextView) view.findViewById(R.id.program_name);
        TextView tvProgramTime = (TextView) view.findViewById(R.id.program_time);
        viewHolder.tvProgramName = tvProgramName;
        viewHolder.tvProgramTime = tvProgramTime;
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        String programName = cursor.getString(cursor.getColumnIndex(ChannelContract.ProgramEntry.COLUMN_TVSHOW_NAME));
        long programTime = cursor.getLong(cursor.getColumnIndex(ChannelContract.ProgramEntry.COLUMN_DATE));
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(programTime);
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder != null){
            viewHolder.tvProgramName.setText(programName);
            viewHolder.tvProgramTime.setText(time.get(Calendar.HOUR_OF_DAY) + ":" + time.get(Calendar.MINUTE));
            viewHolder.programID = id;
        }
    }

    public static class ViewHolder{
        public TextView tvProgramName;
        public TextView tvProgramTime;
        public long programID;
    }
}

