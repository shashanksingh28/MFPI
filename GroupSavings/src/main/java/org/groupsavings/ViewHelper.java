package org.groupsavings;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.groupsavings.domain.Group;
import org.groupsavings.domain.Member;

/**
 * Created by shashank on 4/3/14.
 * Contains UI helper functions to populate and fetch data from UI
 */
public class ViewHelper {

    public static void populateMemberDetailsToView(View view_layout,Member memberToPopulate)
    {
        if(view_layout == null) return;

        View view = view_layout.findViewById(R.id.layout_member_details);

        if(view == null || memberToPopulate == null) return;

        TextView memberIdText = (TextView) view.findViewById(R.id.layout_member_uid);
        if(memberIdText != null){
            memberIdText.setText(String.valueOf(memberToPopulate.UID));
        }

        EditText firstNameEditor=(EditText) view.findViewById(R.id.edit_member_firstname);
        firstNameEditor.setText(memberToPopulate.FirstName);

        EditText lastNameEditor=(EditText) view.findViewById(R.id.edit_member_lastname);
        lastNameEditor.setText(memberToPopulate.LastName);


        EditText contactEditor=(EditText) view.findViewById(R.id.edit_member_contact);
        contactEditor.setText(memberToPopulate.ContactInfo);

        EditText et_memberAge = (EditText) view.findViewById(R.id.edit_member_age);
        if (memberToPopulate.age==0) {
            et_memberAge.setText("");
        }
        else
            et_memberAge.setText(String.valueOf(memberToPopulate.age));

        TextView tv_member_savings = (TextView) view.findViewById(R.id.textview_member_savings);
        if (memberToPopulate.TotalSavings==0) {
            tv_member_savings.setText("");
        }
        else
            tv_member_savings.setText(String.valueOf(memberToPopulate.TotalSavings));

    }

    public static Member fetchMemberDetailsFromView(View viewLayout)
    {
        if(viewLayout == null) return null;

        View view = viewLayout.findViewById(R.id.layout_member_details);

        if(view == null) return null;

        Member updatedMember = new Member();

        TextView memberIdText = (TextView) view.findViewById(R.id.layout_member_uid);
        if(memberIdText != null && memberIdText.getText() != null)
        {
            String uid_string = memberIdText.getText().toString();
            if(uid_string != null && !uid_string.isEmpty())
                updatedMember.UID = Integer.parseInt(uid_string);
        }

        EditText firstNameEditor=(EditText) view.findViewById(R.id.edit_member_firstname);
        if(firstNameEditor != null && firstNameEditor.getText()!= null) updatedMember.FirstName = firstNameEditor.getText().toString();

        EditText lastNameEditor=(EditText) view.findViewById(R.id.edit_member_lastname);
        if(lastNameEditor !=null && lastNameEditor.getText()!= null) updatedMember.LastName = lastNameEditor.getText().toString();

        EditText contactEditor=(EditText) view.findViewById(R.id.edit_member_contact);
        if(contactEditor !=null && contactEditor.getText() != null) updatedMember.ContactInfo = contactEditor.getText().toString();

        EditText et_memberAge = (EditText) view.findViewById(R.id.edit_member_age);
        if (et_memberAge != null && et_memberAge.getText() != null)
            updatedMember.age = Integer.valueOf(et_memberAge.getText().toString());


        return updatedMember;
    }

    public static Group fetchGroupDetailsFromView(View view_Layout)
    {
        if(view_Layout == null) return null;

        View view = view_Layout.findViewById(R.id.layout_group_details);

        if(view == null) return null;

        Group updatedGroup = new Group();

        TextView memberIdText = (TextView) view.findViewById(R.id.view_group_uid);
        if(memberIdText != null && memberIdText.getText() != null)
        {
            String uid_string = memberIdText.getText().toString();
            if(uid_string != null && !uid_string.isEmpty())
                updatedGroup.UID = Integer.parseInt(uid_string);
        }

        EditText groupNameEditor =(EditText) view.findViewById(R.id.edit_group_name);
        if(groupNameEditor != null && groupNameEditor.getText()!= null) updatedGroup.GroupName = groupNameEditor.getText().toString();

        EditText groupAddress1Editor = (EditText) view.findViewById(R.id.edit_group_addressline1);
        if (groupAddress1Editor != null && groupAddress1Editor.getText() != null)
            updatedGroup.AddressLine1 = groupAddress1Editor.getText().toString();

        EditText groupAddress2Editor = (EditText) view.findViewById(R.id.edit_group_addressline2);
        if (groupAddress2Editor != null && groupAddress2Editor.getText() != null)
            updatedGroup.AddressLine2 = groupAddress2Editor.getText().toString();

        EditText grpRecurringSavingsEditor =(EditText) view.findViewById(R.id.edit_group_recurring_savings);
        if(grpRecurringSavingsEditor !=null && grpRecurringSavingsEditor.getText() != null)
        {
            String savings = grpRecurringSavingsEditor.getText().toString();
            if(savings != null && !savings.isEmpty())
                updatedGroup.RecurringSavings = Integer.parseInt(savings);
        }

        return updatedGroup;
    }

    public static void populateGroupDetailsToView(View view_layout,Group groupToPopulate)
    {
        if(view_layout == null) return;

        View view = view_layout.findViewById(R.id.layout_group_details);

        if(view == null) return;

        TextView memberIdText = (TextView) view.findViewById(R.id.view_group_uid);
        if(memberIdText != null ){
            memberIdText.setText(String.valueOf(groupToPopulate.UID));
            memberIdText.clearFocus();
        }

        EditText groupNameEditor =(EditText) view.findViewById(R.id.edit_group_name);
        groupNameEditor.setText(groupToPopulate.GroupName);

        EditText groupAddress1Editor = (EditText) view.findViewById(R.id.edit_group_addressline1);
        groupAddress1Editor.setText(groupToPopulate.AddressLine1);

        EditText groupAddress2Editor = (EditText) view.findViewById(R.id.edit_group_addressline2);
        groupAddress2Editor.setText(groupToPopulate.AddressLine2);

        EditText grpRecurringSavingsEditor =(EditText) view.findViewById(R.id.edit_group_recurring_savings);
        grpRecurringSavingsEditor.setText(String.valueOf(groupToPopulate.RecurringSavings));

    }
}
