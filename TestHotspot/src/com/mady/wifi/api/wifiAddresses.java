/* 
 * Copyright (C) 2013-2014 www.Andbrain.com 
 * Faster and more easily to create android apps
 * 
 * */
package com.mady.wifi.api;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;



@SuppressLint("NewApi")
public class wifiAddresses {
	
	 public enum INSTRUCTION 	 { 	CMD,ARP,DNS,PORT,ALL}
	 
	  Context mContext;
      WifiManager mWifiManager;
      WifiInfo  mWifiInfo ;
      com.mady.wifi.datatransfer.SimpleAsynTask mTask;
      public boolean isDnsLive=false;
      public  ArrayList<String> addresses=new ArrayList<String>() ;
	  private final static String REG_E = "^%s\\s+0x1\\s+0x2\\s+([:0-9a-fA-F]+)\\s+\\*\\s+\\w+$";
	  private final static int BUFFER = 8 * 1024;
	  private final static int[] PORTS = { 139, 445, 22, 80 ,7,13};
	 
	  
	 public wifiAddresses(Context c) {
		 
		 mContext=c;
		 mWifiManager=(WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		 mWifiInfo = mWifiManager.getConnectionInfo();
		 mTask=new com.mady.wifi.datatransfer.SimpleAsynTask();
		
	  }
	/**
  	 * Method to Get Gateway Ip Address
  	 * 
  	 * @return Gateway Ip Address as String 
  	 */
    public String getGatewayIPAddress(){
	 if(mWifiManager!=null){
		 final DhcpInfo dhcp = mWifiManager.getDhcpInfo();
		 return ipIntToString(dhcp.gateway);
	    }
	   return null;
    }
    /**
     *  Method to Get Device Ip Address 
     * 
     * @return  Device Ip Address as String
     */
    public String getDeviceIPAddress(){
	 if(mWifiInfo!=null){
		 int ip = mWifiInfo.getIpAddress();
		 return ipIntToString(ip);
	 }
     	return null;
    } 

    /**
     * Method for Conversion Ip Address From Int to String
     * 
     * @param ipInt Ip as Int
     * @return Ip as String
     */
    public static String ipIntToString(int ipInt) {
        String ip = "";
        for (int i = 0; i < 4; i++) {
            ip = ip + ((ipInt >> i * 8) & 0xFF) + ".";
        }
        return ip.substring(0, ip.length() - 1);
    }
   
    /**
  	 * Method to Get Device MAC Address
  	 * 
   	 * @return MAC Address as String
  	 */
    public String getDeviceMacAddress(){
	 if(mWifiInfo!=null){
		 return  mWifiInfo.getMacAddress();
	 }
	return null;
}
    /**
  	 * Method to Get MAC Address of GatWay(BSSID)
  	 * 
  	 * @return String contain MAC Address of GatWay
  	 */
    public String getGatWayMacAddress(){
		 if(mWifiManager!=null){
			 return mWifiInfo.getBSSID();
		 }
		return null;
	}
    /**
 	 * Method to Ping  IP Address
 	 * 
 	 * @param addr IP address you want to ping it 
 	 * @return true if the IP address is reachable
 	 */
 	public boolean pingCmd(String addr){
 		try {
			String ping = "ping  -c 1 -W 1 " + addr;
    		Runtime run = Runtime.getRuntime();
			Process pro = run.exec(ping);
			 try {
				pro.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	     	int exit = pro.exitValue();
	        if (exit == 0) { 
	        	 return true;
	         } else {
	        	 //ip address is not reachable
	           return false;
	         }
		}
			catch (IOException e) {
			}
		return false;
	}  	
 	/**
 	 * Method to Get Result of pinging  IP Address
 	 * 
 	 * @param addr  IP address you want to ping it 
 	 * @return Result of Pinging as String
 	 */
    public String getPingResulta(String addr){
		try {
			String ping = "ping -c 1 -W 1 " + addr;
			String pingResult = "";
			Runtime run = Runtime.getRuntime();
			Process pro = run.exec(ping);
			
			BufferedReader buf = new BufferedReader(new	InputStreamReader(pro.getInputStream()));
			
			String inputLine;
			while ((inputLine = buf.readLine()) != null) {
						pingResult += inputLine;
			}
			      buf.close();
			return pingResult;
		}
			catch (IOException e) {
			}
		
	return "";
	}
   /**
     * Method to Get MAC Address From  ARP File
     * 
     * @param ip  address you want to Get it MAC Address
     * @return MAC Address as String
     */
    public String getArpMacAddress(String addr){
			String macAddr = "00:00:00:00:00:00";
	        try {
	            if (addr != null) {
	                String ptrn = String.format(REG_E, addr.replace(".", "\\."));
	                Pattern pat = Pattern.compile(ptrn);
	                            
	                BufferedReader buf = new BufferedReader(new FileReader("/proc/net/arp"), BUFFER);
	                String line;
	                Matcher mat;
	                while ((line = buf.readLine()) != null) {
	                    mat = pat.matcher(line);
	                    if (mat.matches()) {
	                    	macAddr = mat.group(1);
	                        break;
	                    }
	                }
	                buf.close();
	            } else {
	            }
	        } catch (IOException e) {
	             return macAddr;
	        }
	        return macAddr;
	           
		}
    
    
    /**
  	 * Method to Clear ARP table not tested
  	 * 
  	 * @return true if ARP table cleared or false if not
  	 */
    public boolean clearArpTable(){
    	 return runAsRoot("ip -s -s neigh flush all");
	   
    }
    boolean gotRoot=false;
    public void CheckRoot()
    {                    
        Process pro;
        try {  
               pro = Runtime.getRuntime().exec("su");   
               DataOutputStream outStr = new DataOutputStream(pro.getOutputStream()); 
               
               outStr.writeBytes("echo \"salam alikoum\" >/data/Test.txt\n");  
               outStr.writeBytes("exit\n");  
               outStr.flush();  
               
               try {  
                  pro.waitFor();  
                       if (pro.exitValue() == 0) {  
                          this.gotRoot=true;
                       }  
                       else {  
                            this.gotRoot=false;  
                       }  
               } catch (InterruptedException e) {  
            	   this.gotRoot=false;
               }  
            } catch (IOException e) {  
             	 this.gotRoot=false;
            }  
    }
   	/**
	 * Run command as root.
	 * 
	 * @param command
	 * @return true, if command was successfully executed
	 */
	private static boolean runAsRoot(final String command) {
		try {
								
			Process pro = Runtime.getRuntime().exec("su");
			DataOutputStream outStr = new DataOutputStream(pro.getOutputStream());
			
			 outStr.writeBytes(command);
			 outStr.writeBytes("\nexit\n");
			 outStr.flush();
			 
			int retval = pro.waitFor();
			
			return (retval == 0);
			
		    } catch (Exception e) {
		    	
			return false;
			
		    }
	}
    /**
  	 * Method to Check if  IP Live Using ARP
  	 * 
  	 * @addr ip address you want to check it
  	 * @return true if the ip address is reachable
  	 */
   public boolean arpIsALive(String addr){
		 
	       if(!"00:00:00:00:00:00".equals( getArpMacAddress(addr))){
	         return true;
	        }else{return false;}
			
		}
   
    /**
  	 * Method to Check if IP Live Using DNS
  	 * 
  	 * @addr ip address you want to check it
  	 * @return true if the ip address is reachable
  	 */
   
   public void dnsLive(final String addr,final Runnable task){
	   mTask.runAsynTask(new Runnable() {
    	    public void run() {
    	    	isDnsLive=dnsIsALive(addr);
    	    	task.run();
    	    	
    	    }	
     	    	
     	} );
   }
   public boolean dnsIsALive(String addr){
	        
	        try {
	          
	            InetAddress inetAddr = InetAddress.getByName(addr);
	            if (inetAddr.isReachable(100)) {
	                  return true;
	            }else{
	            	 return false;
	            }
	           
	       } catch (IOException e) {
	        	 return false;
	        } 
	       

	}
   
  
    /**
     * Method to Check if IP Live Using Socket And PORT
     * 
     * @addr ip address you want to check it
  	 * @return true if the ip address is reachable
     */
   public void portLive(final String addr,final Runnable task){
	   mTask.runAsynTask(new Runnable() {
    	    public void run() {
    	    	isDnsLive=portIsALive(addr);
    	    	task.run();
    	    	
    	    }	
     	    	
     	} );
   }
   public boolean portIsALive(String addr) {
		boolean  isLive=false;
		Socket soc = new Socket();
        for (int i = 0; i < PORTS.length; i++) {
            try {
                soc.bind(null);
                soc.connect(new InetSocketAddress(addr, PORTS[i]),100);
                isLive=true;
            } catch (IOException e) {
         	   isLive=false;
            } catch (IllegalArgumentException e) {
         	   isLive=false;
            } finally {
                try {
                    soc.close();
                } catch (Exception e){
                }
            }
        }
	          return isLive;
	          
		}
	    
    /**
     * Method to scan IP Addresses Using PING ARP 
     * 
     * @return ArrayList<String> all ip addresses are  reachable
     */
   
   public ArrayList<String> getAllDevicesIp() {
	   addresses.clear();
	   if(!getGatewayIPAddress().contentEquals("0.0.0.0")){
		   
		   addresses.add(getGatewayIPAddress());  
		   
	   }
	   if(!getDeviceIPAddress().contentEquals("0.0.0.0")){
		 
		   addresses.add(getDeviceIPAddress());  
	   }
	   ArrayList<String> results3 = this.getArpLiveIps(true);
	      
       for (String result : results3) {
           if(!addresses.contains(result)){
        	   addresses.add(result) ; 
           }
           }
       int start=getLastIpNubmer(getDeviceIPAddress());
       String sub=SubIp(getDeviceIPAddress());
       
       for(int i=start+1;i<start+6;i++)
		{
    	   if(((start+6)<255)||((start+6)<255)){
    		  
    		   if(pingCmd(sub+Integer.toString(i))){
    			   if(!addresses.contains(sub+Integer.toString(i))){
    				      addresses.add(sub+Integer.toString(i)) ; 
    	               }  
			   }
    		  
    	   }
    	   
		}
	  
      
       for(int i=start-1;i>start-6;i--)
		{
   	   if(((start-1)>0)||((start-6)>0)){
   		
   		  
   		   if(pingCmd(sub+Integer.toString(i))){
   			   if(!addresses.contains(sub+Integer.toString(i))){
   				      addresses.add(sub+Integer.toString(i)) ; 
   	               }  
			   }
   		 
   	   }
   	   
		}
		
       return addresses;
}
   public int getLastIpNubmer(String myIp){
	  
	    String s=myIp.substring( myIp.indexOf(".")+1);
	    String s2=s.substring( s.indexOf(".")+1);
	    return Integer.parseInt(s2.substring( s2.indexOf(".")+1));
	    
	  
   }
   public String SubIp(String myIp){
	  
	   return myIp.substring(0,getIndexOfStr(myIp,".",3))+".";
   }
   public int getIndexOfStr(String myIp,String mystr,int rept){
	  int indexOf=0;
	  String s=myIp;
	   while(rept>0&&!s.isEmpty()){
		   indexOf+=s.indexOf(mystr)+1;
		   s=s.substring(s.indexOf(mystr)+1);
			   rept--;
	   }
	return indexOf-1;
	   
   }
     
   /**
     * Gets a list of all clients Ip Addresses connected to the Hotspot from ARP file
     * @param onlyReachables  false if the list should contain unreachable clients or true otherwise
     * @return ArrayList of all clients Ip Addresses 
     */
    public ArrayList<String> getArpLiveIps(boolean onlyReachables) {
                BufferedReader bufRead = null;
                ArrayList<String> result = null;
               
                try {
                        result = new ArrayList<String>();
                        bufRead = new BufferedReader(new FileReader("/proc/net/arp"));
                        String fileLine;
                        while ((fileLine = bufRead.readLine()) != null) {
                        	
                        	 
                                String[] splitted = fileLine.split(" +");
                                
                                  if ((splitted != null) && (splitted.length >= 4)) {
                                	  
                                        String mac = splitted[3];
                                         if (mac.matches("..:..:..:..:..:..")) {
                                              boolean isReachable = pingCmd(splitted[0]);
                                             if (!onlyReachables || isReachable) {
                                                        result.add(splitted[0]);
                                                }
                                        }
                                }
                        }
                } catch (Exception e) {
                       
                } finally {
                        try {
                        	bufRead.close();
                        } catch (IOException e) {
                               
                        }
                }

                return result;
        }
    
    
     /**
      * Method to Change  static IP address, netmask, gateway 
      * <uses-permission android:name="android.permission.WRITE_SETTINGS"></uses-permission>
      */
    public void setStaticIpInfo(String ip,String netMask,String gateWay,String dns1,String dns2){
        	final ContentResolver cr = mContext.getContentResolver();
        	Settings.System.putInt(cr, Settings.System.WIFI_USE_STATIC_IP, 1);
        	Settings.System.putString(cr, Settings.System.WIFI_STATIC_IP, ip);
        	Settings.System.putString(cr, Settings.System.WIFI_STATIC_GATEWAY, gateWay);
        	Settings.System.putString(cr, Settings.System.WIFI_STATIC_NETMASK, netMask);
        	Settings.System.putString(cr, Settings.System.WIFI_STATIC_DNS1, dns1);
        	Settings.System.putString(cr, Settings.System.WIFI_STATIC_DNS2, dns2);
        }
        /**
         * Method to Manually Change  static IP address, netmask, gateway 
         * 
         */
    public void startStaticIpIntent(){
        	   mContext.startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
          }
    
    
    


}
