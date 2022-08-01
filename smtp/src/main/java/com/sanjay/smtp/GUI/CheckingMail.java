package com.sanjay.smtp.GUI;


import java.security.Key;
import java.util.Properties;
 
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;


import java.util.*;
public class CheckingMail {
 
    /**
     * @param args
     */
    static String subjectm="";
    static String bodym="";
    public static void receiveMail(String Email,String password) throws Exception {
        Scanner scn = new Scanner(System.in);
        Properties props = new Properties();
        			
        	props.put("mail.smtp.host","smtp.gmail.com");
        	props.put("mail.smtp.socketFactory.port", "465");
        	props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        	props.put("mail.smtp.auth", "true");
        	props.put("mail.smtp.port", "465");
        

        Session session = Session.getDefaultInstance(props, null);
        Store store = session.getStore("imaps");
        try{
            store.connect("smtp.gmail.com", Email,password);
        
        
            System.out.println(store);
    
            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_WRITE); // Folder.READ_ONLY
            int messageCount = inbox.getMessageCount();
            System.out.println("Total Messages " + messageCount);
            int startMessage = messageCount - 5;
            int endMessage = messageCount;
    
            if (messageCount < 5) {
                startMessage = 0;
            }
    
            Message[] messages = inbox.getMessages(startMessage, endMessage);
            Message message = messages[messages.length-1];
            String content= message.getContent().toString();
            System.out.println(content);
            System.out.println(message.getSubject());
            subjectm = message.getSubject();
            int sizeOfByte = 16;
            int emhalflength = (content.length()/2)-((sizeOfByte/2)+1);
            String dkeyvalue = content.substring(emhalflength,emhalflength+sizeOfByte);
            // System.out.println(dkeyvalue);
            StringBuffer result = new StringBuffer();
            int s=22;
            for (int i=0; i<dkeyvalue.length(); i++){
                if ((dkeyvalue.charAt(i)>='A' && dkeyvalue.charAt(i)<='Z') || (dkeyvalue.charAt(i)>='a' && dkeyvalue.charAt(i)<='z')){
                    if (Character.isUpperCase(dkeyvalue.charAt(i))){
                        char ch = (char)(((int)dkeyvalue.charAt(i) + s - 65) % 26 + 65);
                        result.append(ch);
                    }
                    else
                    {
                        char ch = (char)(((int)dkeyvalue.charAt(i) + s - 97) % 26 + 97);
                        result.append(ch);
                    }
                }else {
                    result.append(dkeyvalue.charAt(i));
                }
            }
            String resultkey = result.toString();
            // System.out.println(resultkey);
            String msg = content.substring(0,emhalflength) + content.substring(emhalflength+sizeOfByte,content.length()); 
            // System.out.println(msg);
            Encryption enc = new Encryption(resultkey);
            Key key = enc.generateKey();
            String decryptedvalue = enc.decrypt(msg, key);
            System.out.print("Decrypted value is : ");
            System.out.println(decryptedvalue);
            bodym=decryptedvalue;
            inbox.close(true);
            System.out.println("Done....");
            store.close();
            scn.close();
        }catch(Exception e){
            System.out.println("Invalid Inputs");
        }
        }
        public static String subject(){
            if (subjectm==""){
                return "NULL";
            }else {
                return subjectm;
            }
        }
        public static String body(){
            if (bodym==""){
                return "NULL";
            }else {
                return bodym;
            }
        }
}