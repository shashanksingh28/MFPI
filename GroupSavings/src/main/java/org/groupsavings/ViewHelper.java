package org.groupsavings;

import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import org.groupsavings.domain.*;

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
            memberIdText.clearFocus();
        }

        EditText firstNameEditor=(EditText) view.findViewById(R.id.edit_member_firstname);
        if(firstNameEditor != null){
            firstNameEditor.setText(memberToPopulate.FirstName);
            firstNameEditor.clearFocus();
        }

        EditText lastNameEditor=(EditText) view.findViewById(R.id.edit_member_lastname);
        if(lastNameEditor !=null){
            lastNameEditor.setText(memberToPopulate.LastName);
            lastNameEditor.clearFocus();
        }

        EditText contactEditor=(EditText) view.findViewById(R.id.edit_member_contact);
        if(contactEditor !=null){
            contactEditor.setText(memberToPopulate.ContactInfo);
            contactEditor.clearFocus();
        }

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

        EditText groupAddressEditor =(EditText) view.findViewById(R.id.edit_group_address);
        if(groupAddressEditor !=null && groupAddressEditor.getText()!= null) updatedGroup.Address = groupAddressEditor.getText().toString();

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
        if(groupNameEditor != null ){
            groupNameEditor.setText(groupToPopulate.GroupName);
            groupNameEditor.clearFocus();
        }

        EditText groupAddressEditor =(EditText) view.findViewById(R.id.edit_group_address);
        if(groupAddressEditor !=null){
            groupAddressEditor.setText(groupToPopulate.Address);
            groupAddressEditor.clearFocus();
        }

        EditText grpRecurringSavingsEditor =(EditText) view.findViewById(R.id.edit_group_recurring_savings);
        if(grpRecurringSavingsEditor !=null){
            grpRecurringSavingsEditor.setText(String.valueOf(groupToPopulate.RecurringSavings));
            grpRecurringSavingsEditor.clearFocus();
        }
    }
}
