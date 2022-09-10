package services.impl;

import models.Group;
import models.MessageResponse;
import models.StoredMessage;
import models.User;
import services.ChatService;
import services.GroupService;
import services.UserService;

import java.util.*;

public class ChatServiceImpl implements ChatService {

    private final GroupService groupService;
    private final UserService userService;

    private static final Map<String, ArrayList<StoredMessage>> messageDB = new HashMap<>();

    public ChatServiceImpl(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    @Override
    public String createGroup(String requestingAdminId, String groupName) throws Exception {
        return groupService.createGroup(requestingAdminId, groupName);
    }

    @Override
    public void deleteGroup(String requestingAdminId, String groupId) throws Exception {
        groupService.deleteGroup(requestingAdminId, groupId);
    }

    @Override
    public void addAdminToGroup(String requestingAdminId, String newAdminId, String groupId) throws Exception {
        groupService.addAdminToGroup(requestingAdminId, newAdminId, groupId);
    }

    @Override
    public void removeAdminFromGroup(String requestingAdminId, String newAdminId, String groupId) throws Exception {
        groupService.removeAdminFromGroup(requestingAdminId, newAdminId, groupId);
    }

    @Override
    public void addMemberToGroup(String requestingAdminId, String newMemberId, String groupId) throws Exception {
        groupService.addMemberToGroup(requestingAdminId, newMemberId, groupId);
    }

    @Override
    public void removeMemberFromGroup(String requestingAdminId, String newMemberId, String groupId) throws Exception {
        groupService.removeMemberFromGroup(requestingAdminId, newMemberId, groupId);
    }

    @Override
    public void sendMessageIntoGroup(String memberId, String groupId, String message) throws Exception {
        validateUser(memberId);
        validateGroup(groupId, memberId);

        ArrayList<StoredMessage> storedMessages = messageDB.get(groupId);

        if (storedMessages == null || storedMessages.isEmpty()) {
            storedMessages = new ArrayList<>();
        }

        storedMessages.add(new StoredMessage(message, String.valueOf(UUID.randomUUID()), memberId, System.currentTimeMillis()));

        System.out.println("Member Id " + memberId + " has sent message: " + message);
        messageDB.put(groupId, storedMessages);
    }

    @Override
    public MessageResponse getMessages(String memberId, String groupId, int lastPaginatedIndex) throws Exception {
        validateUser(memberId);
        validateGroup(groupId, memberId);

        ArrayList<StoredMessage> storedMessages = messageDB.get(groupId);

        if (storedMessages == null || storedMessages.isEmpty()) {
            return new MessageResponse(Collections.EMPTY_LIST, -1);
        }

        if (lastPaginatedIndex < 0) {
            lastPaginatedIndex = 0;
        }

        List<String> messages = new ArrayList<>();
        int count = 3;
        while (lastPaginatedIndex < storedMessages.size() && count > 0) {
            messages.add(storedMessages.get(lastPaginatedIndex).getMessage());
            lastPaginatedIndex = lastPaginatedIndex + 1;
            count = count - 1;
        }

        return new MessageResponse(messages, lastPaginatedIndex);
    }


    private void validateUser(String userId) throws Exception {
        User user = userService.getUserDetails(userId);

        if (user == null) {
            throw new Exception("User doesn't exist");
        }
    }

    private void validateGroup(String groupId, String memberId) throws Exception {
        Group group = groupService.fetchGroupDetails(groupId);

        if (group == null) {
            throw new Exception("Group doesn't exist");
        }

        if (!group.getGroupMemberList().contains(memberId)) {
            System.err.println("MemberId : " + memberId + " doesn't exist inside groupId : " + groupId);
        }
    }
}
