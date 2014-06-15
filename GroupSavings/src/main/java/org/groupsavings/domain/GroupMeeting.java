package org.groupsavings.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shashank on 30/3/14.
 */
public class GroupMeeting {
    public String Id;
    public String GroupId;
    public String Date;
    public String FieldOfficerId;

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        //return sdf.format(date);
        return Date;
    }
}
