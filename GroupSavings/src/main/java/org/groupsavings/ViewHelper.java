package org.groupsavings;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.groupsavings.domain.Group;
import org.groupsavings.domain.Member;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
            memberIdText.setText(String.valueOf(memberToPopulate.Id));
        }

        TextView firstNameEditor=(TextView) view.findViewById(R.id.tv_member_firstname);
        firstNameEditor.setText(memberToPopulate.FirstName);

        TextView lastNameEditor=(TextView) view.findViewById(R.id.tv_member_lastname);
        lastNameEditor.setText(memberToPopulate.LastName);

        EditText contactEditor=(EditText) view.findViewById(R.id.edit_member_contact);
        contactEditor.setText(memberToPopulate.ContactNumber);

        TextView tv_memberAge = (TextView) view.findViewById(R.id.tv_member_age);
        if (memberToPopulate.DOB != null) {
            SimpleDateFormat sdf= new SimpleDateFormat("dd/mm/yyyy");
            try {
                Date dob = sdf.parse(memberToPopulate.DOB);
                Calendar c_dob = Calendar.getInstance();
                c_dob.setTime(dob);
                Calendar c_now = Calendar.getInstance();
                int years = c_now.get(Calendar.YEAR) - c_dob.get(Calendar.YEAR);
                if(c_now.get(Calendar.MONTH) >= c_dob.get(Calendar.MONTH))
                {
                    if(c_now.get(Calendar.DAY_OF_MONTH) >= c_dob.get(Calendar.DAY_OF_MONTH))
                    {
                        // Diff in exact years is correct
                    }
                    else {
                        years--;
                    }
                }
                tv_memberAge.setText(String.valueOf(years));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        EditText add1Editor=(EditText) view.findViewById(R.id.et_member_addressline1);
        add1Editor.setText(memberToPopulate.AddressLine1);

        EditText add2Editor=(EditText) view.findViewById(R.id.et_member_addressline2);
        add2Editor.setText(memberToPopulate.AddressLine2);

        EditText passBookEditor=(EditText) view.findViewById(R.id.edit_pass_book_no);
        passBookEditor.setText(memberToPopulate.Passbook);

        EditText guardianNameEditor=(EditText) view.findViewById(R.id.edit_guardian_name);
        guardianNameEditor.setText(memberToPopulate.GuardianName);

        EditText economicConditionEditor=(EditText) view.findViewById(R.id.edit_economic_condition);
        economicConditionEditor.setText(memberToPopulate.EconomicCondition);

        EditText educationEditor=(EditText) view.findViewById(R.id.edit_education_qualification);
        educationEditor.setText(memberToPopulate.Education);

        EditText occupationEditor=(EditText) view.findViewById(R.id.edit_member_occupation);
        occupationEditor.setText(memberToPopulate.Occupation);

        EditText noOfFamilyMembersEditor=(EditText) view.findViewById(R.id.edit_count_family_members);
        noOfFamilyMembersEditor.setText(String.valueOf(memberToPopulate.NoOfFamilyMembers));

        TextView tv_member_savings = (TextView) view.findViewById(R.id.textview_member_savings);
        if (memberToPopulate.TotalSavings==0) {
            tv_member_savings.setText("");
        }
        else
            tv_member_savings.setText(String.valueOf(memberToPopulate.TotalSavings));

        TextView tv_member_outstanding = (TextView) view.findViewById(R.id.tv_member_total_outstanding);
        if (memberToPopulate.OutstandingLoan == 0) {
            tv_member_outstanding.setText("");
        }
        else
            tv_member_outstanding.setText(String.valueOf(memberToPopulate.OutstandingLoan));

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
                updatedMember.Id = Integer.parseInt(uid_string);
        }

        EditText firstNameEditor=(EditText) view.findViewById(R.id.edit_member_firstname);
        if(firstNameEditor != null && firstNameEditor.getText()!= null) updatedMember.FirstName = firstNameEditor.getText().toString();

        EditText lastNameEditor=(EditText) view.findViewById(R.id.edit_member_lastname);
        if(lastNameEditor !=null && lastNameEditor.getText()!= null) updatedMember.LastName = lastNameEditor.getText().toString();

        EditText contactEditor=(EditText) view.findViewById(R.id.edit_member_contact);
        if(contactEditor !=null && contactEditor.getText() != null) updatedMember.ContactNumber = contactEditor.getText().toString();

        EditText add1Editor=(EditText) view.findViewById(R.id.et_member_addressline1);
        if(add1Editor.getText() != null) updatedMember.AddressLine1 = add1Editor.getText().toString();

        EditText add2Editor=(EditText) view.findViewById(R.id.et_member_addressline2);
        if(add2Editor.getText() != null) updatedMember.AddressLine2 = add2Editor.getText().toString();

        EditText passBookEditor = (EditText) view.findViewById(R.id.edit_pass_book_no);
        if(passBookEditor.getText() != null) updatedMember.Passbook = passBookEditor.getText().toString();

        EditText guardianNameEditor = (EditText) view.findViewById(R.id.edit_guardian_name);
        if(guardianNameEditor.getText() != null) updatedMember.GuardianName = guardianNameEditor.getText().toString();

        EditText economicConditionEditor = (EditText) view.findViewById(R.id.edit_economic_condition);
        if(economicConditionEditor.getText() != null) updatedMember.EconomicCondition = economicConditionEditor.getText().toString();

        EditText educationEditor = (EditText) view.findViewById(R.id.edit_education_qualification);
        if(educationEditor.getText() != null) updatedMember.Education = educationEditor.getText().toString();

        EditText occupationEditor = (EditText) view.findViewById(R.id.edit_member_occupation);
        if(occupationEditor.getText() != null) updatedMember.Occupation = occupationEditor.getText().toString();

        EditText noOfFamilyMembersEditor = (EditText) view.findViewById(R.id.edit_count_family_members);
        if(noOfFamilyMembersEditor.getText() != null)
        {
            String noffamilymembers = noOfFamilyMembersEditor.getText().toString();
            if(noffamilymembers != null && !noffamilymembers.isEmpty())
                updatedMember.NoOfFamilyMembers = Integer.parseInt(noffamilymembers);
        }
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
                updatedGroup.Id = Integer.parseInt(uid_string);
        }

        EditText groupNameEditor =(EditText) view.findViewById(R.id.edit_group_name);
        if(groupNameEditor != null && groupNameEditor.getText()!= null) updatedGroup.Name = groupNameEditor.getText().toString();

        EditText groupAddress1Editor = (EditText) view.findViewById(R.id.edit_group_addressline1);
        if (groupAddress1Editor != null && groupAddress1Editor.getText() != null)
            updatedGroup.AddressLine1 = groupAddress1Editor.getText().toString();

        EditText groupAddress2Editor = (EditText) view.findViewById(R.id.edit_group_addressline2);
        if (groupAddress2Editor != null && groupAddress2Editor.getText() != null)
            updatedGroup.AddressLine2 = groupAddress2Editor.getText().toString();

        Spinner president_spinner = (Spinner) view.findViewById(R.id.sp_group_president);
        if(president_spinner.getSelectedItem() != null && president_spinner.getSelectedItem() != null)
            updatedGroup.President = ((Member)(president_spinner.getSelectedItem()));

        Spinner secretary_spinner = (Spinner) view.findViewById(R.id.sp_group_secretary);
        if(secretary_spinner.getSelectedItem() != null && secretary_spinner.getSelectedItem() != null)
            updatedGroup.Secretary = ((Member)(secretary_spinner.getSelectedItem()));

        Spinner treasurer_spinner = (Spinner) view.findViewById(R.id.sp_group_president);
        if(treasurer_spinner.getSelectedItem() != null && treasurer_spinner.getSelectedItem() != null)
            updatedGroup.Treasurer = ((Member)(treasurer_spinner.getSelectedItem()));

        EditText grpRecurringSavingsEditor =(EditText) view.findViewById(R.id.edit_group_recurring_savings);
        if(grpRecurringSavingsEditor !=null && grpRecurringSavingsEditor.getText() != null)
        {
            String savings = grpRecurringSavingsEditor.getText().toString();
            if(savings != null && !savings.isEmpty())
                updatedGroup.RecurringSavings = Integer.parseInt(savings);
        }

        EditText grpNoOfSubgroupsEditor =(EditText) view.findViewById(R.id.edit_group_no_of_subgroups);
        if(grpNoOfSubgroupsEditor !=null && grpNoOfSubgroupsEditor.getText() != null)
        {
            String subgroups = grpNoOfSubgroupsEditor.getText().toString();
            if(subgroups != null && !subgroups.isEmpty())
                updatedGroup.NoOfSubgroups = Integer.parseInt(subgroups);
        }

        EditText groupBankAccountEditor = (EditText) view.findViewById(R.id.edit_group_bank_account);
        if (groupBankAccountEditor != null && groupBankAccountEditor.getText() != null)
            updatedGroup.Bank = groupBankAccountEditor.getText().toString();

        TextView grpMonthlyMeetDate = (TextView) view.findViewById(R.id.tv_group_mmd);
        if (grpMonthlyMeetDate != null && grpMonthlyMeetDate.getText() != null)
            updatedGroup.MonthlyMeetingDate = grpMonthlyMeetDate.getText().toString();


        return updatedGroup;
    }

    public static void populateGroupDetailsToView(View view_layout,Group groupToPopulate)
    {
        if(view_layout == null) return;

        View view = view_layout.findViewById(R.id.layout_group_details);

        if(view == null) return;

        TextView memberIdText = (TextView) view.findViewById(R.id.view_group_uid);
        if(memberIdText != null ){
            memberIdText.setText(String.valueOf(groupToPopulate.Id));
            memberIdText.clearFocus();
        }

        EditText groupNameEditor =(EditText) view.findViewById(R.id.edit_group_name);
        groupNameEditor.setText(groupToPopulate.Name);

        EditText groupAddress1Editor = (EditText) view.findViewById(R.id.edit_group_addressline1);
        groupAddress1Editor.setText(groupToPopulate.AddressLine1);

        EditText groupAddress2Editor = (EditText) view.findViewById(R.id.edit_group_addressline2);
        groupAddress2Editor.setText(groupToPopulate.AddressLine2);

        Spinner grpPresidentName = (Spinner) view.findViewById(R.id.sp_group_president);
        grpPresidentName.setContentDescription(String.valueOf(groupToPopulate.President));

        Spinner grpSecretaryName = (Spinner) view.findViewById(R.id.sp_group_secretary);
        grpSecretaryName.setContentDescription(String.valueOf(groupToPopulate.Secretary));

        Spinner grpTreasurerName = (Spinner) view.findViewById(R.id.sp_group_treasurer);
        grpTreasurerName.setContentDescription(String.valueOf(groupToPopulate.Treasurer));

        EditText grpRecurringSavingsEditor =(EditText) view.findViewById(R.id.edit_group_recurring_savings);
        grpRecurringSavingsEditor.setText(String.valueOf(groupToPopulate.RecurringSavings));

        EditText grpNoOfSubgroupsEditor =(EditText) view.findViewById(R.id.edit_group_no_of_subgroups);
        grpNoOfSubgroupsEditor.setText(String.valueOf(groupToPopulate.NoOfSubgroups));

        EditText groupBankAccountEditor = (EditText) view.findViewById(R.id.edit_group_bank_account);
        groupBankAccountEditor.setText(groupToPopulate.Bank);

        TextView grpMonthlyMeetDate = (TextView) view.findViewById(R.id.tv_group_mmd);
        if(grpMonthlyMeetDate != null ){
            grpMonthlyMeetDate.setText(String.valueOf(groupToPopulate.MonthlyMeetingDate));
            grpMonthlyMeetDate.clearFocus();
        }

    }
}
