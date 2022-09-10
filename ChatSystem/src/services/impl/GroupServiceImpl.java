package services.impl;

import models.Group;
import models.User;
import services.GroupService;
import services.UserService;

import java.util.*;

public class GroupServiceImpl implements GroupService {

    private final UserService userService;
    private static final Map<String, Group> groupDB = new HashMap<>();

    public GroupServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String createGroup(String requestingAdminId, String groupName) throws Exception {
        validateUser(requestingAdminId);

        String groupId="";
        do {
            groupId = String.valueOf(UUID.randomUUID());
        } while(groupDB.get(groupId) != null);

        Group newGroup = new Group(groupId, groupName, requestingAdminId);
        groupDB.put(groupId, newGroup);

        return newGroup.getGroupId();
    }

    @Override
    public void deleteGroup(String requestingAdminId, String groupId) throws Exception {
        validateUser(requestingAdminId);

        Group group = groupDB.get(groupId);

        if (group == null) {
            throw new Exception("Group doesn't exist");
        }

        Set<String> adminIdList = group.getGroupAdminIdList();
        if (adminIdList.contains(requestingAdminId)) {
            groupDB.remove(requestingAdminId);
        } else {
            throw new Exception("Unauthorized access");
        }
    }

    @Override
    public void addAdminToGroup(String requestingAdminId, String newAdminId, String groupId) throws Exception {
        validateUser(requestingAdminId);

        Group group = groupDB.get(groupId);

        if (group == null) {
            throw new Exception("Group doesn't exist");
        }

        Set<String> adminIdList = group.getGroupAdminIdList();
        if (adminIdList.contains(requestingAdminId)) {
            if (group.getGroupMemberList().contains(newAdminId)) {
                group.addAdminToGroup(newAdminId);
            } else {
                throw new Exception("member doesn't exist in the group");
            }
        } else {
            throw new Exception("Unauthorized access");
        }
    }

    @Override
    public void removeAdminFromGroup(String requestingAdminId, String newAdminId, String groupId) throws Exception {
        validateUser(requestingAdminId);

        if (Objects.equals(requestingAdminId, newAdminId)) {
            throw new Exception("You can't remove yourself");
        }

        Group group = groupDB.get(groupId);

        if (group == null) {
            throw new Exception("Group doesn't exist");
        }

        Set<String> adminIdList = group.getGroupAdminIdList();
        if (adminIdList.contains(requestingAdminId)) {
            group.removeAdminFromGroup(newAdminId);
        } else {
            throw new Exception("Unauthorized access");
        }
    }

    @Override
    public void addMemberToGroup(String requestingAdminId, String newMemberId, String groupId) throws Exception {
        validateUser(requestingAdminId);

        Group group = groupDB.get(groupId);

        if (group == null) {
            throw new Exception("Group doesn't exist");
        }

        Set<String> adminIdList = group.getGroupAdminIdList();
        if (adminIdList.contains(requestingAdminId)) {
            group.addMemberToGroup(newMemberId);
        } else {
            throw new Exception("Unauthorized access");
        }
    }

    @Override
    public void removeMemberFromGroup(String requestingAdminId, String newMemberId, String groupId) throws Exception {
        validateUser(requestingAdminId);

        if (Objects.equals(requestingAdminId, newMemberId)) {
            throw new Exception("You can't remove yourself");
        }

        Group group = groupDB.get(groupId);

        if (group == null) {
            throw new Exception("Group doesn't exist");
        }

        Set<String> adminIdList = group.getGroupAdminIdList();
        if (adminIdList.contains(requestingAdminId)) {
            group.removeMemberFromGroup(newMemberId);
        } else {
            throw new Exception("Unauthorized access");
        }
    }

    @Override
    public Group fetchGroupDetails(String groupId) throws Exception {
        return groupDB.get(groupId);
    }

    private void validateUser(String userId) throws Exception {
        User user = userService.getUserDetails(userId);

        if (user == null) {
            throw new Exception("User doesn't exist");
        }
    }
}
