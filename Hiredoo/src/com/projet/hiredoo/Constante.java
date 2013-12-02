package com.projet.hiredoo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.utility.hiredoo.ProfileReader;
import com.utility.hiredoo.SimpleCrypto;

import android.content.Context;
import android.widget.Toast;

public class Constante {
	
	// Variables
	public static final String file_ini = "params.ini";
	public static final String ini_login = "login";
	public static final String ini_password = "password";
	public static final String ini_remember = "remember_me";
	public static final String secret_key = "azerty";
	
	/* Format fichier INI:
	[application]
	login=the_login
	password=the_password_encrypted
	rememberme=true | false  
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
            stream.write("[application]\n" + Constante.ini_login + "=empty\n" + Constante.ini_password + "=empty\n" + Constante.ini_remember + "=false");
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
	
	public static void saveINIFile(Context context, String login, String password) {
		
		FileOutputStream fout = null;
        OutputStreamWriter stream = null;
        
        try {
        	fout = context.openFileOutput(Constante.file_ini, Context.MODE_PRIVATE);
            stream = new OutputStreamWriter(fout);
            stream.write("[application]\n" + Constante.ini_login + "=" + login + "\n" + Constante.ini_password + "=" + password + "\n" + Constante.ini_remember + "=true");
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

}
