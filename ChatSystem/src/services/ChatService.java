package services;

import models.MessageResponse;

import java.util.List;

public interface ChatService {

    public String createGroup(String requestingAdminId, String groupName) throws Exception;

    public void deleteGroup(String requestingAdminId, String groupId) throws Exception;

    public void addAdminToGroup(String requestingAdminId, String newAdminId, String groupId) throws Exception;

    public void removeAdminFromGroup(String requestingAdminId, String newAdminId, String groupId) throws Exception;

    public void addMemberToGroup(String requestingAdminId, String newMemberId, String groupId) throws Exception;

    public void removeMemberFromGroup(String requestingAdminId, String newMemberId, String groupId) throws Exception;

    public void sendMessageIntoGroup(String memberId, String groupId, String message) throws Exception;

    public MessageResponse getMessages(String memberId, String groupId, int lastPaginatedIndex) throws Exception;
}
