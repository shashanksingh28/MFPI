package org.groupsavings.domain;

import android.text.format.DateFormat;

/**
 * Created by shashank on 8/3/14.
 */
public class Group {

    public int UID;
    public String GroupName;
    public String Address;
    public int FOId;
    public int PresidentId;
    public int RecurringSavings;
    public String CreatedAt;
    public int CreatedBy;

    @Override
    public String toString()
    {
        return GroupName;
    }

}
