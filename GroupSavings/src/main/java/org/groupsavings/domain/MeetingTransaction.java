package org.groupsavings.domain;

/**
 * Created by shashank on 31/3/14.
 */
public class MeetingTransaction {

    public Member GroupMember;

    public SavingTransaction SavingTransaction;

    public LoanTransaction LoanTransaction;

    public int groupId;

    public MeetingTransaction(int groupId, Member member) {
        this.groupId = groupId;
        this.GroupMember = member;
        this.SavingTransaction = new SavingTransaction();
        this.LoanTransaction = new LoanTransaction();
    }
}
