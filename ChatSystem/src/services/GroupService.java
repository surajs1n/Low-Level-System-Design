package services;

import models.Group;

public interface GroupService {

    public String createGroup(String requestingAdminId, String groupName) throws Exception;

    public void deleteGroup(String requestingAdminId, String groupId) throws Exception;

    public void addAdminToGroup(String requestingAdminId, String newAdminId, String groupId) throws Exception;

    public void removeAdminFromGroup(String requestingAdminId, String newAdminId, String groupId) throws Exception;

    public void addMemberToGroup(String requestingAdminId, String newMemberId, String groupId) throws Exception;

    public void removeMemberFromGroup(String requestingAdminId, String newMemberId, String groupId) throws Exception;

    public Group fetchGroupDetails(String groupId) throws Exception;
}
