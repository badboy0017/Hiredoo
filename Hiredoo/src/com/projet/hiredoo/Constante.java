package com.projet.hiredoo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.utility.hiredoo.ProfileReader;
import com.utility.hiredoo.SimpleCrypto;

public class Constante {
	
	// Variables
	public static final String file_ini = "params.ini";
	public static final String ini_email = "email";
	public static final String ini_password = "password";
	public static final String ini_remember = "remember_me";
	public static final String ini_type = "type";
	public static final String ini_id = "id";
	public static final String ini_type_recruiter = "recruiter";
	public static final String ini_type_jobseeker = "jobseeker";
	public static final String secret_key = "c3265jVeW69A49g";
	
	public static final String url = "http://192.168.1.2:8080/Hiredo/";
	public static final String user = "user/";
	public static final String user_login = "user/login/";
	public static final String enterprise = "entreprise/";
	public static final String enterprise_login = "entreprise/login/";
	
	/* Format fichier INI:
	[application]
	email=the_email
	password=the_password_encrypted
	rememberme=true | false
	type=recruter | jobseeker
	id=1 (son ID dans la table de la BD)
	*/
		
	//Fonctions
	public static String getINIvalue(Context context, String value) {
		ProfileReader ini_reader = new ProfileReader();
		try {
			ini_reader.load(context.openFileInput(Constante.file_ini));
		}
		catch (FileNotFoundException ex) {
			Toast.makeText(context, "File not found Exception: \n" + ex.getMessage(), Toast.LENGTH_LONG).show(); 
		}
		catch (Exception ex) {
			Toast.makeText(context, "File exception\n" + ex.getMessage(), Toast.LENGTH_LONG).show(); 
		}
		
		return ini_reader.getProperty("application", value);
	}
		
	public static void createINIFile(Context context) {
		
		FileOutputStream fout = null;
        OutputStreamWriter stream = null;
        
        try {
        	fout = context.openFileOutput(Constante.file_ini, Context.MODE_PRIVATE);
            stream = new OutputStreamWriter(fout);
            stream.write("[application]\n" + Constante.ini_email + "=null\n" + Constante.ini_password + "=null\n" + Constante.ini_remember + "=false\n" + Constante.ini_type + "=null\n" + Constante.ini_id + "=null");
            stream.flush();
        }
        catch (Exception ex) {
        	Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    	
        try {
          stream.close();
          fout.close();
    	}
    	catch (IOException ex) {
    		Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
    	}
        catch (Exception ex) {
        	Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
    	}
	}
	
	public static void saveINIFile(Context context, String email, String password, boolean rememberme, String type, int id) {
		
		FileOutputStream fout = null;
        OutputStreamWriter stream = null;
        
        try {
        	fout = context.openFileOutput(Constante.file_ini, Context.MODE_PRIVATE);
            stream = new OutputStreamWriter(fout);
            stream.write("[application]\n" + Constante.ini_email + "=" + email + "\n" + Constante.ini_password + "=" + password + "\n" + Constante.ini_remember + "=" + rememberme + "\n" + Constante.ini_type + "=" + type + "\n" + Constante.ini_id + "=" + id);
            stream.flush();
        }
        catch (Exception ex) {
        	Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    	
        try {
          stream.close();
          fout.close();
    	}
    	catch (IOException ex) {
    		Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
    	}
        catch (Exception ex) {
        	Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
    	}
	}
	
	public static String readINIFile(Context context) {
		
		FileInputStream fin = null;
        InputStreamReader stream = null;
        char[] inputBuffer;
        String data = null;
 
        try	{
        	fin = context.openFileInput(Constante.file_ini);
        	
        	if(fin.available() == 0) // Fichier vide
        		return "";
        	
        	inputBuffer = new char[fin.available()];
            stream = new InputStreamReader(fin);
            stream.read(inputBuffer);
            data = new String(inputBuffer);
        }
        catch(Exception ex) {       
        	Toast.makeText(context, "File not read\n" + ex.getMessage(), Toast.LENGTH_LONG).show(); 
        }
        
    	try { 
    		stream.close(); 
    		fin.close();
    	}
    	catch(IOException ex) { 
    		Toast.makeText(context, "File not closed\n" + ex.getMessage(), Toast.LENGTH_LONG).show(); 
    	}
    	catch(Exception ex) { 
    		Toast.makeText(context, "Exception\n" + ex.getMessage(), Toast.LENGTH_LONG).show(); 
    	}
        
        return data;
	}
		
	public static Boolean deleteINIFile(Context context) {
		
		try {
			context.deleteFile(Constante.file_ini);
			return true;
		}
		catch(Exception ex) {
			Toast.makeText(context, "Exception\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			return false;
		}
	}
		
	public static String encrypt(Context context, String clearText) {
		try {
			return SimpleCrypto.encrypt(Constante.secret_key, clearText);
		}
		catch (Exception ex) {
			Toast.makeText(context, "Encrypt Exception\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			return null;
		}
	}
	
	public static String decrypt(Context context, String cypherText) {
		try {
			return SimpleCrypto.decrypt(Constante.secret_key, cypherText);
		}
		catch (Exception ex) {
			Toast.makeText(context, "Decrypt Exception\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			return null;
		}
	}
	
	// Test de la presence d'une connexion internet
	public static boolean isInternetAvailable(Context context) {
		
		try {
	        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	        
	        if(activeNetwork == null) {
	        	return false;
	        }
	        else {
	        	if(!(activeNetwork.isConnectedOrConnecting())) {
	        		return false;
	        	}
	        }
		}
		catch(Exception ex) {
			Toast.makeText(context, "isInternetAvailable method exception\n" + ex.getCause() + "\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			return false;
        }
		
		return true;
	}

}
