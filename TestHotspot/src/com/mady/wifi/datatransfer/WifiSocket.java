/* 
 * Copyright (C) 2013-2014 www.Andbrain.com 
 * Faster and more easily to create android apps
 * 
 * */
package com.mady.wifi.datatransfer;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import android.annotation.SuppressLint;
import android.content.Context;


public class WifiSocket {
	static final int BUFFER=1024;
	 Context mContext;
	 SimpleAsynTask mTask;
	 public String receivedMessage="";
	 
	public WifiSocket(Context c){
		mContext=c;
		mTask=new SimpleAsynTask();
	}
	
	 
	
	 
	
	 /**
	  * Method to send message text in  Network.
	  * 
	  * @param ipAddr ,port  receiver of message text,sendText
	  */
	public void sendMessage(final String ipAddr,final int port,final String sendText){
		   mTask.runAsynTask(new Runnable() {
	    	    public void run() {
	    	    	SendText(ipAddr,port,sendText);
	    	    }	
	     	    	
	     	} );
	   }
	
     @SuppressLint("NewApi")
     public static void SendText(String ipAddr, int port, String sendText) 
         {
    	 try {
     		     Socket client;
    		     PrintWriter printwriter;
    		     client = new Socket(ipAddr, port);  //connect to server
    		     printwriter = new PrintWriter(client.getOutputStream(),true);
    		     printwriter.write(sendText);  //write the message to output stream
    		     printwriter.flush();
      		     printwriter.close();
    		     client.close();   //closing the connection
     		     } catch (UnknownHostException e) {
    		
    		         e.printStackTrace();
    		 
    		     } catch (IOException e) {
     		          e.printStackTrace();
    		     }
       }
     /**
	  * Method to Receive message text From  Network .
	  * 
	  *@param port use to  open internal port to receiver message text,
	  *       numMessags number of messages want to receive ,
	  *       task instructions  you want to execute when receive message
	  */
     public void receiveMessage(final int port,final int numMessags,final Runnable task){
		   mTask.runAsynTask(new Runnable() {
	    	    public void run() {
	    	    	ReceiveText(port,numMessags,task);
	    	    	
	    	    }	
	     	    	
	     	} );
	   }
     
     
     public   String ReceiveText(int port,int numMessages,Runnable task) 
     {
    	  ServerSocket serverSocket=null;
	      Socket clientSocket;
	      InputStreamReader inputStreamReader;
	      BufferedReader bufferedReader;
	      String message="";
    
	      try {
			serverSocket = new ServerSocket(port);
		  } catch (IOException e) {
			   e.printStackTrace();
		  }  
    		      
    		
    	 try {
    		 int i=0;
     		while(i<numMessages){
             clientSocket = serverSocket.accept();   //accept the client connection
    		 inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
    		 bufferedReader = new BufferedReader(inputStreamReader); //get the client message
    		 message = bufferedReader.readLine();
    		 receivedMessage=message;
    		 task.run();
    		 inputStreamReader.close();
    		 clientSocket.close();
    		 bufferedReader.close();
    		 i++;
     		}
 
    	  } catch (IOException ex) {
     		                  
            }
    		 
    	 try {
			serverSocket.close();
		} catch (IOException e) {
 			e.printStackTrace();
		}	 
    return message;		     

 }   
     
     
    /******************************************************
/**
 * Method to send File in  Network.
 * 
 * @param ipAddr ,port  receiver of file
 * @param filePath  path of file you want to send 
 */	
     
     public void sendFile(final String ipAddr,final int port,final String filePath){
		   mTask.runAsynTask(new Runnable() {
	    	    public void run() {
	    	    	
	    	    	sendFileSocket(ipAddr,port,filePath);	
	    	    	
	    	    }	
	     	    	
	     	} );
	   }
     
      @SuppressLint("NewApi")
      public static void sendFileSocket(String host, int port, String fileName) 
          {
    	  
            try{
            	
    	       Socket socket = new Socket(host, port);
               DataOutputStream dataOS = new DataOutputStream(socket.getOutputStream());
          
               File file = new File(fileName);
               int size = (int)(file.length());
               dataOS.writeInt(size);

               FileInputStream inpStr = new FileInputStream(file);
               OutputStream outStr = socket.getOutputStream();

               int read;
               byte[] buffer = new byte[BUFFER];
               while ((read = inpStr.read(buffer, 0, BUFFER)) > 0){
        	     outStr.write(buffer, 0, read);
               }
             
               outStr.flush();
               outStr.close();
               dataOS.close();
               inpStr.close();
               socket.close();
             
            } catch (IOException e) {
		          e.printStackTrace();
		     }
       }
  /**
   * Method to Receive File From  Network .
   * 
   * @param port use to  open internal port to receiver file 
   * @param filePath path for saving the file received  
   */
      public void receiveFile(final int port,final String filePath){
		   mTask.runAsynTask(new Runnable() {
	    	    public void run() {
	    	    	
	    	    	receiveFileSocket(port,filePath);	
	    	    	
	    	    }	
	     	    	
	     	} );
	   }
      
     
      public static  void receiveFileSocket(int port, String fileName){
  	 
     try{	  
         ServerSocket server = new ServerSocket(port);
         Socket socket = server.accept();
         DataInputStream dataOS = new DataInputStream(socket.getInputStream());
      
         int size = dataOS.readInt();
         FileOutputStream outStr = new FileOutputStream(fileName);
         InputStream inpStr = socket.getInputStream();

         byte[] buffer = new byte[BUFFER];
         ByteArrayOutputStream byteOutStr = new ByteArrayOutputStream();
         while (size >= BUFFER){
          size -= getChunk(inpStr, byteOutStr, buffer);
          outStr.write(byteOutStr.toByteArray());
          byteOutStr.reset();
        }
 
         int data = 0;
         while ((data = inpStr.read()) != -1)
        	 byteOutStr.write(data); 
      
         	 outStr.write(byteOutStr.toByteArray());
         	 server.close();
         	 socket.close();
         	 inpStr.close();
         	 dataOS.close();
         	 outStr.close();
     } catch (IOException e) {
          e.printStackTrace();
      }
     }
      
      static int getChunk(InputStream inputStr, ByteArrayOutputStream bytes, byte[] buffer) throws
                  IOException{
           int read = inputStr.read(buffer, 0, BUFFER);
           while (read < BUFFER){
               read += inputStr.read(buffer, read, BUFFER - read);
            }
           bytes.write(buffer);
           return read;
      }
	  
}
