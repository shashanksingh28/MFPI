package org.groupsavings.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shashank on 30/3/14.
 */
public class GroupMeeting {
    public int id;
    public int groupId;
    public String date;

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        //return sdf.format(date);
        return date;
    }
}
