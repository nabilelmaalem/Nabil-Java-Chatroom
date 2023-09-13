package Project.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.Random; //added

import Project.common.Constants;

public class Room implements AutoCloseable {
    // server is a singleton now so we don't need this
    // protected static Server server;// used to refer to accessible server
    // functions
    private String name;
    private List<ServerThread> clients = new ArrayList<ServerThread>();
    private boolean isRunning = false;
    // Commands
    private final static String COMMAND_TRIGGER = "/";
    private final static String CREATE_ROOM = "createroom";
    private final static String JOIN_ROOM = "joinroom";
    private final static String DISCONNECT = "disconnect";
    private final static String LOGOUT = "logout";
    private final static String LOGOFF = "logoff";
    private final static String FLIP = "flip"; //added
    private final static String ROLL = "roll"; //added
    private final static String MUTE = "mute"; //added
    private final static String UNMUTE = "unmute"; //added
    private static Logger logger = Logger.getLogger(Room.class.getName());

    public Room(String name) {
        this.name = name;
        isRunning = true;
    }

    public String getName() {
        return name;
    }

    public boolean isRunning() {
        return isRunning;
    }

    protected synchronized void addClient(ServerThread client) {
        if (!isRunning) {
            return;
        }
        client.setCurrentRoom(this);
        if (clients.indexOf(client) > -1) {
            logger.warning("Attempting to add client that already exists in room");
        } else {
            clients.add(client);
            client.sendResetUserList();
            syncCurrentUsers(client);
            sendConnectionStatus(client, true);
        }
    }

    protected synchronized void removeClient(ServerThread client) {
        if (!isRunning) {
            return;
        }
        // attempt to remove client from room
        try {
            clients.remove(client);
        } catch (Exception e) {
            logger.severe(String.format("Error removing client from room %s", e.getMessage()));
            e.printStackTrace();
        }
        // if there are still clients tell them this person left
        if (clients.size() > 0) {
            sendConnectionStatus(client, false);
        }
        checkClients();
    }

    private void syncCurrentUsers(ServerThread client) {
        Iterator<ServerThread> iter = clients.iterator();
        while (iter.hasNext()) {
            ServerThread existingClient = iter.next();
            if (existingClient.getClientId() == client.getClientId()) {
                continue;// don't sync ourselves
            }
            boolean messageSent = client.sendExistingClient(existingClient.getClientId(),
                    existingClient.getClientName());
            if (!messageSent) {
                handleDisconnect(iter, existingClient);
                break;// since it's only 1 client receiving all the data, break if any 1 send fails
            }
        }
    }

    /***
     * Checks the number of clients.
     * If zero, begins the cleanup process to dispose of the room
     */
    private void checkClients() {
        // Cleanup if room is empty and not lobby
        if (!name.equalsIgnoreCase(Constants.LOBBY) && (clients == null || clients.size() == 0)) {
            close();
        }
    }

    /***
     * Helper function to process messages to trigger different functionality.
     * 
     * @param message The original message being sent
     * @param client  The sender of the message (since they'll be the ones
     *                triggering the actions)
     */
    @Deprecated // not used in my project as of this lesson, keeping it here in case things
                // change
    private boolean processCommands(String message, ServerThread client) {
        boolean wasCommand = false;
        try {
            if (message.startsWith(COMMAND_TRIGGER)) {
                String[] comm = message.split(COMMAND_TRIGGER);
                String part1 = comm[1];
                String[] comm2 = part1.split(" ");
                String command = comm2[0];
                String roomName;
                wasCommand = true;
                switch (command) {
                    case CREATE_ROOM:
                        roomName = comm2[1];
                        Room.createRoom(roomName, client);
                        break;
                    case JOIN_ROOM:
                        roomName = comm2[1];
                        Room.joinRoom(roomName, client);
                        break;
                    case DISCONNECT:
                    case LOGOUT:
                    case LOGOFF:
                        Room.disconnectClient(client, this);
                        break;
                    //Nabil El Maalem (nre3) 4/19/2023
                    //Roll implementation
                    //Fix this
                    case ROLL:
                        int numDice = Integer.parseInt(comm2[1].substring(0, comm2[1].indexOf("d")));
                        int numSide = Integer.parseInt(comm2[1].substring(comm2[1].indexOf("d") + 1));
                        Random ran = new Random();
                        int rollResult = 0;
                        for (int i = 0; i < numDice; i++) {
                            rollResult += ran.nextInt(numSide) + 1;
                        }
                        Server.INSTANCE.broadcast("*b" + client.getClientName() + " rolled " + numDice + "d" + numSide + " and got " + rollResult + "b*");
                        break;
                    //Flip implementation
                    case FLIP:
                        Random rand = new Random();
                        int coinFlip = rand.nextInt(2);
                        String flipResult;
                        if (coinFlip == 0){
                            flipResult = "Tails";
                        }
                        else {
                            flipResult = "Heads";
                        }
                        Server.INSTANCE.broadcast("*b" + client.getClientName() + " Flipped a coin and got " + flipResult + "b*");
                        break;

                    //Start
                    case MUTE:
                    if (!client.isMuted(comm2[1]))  //Cannot find symbol here (referring to isMuted)
                    {
                        for(ServerThread localclient : clients)
                        {
                            if (localclient.getClientName().equals(comm2[1]))
                            {
                                localclient.sendMessage(Constants.DEFAULT_CLIENT_ID, client.getClientName() + " muted you.");
                                break;
                            }
                        }
                        client.mutedUser(comm2[1]);
                        File muteFile = new File(client.getClientName() + "MutedUsersList.txt");
                        muteFile.delete();
                        File muteFile2 = new File(client.getClientName() + "MutedUserList.txt");
                        FileWriter mutedUsersList = new FileWriter(muteFile2, true);
                        BufferedWriter bw = new BufferedWriter(mutedUsersList);

                        for(String mutedUser : client.mutedUsers)
                        {
                            bw.write(mutedUser);
                            bw.newLine();
                        }
                        bw.close();
                        mutedUsersList.close();
                    }
                    break;
                    
                    case UNMUTE:
                    if (client.isMuted(comm2[1])) 
                    {
                        for(ServerThread localclient : clients)
                        {
                            if (localclient.getClientName().equals(comm2[1]))
                            {
                                localclient.sendMessage(Constants.DEFAULT_CLIENT_ID, client.getClientName() + " unmuted you.");
                                break;
                            }
                        }
                        client.unmutedUser(comm2[1]);
                        File muteFile = new File(client.getClientName() + "MutedUsersList.txt");
                        muteFile.delete();
                        File muteFile2 = new File(client.getClientName() + "MutedUserList.txt");
                        FileWriter mutedUsersList = new FileWriter(muteFile2, true);
                        BufferedWriter bw = new BufferedWriter(mutedUsersList);

                        for(String mutedUser : client.mutedUsers)
                        {
                            bw.write(mutedUser);
                            bw.newLine();
                        }
                        bw.close();
                        mutedUsersList.close();
                    }
                    break;
                    //End
                    
                    default:
                        wasCommand = false;
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wasCommand;
    }

    // Command helper methods
    protected static void getRooms(String query, ServerThread client) {
        String[] rooms = Server.INSTANCE.getRooms(query).toArray(new String[0]);
        client.sendRoomsList(rooms,
                (rooms != null && rooms.length == 0) ? "No rooms found containing your query string" : null);
    }

    protected static void createRoom(String roomName, ServerThread client) {
        if (Server.INSTANCE.createNewRoom(roomName)) {
            Room.joinRoom(roomName, client);
        } else {
            client.sendMessage(Constants.DEFAULT_CLIENT_ID, String.format("Room %s already exists", roomName));
        }
    }

    /**
     * Will cause the client to leave the current room and be moved to the new room
     * if applicable
     * 
     * @param roomName
     * @param client
     */
    protected static void joinRoom(String roomName, ServerThread client) {
        if (!Server.INSTANCE.joinRoom(roomName, client)) {
            client.sendMessage(Constants.DEFAULT_CLIENT_ID, String.format("Room %s doesn't exist", roomName));
        }
    }

    protected static void disconnectClient(ServerThread client, Room room) {
        client.disconnect();
        room.removeClient(client);
    }
    // end command helper methods

    /***
     * Takes a sender and a message and broadcasts the message to all clients in
     * this room. Client is mostly passed for command purposes but we can also use
     * it to extract other client info.
     * 
     * @param sender  The client sending the message
     * @param message The message to broadcast inside the room
     */
    protected synchronized void sendMessage(ServerThread sender, String message) {
        if (!isRunning) {
            return;
        }
        //Nabil El Maalem (nre3) 4/4/23
        //Altered message output
        String startTag = "";
        String endTag = "";
        int startIndex = -1;
        int endIndex = -1;
        boolean processed = true;
        //Nabil El Maalem NRE3 4/19/23
        while(processed){
            processed = false;
            startIndex = message.indexOf("*b");
            endIndex = message.indexOf("b*");
            //bold
            if(startIndex > -1 && endIndex > -1 && endIndex > startIndex+2){
                processed = true;
                startTag = "<b>";
                endTag = "</b>";
                message = message.substring(0, startIndex) + startTag 
                + message.substring(startIndex+2, endIndex) + endTag 
                + message.substring(endIndex+2); 
            }
            //italic
            startIndex = message.indexOf("*i");
            endIndex = message.indexOf("i*");
            if(startIndex > -1 && endIndex > -1 && endIndex > startIndex+2){
                processed = true;
                startTag = "<i>";
                endTag = "</i>";
                message = message.substring(0, startIndex) + startTag 
                + message.substring(startIndex+2, endIndex) + endTag 
                + message.substring(endIndex+2);
            }
            //underline
            startIndex = message.indexOf("*u");
            endIndex = message.indexOf("u*");
            if(startIndex > -1 && endIndex > -1 && endIndex > startIndex+2){
                processed = true;
                startTag = "<u>";
                endTag = "</u>";
                message = message.substring(0, startIndex) + startTag 
                + message.substring(startIndex+2, endIndex) + endTag 
                + message.substring(endIndex+2);
            }
            //red
            startIndex = message.indexOf("#r");
            endIndex = message.indexOf("r#");
            if(startIndex > -1 && endIndex > -1 && endIndex > startIndex+2){
                processed = true;
                startTag = "<font color=\"red\">";
                endTag = "</font>";
                message = message.substring(0, startIndex) + startTag 
                + message.substring(startIndex+2, endIndex) + endTag 
                + message.substring(endIndex+2);
            }
            //green
            startIndex = message.indexOf("#g");
            endIndex = message.indexOf("g#");
            if(startIndex > -1 && endIndex > -1 && endIndex > startIndex+2){
                processed = true;
                startTag = "<font color=\"green\">";
                endTag = "</font>";
                message = message.substring(0, startIndex) + startTag 
                + message.substring(startIndex+2, endIndex) + endTag 
                + message.substring(endIndex+2);
            }
            //blue
            startIndex = message.indexOf("#b");
            endIndex = message.indexOf("b#");
            if(startIndex > -1 && endIndex > -1 && endIndex > startIndex+2){
                processed = true;
                startTag = "<font color=\"blue\">";
                endTag = "</font>";
                message = message.substring(0, startIndex) + startTag 
                + message.substring(startIndex+2, endIndex) + endTag 
                + message.substring(endIndex+2);
            }
            
            //Whisper/Private message
            startIndex = message.indexOf("@");
            if (startIndex > -1) {
                endIndex = message.indexOf(" ", startIndex);
                if (endIndex == -1) {
                    endIndex = message.length();
                }
                String clientName = message.substring(startIndex + 1, endIndex);
                for (ServerThread client : clients) {
                    if (client != sender && client.getClientName().equals(clientName)) {
                        client.sendMessage(sender.getClientId(), message);
                        sender.sendMessage(sender.getClientId(), message);
                        return;
                    }
                }
            }


        } 
        System.out.println(message);
    
        //End

        logger.info(String.format("Sending message to %s clients", clients.size()));
        if (sender != null && processCommands(message, sender)) {
            // it was a command, don't broadcast
            return;
        }
        long from = sender == null ? Constants.DEFAULT_CLIENT_ID : sender.getClientId();
        Iterator<ServerThread> iter = clients.iterator();
        while (iter.hasNext()) {
            ServerThread client = iter.next();
            //This is for the muting of a certain user
            if (sender == null || !client.mutedUsers.contains(sender.getClientName())){
                boolean messageSent = client.sendMessage(from, message);
                if (!messageSent) {
                    handleDisconnect(iter, client);
                }
            }
        }
    }

    protected synchronized void sendConnectionStatus(ServerThread sender, boolean isConnected) {
        Iterator<ServerThread> iter = clients.iterator();
        while (iter.hasNext()) {
            ServerThread receivingClient = iter.next();
            boolean messageSent = receivingClient.sendConnectionStatus(
                    sender.getClientId(),
                    sender.getClientName(),
                    isConnected);
            if (!messageSent) {
                handleDisconnect(iter, receivingClient);
            }
        }
    }

    private void handleDisconnect(Iterator<ServerThread> iter, ServerThread client) {
        iter.remove();
        logger.info(String.format("Removed client %s", client.getClientName()));
        sendMessage(null, client.getClientName() + " disconnected");
        checkClients();
    }

    public void close() {
        Server.INSTANCE.removeRoom(this);
        isRunning = false;
        clients.clear();
    }
}