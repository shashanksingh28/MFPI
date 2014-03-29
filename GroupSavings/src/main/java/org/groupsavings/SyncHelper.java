package org.groupsavings;

import org.groupsavings.database.DatabaseHandler;
import org.groupsavings.domain.*;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shashank on 16/3/14.
 */
public class SyncHelper {
    public static JSONObject getJsonGroup(Group group)
    {
        JSONObject groupJSON = new JSONObject();
        try {
            groupJSON.put(DatabaseHandler.COLUMN_GROUP_UID,group.UID);
            groupJSON.put(DatabaseHandler.COLUMN_GROUP_NAME,group.GroupName);
            groupJSON.put(DatabaseHandler.COLUMN_GROUP_ADDRESS,group.Address);
            groupJSON.put(DatabaseHandler.COLUMN_RECURRING_SAVING,group.RecurringSavings);
            groupJSON.put(DatabaseHandler.COLUMN_CREATED_DATETIME,group.CreatedAt);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return groupJSON;
    }

    public static JSONArray GetAllGroupsJSON(ArrayList<Group> groups)
    {
        JSONArray jsonArray = new JSONArray();
        for (Group group : groups) {
            jsonArray.put(getJsonGroup(group));
        }
        return jsonArray;
    }

    public static ArrayList<NameValuePair> getNameValuePairs(Group group)
    {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(DatabaseHandler.COLUMN_GROUP_UID,String.valueOf(group.UID)));
        nameValuePairs.add(new BasicNameValuePair(DatabaseHandler.COLUMN_GROUP_NAME,group.GroupName));

        return nameValuePairs;
    }

}