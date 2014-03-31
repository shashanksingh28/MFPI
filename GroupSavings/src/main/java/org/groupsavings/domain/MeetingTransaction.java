package org.groupsavings.domain;

/**
 * Created by shashank on 31/3/14.
 */
public class MeetingTransaction {
    public int memberId;

    public Member member;

    public int groupCompulsorySavings;

    public int optionalSavings;

    public int totalSavings;

    public MeetingTransaction(Member member) {
        this.member = member;
    }
}
