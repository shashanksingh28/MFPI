package org.groupsavings;

import org.groupsavings.domain.Group;
import org.groupsavings.handlers.DatabaseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shashank on 16/3/14.
 */
public class SyncHelper {
    public static JSONObject getJsonGroup(Group group)
    {
        JSONObject groupJSON = new JSONObject();
        try {
            groupJSON.put(DatabaseHandler.COLUMN_GROUP_HashId, group.UID);
            groupJSON.put(DatabaseHandler.COLUMN_GROUP_Name, group.GroupName);
            groupJSON.put(DatabaseHandler.COLUMN_GROUP_AddressLine1, group.Address);
            groupJSON.put(DatabaseHandler.COLUMN_GROUP_RecurringIndividualAmount, group.RecurringSavings);
            groupJSON.put(DatabaseHandler.COLUMN_GROUP_CreatedDate, group.CreatedAt);
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

}