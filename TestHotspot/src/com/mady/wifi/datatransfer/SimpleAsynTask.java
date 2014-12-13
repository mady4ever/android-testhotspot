package com.mady.wifi.datatransfer;


import android.os.AsyncTask;
import android.util.Log;

public class SimpleAsynTask {
	
	 public void runAsynTask(Runnable task){
   	  
     	  new simpleTask(task).execute();
     	  
     	  }
     class simpleTask extends AsyncTask<Object,Void,String>
     {
       
         private Runnable  mTask;

         protected simpleTask(Runnable task)
         {
             super();
             mTask = task;
            
         }

         protected void onPreExecute()
         {
       	      	 Log.i("Receive file :: ", "Starting... ");
         }

         protected void onPostExecute(String success)
         {
             if (success == null)
             {
           	   	 Log.i("Receive file :: ", "succeed");
             }
             else
             {
           	 
            	 Log.e("Receive file :: ", "Failed with exception '" + success);
            }
         }

         protected String doInBackground(final Object... args)
         {
         	
             String success = null;
             try 
             {
            	 mTask.run();
                
             }
             catch (Exception e)
             {
               success = e.toString();
             }

             return success;
         }
     }   
    
}
