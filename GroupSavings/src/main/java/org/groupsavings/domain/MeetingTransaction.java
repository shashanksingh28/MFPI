package org.groupsavings.domain;

/**
 * Created by shashank on 31/3/14.
 */
public class MeetingTransaction {

    public Member member;

    public int groupId;

    public int groupCompulsorySavings;

    public int optionalSavings;

    public MeetingTransaction(int groupId, Member member) {
        this.groupId = groupId;
        this.member = member;
    }

    public int getTotalSavings() {
        return groupCompulsorySavings + optionalSavings;
    }
}
