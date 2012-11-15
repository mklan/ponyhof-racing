/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.ponyhofgang.chat3000;

import com.google.android.gcm.GCMRegistrar;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.Random;

/**
 * Helper class used to communicate with the demo server.
 */
public final class ServerUtilities {

    private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static String SERVER_URL = "http://thm-chat.appspot.com";
    private static String TAG = "chat3000";
    private static String GROUP = "ws1213";
    private static final Random random = new Random();
    
    
    private static Vector<String[]> kontakte;
    
    private static String username;
    

    /**
     * Register this account/device pair within the server.
     *
     * @return whether the registration succeeded or not.
     */
    static boolean register(final Context context, final String regId) {
        Log.i(TAG, "registering device (regId = " + regId + ")");
        String serverUrl = SERVER_URL + "/group";
        Map<String, String> params = new HashMap<String, String>();
        
        
        
        params.put("regId", regId);
        params.put("groupId", GROUP);
        params.put("username", username);
        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        // Once GCM returns a registration id, we need to register it in the
        // demo server. As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            Log.d(TAG, "Attempt #" + i + " to register");
            try {
                
                post(serverUrl, params, true);
                GCMRegistrar.setRegisteredOnServer(context, true);
                
                return true;
            } catch (IOException e) {
                // Here we are simplifying and retrying on any error; in a real
                // application, it should retry only on unrecoverable errors
                // (like HTTP error code 503).
                Log.e(TAG, "Failed to register on attempt " + i, e);
                if (i == MAX_ATTEMPTS) {
                    break;
                }
                try {
                    Log.d(TAG, "Sleeping for " + backoff + " ms before retry");
                    Thread.sleep(backoff);
                } catch (InterruptedException e1) {
                    // Activity finished before we complete - exit.
                    Log.d(TAG, "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return false;
                }
                // increase backoff exponentially
                backoff *= 2;
            }
        }
        
        return false;
    }
    
    
    static boolean send(final String fromRegId, final String toMemId, final String msgText) {
        
        
                String urlString = "http://thm-chat.appspot.com/chat";
        		String charset = "UTF-8";
        		
        		
        		
        			HttpURLConnection urlConnection = null;
        			try{
        				String query = "fromRegId=" + fromRegId + "&toMemId=" + toMemId + "&msgTxt=" +  URLEncoder.encode(msgText);
        				
        				URL url = new URL(urlString + "?" + query);
        				Log.v("test", "URL: " + url.toString());
        				
        				urlConnection = (HttpURLConnection) url.openConnection();
        				urlConnection.setRequestProperty("Accept-Charset", charset);		
        				BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), charset));
        				
        			}
        			catch(MalformedURLException e){
        				e.printStackTrace();
        			}
        			catch(IOException e){
        				e.printStackTrace();
        			}
        			finally{
        				if(urlConnection != null){
        					urlConnection.disconnect();
        				}
        			}
        		
                
          
                return true;
           
        }
        
      
    

   

    /**
     * Issue a POST request to the server.
     *
     * @param endpoint POST address.
     * @param params request parameters.
     *
     * @throws IOException propagated from POST.
     */
    private static void post(String endpoint, Map<String, String> params, boolean returnValues)
            throws IOException {
        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        String body = bodyBuilder.toString();
        Log.v(TAG, "Posting '" + body + "' to " + url);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
        	
        	
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            
            if (returnValues){
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = rd.readLine()) != null){
				sb.append(line + '\n');
			}
			String response = sb.toString();
			Log.v("test", "Antwort vom Server: " + response);
			
			String[] zeilen = response.split("\\n");
			kontakte = new Vector<String[]>();
			
			for(int i = 0; i<zeilen.length; i++){
				
				String id = zeilen[i].split(",")[0];
				String name = zeilen[i].split(",")[1];
				
				kontakte.add(new String[]{id, name});
				
				Log.v("test", id + ": " + name);
			}
			
		}
        }
		catch(MalformedURLException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if(conn != null){
				conn.disconnect();
			}
		}
		
	}
    
    
	static Vector<String[]> getContacts(){
    	
    	return kontakte;
    }
            
            
	static void setUsername(String usernameNew){
		
		username = usernameNew;
		
	}
            
        
        
      
}
