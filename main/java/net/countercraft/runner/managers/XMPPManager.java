
package net.countercraft.runner.managers;

//Java Imports
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//Local Imports
import net.countercraft.runner.Controller;
import net.countercraft.runner.XMPP.InboundHandler;

//Jivesoftware Imports
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class XMPPManager {
    private List<Chat> connectionList = new ArrayList<Chat>();
    private ChatManager chatManager;
    private XMPPConnection connection;
    
    private XMPPManager() {
    }
    
    public static XMPPManager getInstance() {
        return XMPPManagerHolder.INSTANCE;
    }
    
    private static class XMPPManagerHolder {

        private static final XMPPManager INSTANCE = new XMPPManager();
    }
    
    public void connect(){
        
        try {
            ConnectionConfiguration xmppConfig = new ConnectionConfiguration("talk.google.com", 5222, "gmail.com");
            connection = new XMPPConnection(xmppConfig);
            
            connection.connect();
            
            SASLAuthentication.supportSASLMechanism("PLAIN", 0);
            //Get data
            String user = Controller.getSettings().USERNAME;
            String password = Controller.getSettings().PASSWORD;
            connection.login(user, password);
            chatManager = connection.getChatManager();
            for(String name : Controller.getSettings().ADMIN_LIST){
                
                    createAndAddToChat(name);
                    chat(name).sendMessage("Runner has activated on server : " + Controller.getPluginInstance().getServer().getServerName());
                
           }
            
            for(String name : Controller.getSettings().USER_LIST){
                createAndAddToChat(name);
                chat(name).sendMessage("Runner has activated on server : " + Controller.getPluginInstance().getServer().getServerName());
            }
            chatManager.addChatListener(new ChatManagerListener() {

                public void chatCreated(Chat chat, boolean createdLocally) {
                    if (!createdLocally && !chatExactlyExists(chat)){
                        if(Controller.getSettings().OPEN_CHANNEL){
                            try {
                                chat.sendMessage("You have now been added to this server's chat room.");
                            } catch (XMPPException ex) {
                                Logger.getLogger(XMPPManager.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            createAndAddToChat(chat);
                            
                        }else{
                            
                            if(Controller.getSettings().isUser(correctAdress(chat.getParticipant()))){
                                try {
                                    chat.sendMessage("You have now been added to this server's chat room.");
                                } catch (XMPPException ex) {
                                    Logger.getLogger(XMPPManager.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                createAndAddToChat(chat);
                                
                            }else{
                                try {
                                    chat.sendMessage("You do not have permission to join this server's chat. Please contact an admin.");
                                } catch (XMPPException ex) {
                                    Logger.getLogger(XMPPManager.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
            });
             
                       
             
             } catch (XMPPException ex) {
                System.out.println("Failure to connect : " + ex.getMessage());
                
            }
   }
   
   private void createAndAddToChat(Chat chat){
       connectionList.add(chatManager.createChat(correctAdress(chat.getParticipant()), new InboundHandler()));
   }
   
   private void createAndAddToChat(String name){
       connectionList.add(chatManager.createChat(name, new InboundHandler()));
   }
   
   private void removeFromConnectionList(String participant){
       for(Chat c : connectionList){
           if(c.getParticipant().equalsIgnoreCase(participant)){
               connectionList.remove(c);
           }
       }
   }
   
   private boolean chatExactlyExists(Chat chat){
       for(Chat c : connectionList){
           if(c.getParticipant().equalsIgnoreCase(chat.getParticipant())){
               return true;
           }
       } 
       return false;
    }
   private boolean chatExists(Chat chat){
       for(Chat c : connectionList){
           if(correctAdress(c.getParticipant()).equalsIgnoreCase(correctAdress(chat.getParticipant()))){
               return true;
           }
       }
       
       return false;
   }
   
   public void sendAll(String message){
       for(Chat c : connectionList){
            try {
                c.sendMessage(message);
            } catch (XMPPException ex) {
                Logger.getLogger(XMPPManager.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
   }
   
   public void sendAllExcept(String message, String exceptionName){
       for(Chat c : connectionList){
           if(!c.getParticipant().equalsIgnoreCase(exceptionName)){
                try {
                    c.sendMessage(message);
                } catch (XMPPException ex) {
                    Logger.getLogger(XMPPManager.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
       }
   }
   
   public void sendToAdmins(String message){
     
       for(String s : Controller.getSettings().ADMIN_LIST){
            try {
                chat(s).sendMessage("[Admin Priority Message] " + message);
            } catch (XMPPException ex) {
                Logger.getLogger(XMPPManager.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
   }
   
   public Chat chat(String participantName){
       for(Chat c : connectionList){
           if(c.getParticipant().equalsIgnoreCase(participantName)){
               return c;
           }
       }
       
       return null;
   }
   
   public String correctAdress(String contact){
       if(contact.contains("@gmail.com")){
         return contact.split("@gmail.com")[0] + "@gmail.com";
       }
       
       return contact;
                            
   }
   
   public void close() throws XMPPException{
       for(String name : Controller.getSettings().ADMIN_LIST){
           chat(name).sendMessage("Runner remote console is now closing down.");
       }
       
       for(String name : Controller.getSettings().USER_LIST){
           chat(name).sendMessage("Runner remote chat is now closing down.");
       }
       
       connection.disconnect();
       
   }
        
}
