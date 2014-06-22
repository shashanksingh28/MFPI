package org.groupsavings.domain;

/**
 * Created by shashank on 22/6/14.
 * Class that encapsulates different types of Saving Acc related transactions
 * that a member can do on a meeting. Each row in UI corresponds to one such object
 */
public class MeetingSavingsAccTransaction {

    Group Group;

    Member Member;

    SavingsAccount SavingsAccount;

    SavingTransaction CompulsorySavingTransaction;

    SavingTransaction OptionalSavingTransaction;

    public MeetingSavingsAccTransaction(Group group, Member member, SavingsAccount account)
    {
        this.Group = group;
        this.Member = member;
        this.SavingsAccount = account;
        this.CompulsorySavingTransaction = new SavingTransaction();
        CompulsorySavingTransaction.Type = "C";
        CompulsorySavingTransaction.Amount = group.MonthlyCompulsoryAmount;
        this.OptionalSavingTransaction = new SavingTransaction();
        OptionalSavingTransaction.Type = "O";
    }
}
