import models.MessageResponse;
import models.User;
import services.ChatService;
import services.GroupService;
import services.UserService;
import services.impl.ChatServiceImpl;
import services.impl.GroupServiceImpl;
import services.impl.UserServiceImpl;

public class Server {
    public static void main(String [] args) throws Exception {
        final UserService userService = new UserServiceImpl();
        final GroupService groupService = new GroupServiceImpl(userService);
        final ChatService chatService = new ChatServiceImpl(groupService, userService);

        // Add members
        userService.registerUser(new User("1", "Suraj", "111"));
        userService.registerUser(new User("2", "Akash", "222"));
        userService.registerUser(new User("3", "Namrata", "333"));
        userService.registerUser(new User("4", "Sanjeet", "444"));

        userService.registerUser(new User("5", "Pika", "555"));
        userService.registerUser(new User("6", "JJ", "666"));

        // First group
        String familyGroupId = chatService.createGroup("1", "Family Group");
        chatService.addMemberToGroup("1", "2", familyGroupId);
        chatService.addMemberToGroup("1", "3", familyGroupId);

        chatService.addAdminToGroup("1", "3", familyGroupId);

        chatService.addMemberToGroup("3", "4", familyGroupId);

        // Second group
        String friendGroupId = chatService.createGroup("6", "Friend Group");
        chatService.addMemberToGroup("6", "5", friendGroupId);
        chatService.addMemberToGroup("6", "1", friendGroupId);


        // Send message into group
        chatService.sendMessageIntoGroup("1", familyGroupId, "Welcome To the group");
        chatService.sendMessageIntoGroup("2", familyGroupId, "Happy New year");

        MessageResponse fourthMessageResponse = chatService.getMessages("4", familyGroupId, 0);
        printMessage("4", fourthMessageResponse);

        MessageResponse thirdMessageResponse = chatService.getMessages("3", familyGroupId, 0);
        printMessage("3", thirdMessageResponse);
        chatService.sendMessageIntoGroup("3", familyGroupId, "Why is this group?");

        MessageResponse firstMessageResponse = chatService.getMessages("1", familyGroupId, 0);
        printMessage("1", firstMessageResponse);


        // un-authorized message access/send
        chatService.getMessages("5", familyGroupId, 0);
        chatService.sendMessageIntoGroup("6", familyGroupId, "Hello everyone");
    }

    private static void printMessage(String memberId, MessageResponse response) {
        System.out.println("Member Id : " + memberId + " received these messages:");
        response.getMessage().forEach(System.out::println);
    }
}
