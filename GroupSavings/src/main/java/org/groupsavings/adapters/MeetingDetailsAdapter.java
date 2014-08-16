package org.groupsavings.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.domain.MeetingDetails;

import java.util.ArrayList;

/**
 * Created by shashank on 16/8/14.
 */
public class MeetingDetailsAdapter extends ArrayAdapter<MeetingDetails> {

    Context context;
    ArrayList<MeetingDetails> meetingDetails;
    boolean readonly;

    public MeetingDetailsAdapter(Context context, int textViewResourceId, ArrayList<MeetingDetails> objects, boolean readonly) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.meetingDetails = objects;
        this.readonly = readonly;
    }

    @Override
    public int getCount()
    {
        return meetingDetails.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public MeetingDetails getItem(int position) {
        return meetingDetails.get(position);
    }

    @Override
    public View getView(int i, View convert_view, ViewGroup viewGroup) {

        final MeetingDetails detail = meetingDetails.get(i);

        try
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convert_view = inflater.inflate(R.layout.meeting_details_row, viewGroup, false);

            TextView tv_memberName = (TextView) convert_view.findViewById(R.id.tv_meeting_detail_member_name);
            tv_memberName.setText(detail.member.toString());

            final CheckBox ck_attended = (CheckBox) convert_view.findViewById(R.id.ck_meeting_detail_attended);
            ck_attended.setChecked(detail.Attended);
            if(readonly)
            {
                ck_attended.setEnabled(false);
            }

            final EditText et_fineReason = (EditText) convert_view.findViewById(R.id.et_meeting_detail_fine_reason);
            if(detail.FineReason != null) et_fineReason.setText(String.valueOf(detail.FineReason));
            if(readonly)
            {
                et_fineReason.setFocusable(false);
            }
            else
            {
                et_fineReason.setOnFocusChangeListener(new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if(!hasFocus)
                        {
                            // A kind of workaround since this is being called more than once weirdly
                            String prev = detail.FineReason == null ? "" : detail.FineReason;
                            String curr = et_fineReason.getText() == null ? "" : et_fineReason.getText().toString();

                            if(!prev.equals(curr))
                            {
                                detail.FineReason = curr;
                                notifyDataSetChanged();
                            }
                        }
                    }
                });
            }

            final EditText et_fined = (EditText) convert_view.findViewById(R.id.et_meeting_detail_fined_amount);
            if(detail.Fine != 0) et_fined.setText(String.valueOf(detail.Fine));
            if(readonly)
            {
                et_fined.setEnabled(false);
            }
            else
            {
                et_fined.setOnFocusChangeListener(new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if(!hasFocus)
                        {
                            // A kind of workaround since this is being called more than once weirdly
                            float prev = detail.Fine;
                            float curr = 0;
                            try{
                                curr = Integer.valueOf(et_fined.getText().toString());
                            } catch (NumberFormatException ex) {
                                detail.Fine = 0;
                            }

                            if(prev != curr)
                            {
                                detail.Fine = curr;
                                notifyDataSetChanged();
                            }
                        }
                    }
                });
            }

        } catch (Exception ex) {

            Toast.makeText(context,ex.getMessage(),Toast.LENGTH_SHORT).show();
            Log.d("ERROR", ex.getMessage());
        }

        return convert_view;
    }

}
