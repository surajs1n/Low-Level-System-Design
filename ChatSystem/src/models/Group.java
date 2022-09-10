package models;

import java.util.HashSet;
import java.util.Set;

public class Group {
    private String groupId;
    private String groupName;
    private final Set<String> groupAdminIdList;

    public Set<String> getGroupMemberList() {
        return groupMemberList;
    }

    private final Set<String> groupMemberList;

    public Group(String groupId, String groupName, String groupAdminId) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupAdminIdList =  new HashSet<>();
        groupAdminIdList.add(groupAdminId);
        this.groupMemberList = new HashSet<>(this.groupAdminIdList);
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<String> getGroupAdminIdList() {
        return groupAdminIdList;
    }

    public void addAdminToGroup(String newAdminId) {
        this.groupAdminIdList.add(newAdminId);
    }

    public void removeAdminFromGroup(String newAdminId) throws Exception {
        if (this.groupAdminIdList.size() > 1) {
            this.groupAdminIdList.remove(newAdminId);
        }

        throw new Exception("We should have at least one admin in the group");
    }

    public void addMemberToGroup(String newMemberId) {
        this.groupMemberList.add(newMemberId);
    }

    public void removeMemberFromGroup(String newMemberId) {
        this.groupMemberList.remove(newMemberId);
        this.groupAdminIdList.remove(newMemberId);
    }
}
