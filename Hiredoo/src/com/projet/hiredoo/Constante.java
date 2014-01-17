package com.projet.hiredoo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import org.json.JSONArray;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.utility.hiredoo.ProfileReader;
import com.utility.hiredoo.SimpleCrypto;

public class Constante {
	
	// Constantes
	public static final String file_ini = "params.ini";
	public static final String ini_email = "email";
	public static final String ini_password = "password";
	public static final String ini_remember = "remember_me";
	public static final String ini_type = "type";
	public static final String ini_id = "id";
	public static final String ini_type_recruiter = "recruiter";
	public static final String ini_type_jobseeker = "jobseeker";
	public static final String secret_key = "c3265jVeW69A49g";
	
	public static final String url       = "http://192.168.1.2:8080/Hiredo/";
	public static final String url_files = "http://192.168.1.2:8093/assets/uploads/";
	public static final String url_php   = "197.8.192.247";
	
	public static final String php_prehttp = "http://";
	public static final String php_prertsp = "rtsp://";
	public static final String php_streaming = ":1935/hiredo/mp4:";
	public static final String php_live      = ":1935/live/work";
	public static final String php_upload    = "/wowza/upload.php";
	
	public static final String user                = "user/";
	public static final String user_login          = "user/login/";
	public static final String user_getUserProfile = "user/profile/";
	public static final String user_getCvLmVideo   = "user/getcvresumevideo/";
	public static final String user_getMypostules  = "user/listejobpostule/";
	public static final String user_search         = "user/search/";
	public static final String user_setNotif       = "user/setnotif/";
	public static final String user_getNotif       = "user/getnotif/";
	public static final String user_chooseVideo    = "user/choose/";
	
	public static final String enterprise          = "entreprise/";
	public static final String enterprise_login    = "entreprise/login/";
	public static final String enterprise_search   = "entreprise/search/";
	public static final String enterprise_setNotif = "entreprise/setnotif/";
	public static final String enterprise_getNotif = "entreprise/getnotif/";
	
	public static final String job                  = "job/";
	public static final String job_getAllJobs       = "job/findall/";
	public static final String job_getJobsByDomaine = "job/domaine/";
	public static final String job_getMyJobs        = "job/myjobs/";
	public static final String job_search           = "job/search/";
	
	public static final String language = "language/";
	
	public static final String postule                   = "postule/";
	public static final String postule_getUsersByPostule = "postule/users/";
	public static final String postule_getPostuleDetail  = "postule/detail/";
	
	public static final String experience = "experience/";
	
	public static final String education = "education/";
	
	public static final String video = "video/";
	public static final String video_getMyVideos = "video/myvideos/";
	
	public static final String upload = "upload"; // Attention: Cette url ne comporte pas de / à la fin
	public static final String upload_folder = "uploads/";
	
	public static final CharSequence[] job_domaine = {"Computer Science", "Mechanic", "Electronic", "Telecom", "Transport", "Manufacturing", "Managment", "Aeronautics", "Finance", "Audiovisual", "Teaching", "Journalisme", "Marketing", "Medical", "Tourisme", "Others"};
	public static final CharSequence[] job_type = {"CDD", "CDI", "Stage"};
	
	public static final String http_get = "GET";
	public static final String http_post = "POST";
	
	public static final String date_from_type = "from";
	public static final String date_to_type = "to";
	
	public static final String listjob_type_all   = "all";
	public static final String listjob_type_seach = "search";
	
	public static final String path_static = "/mnt";
	
	public static final String type_cv = "cv";
	public static final String type_lm = "resume";
	
	public static final String video_streaming = "streaming";
	public static final String video_live      = "live";
	
	public static final String application_accepted = "A";
	public static final String application_rejected = "R";
	
	// Variables Globales
	public static JSONArray ja = new JSONArray();
	public static String path = "/mnt";
	public static String job_id = "0";
	public static String date_from = "";
	public static String date_to   = "";
	public static String listjob_type = "";
	
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
	
	// Retourne la date courante dans un format lisible par Mysql
	public static String getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int mois = cal.get(Calendar.MONTH) + 1;
		int day  = cal.get(Calendar.DAY_OF_MONTH);
		String ch_mois = mois + "";
		
		if(ch_mois.length() == 1) {
			ch_mois = "0" + ch_mois;
		}
		
		String now = year + "-" + ch_mois + "-" + day + "T00:00:00";
		return now;
	}
	
	// Retourne une chaine de caracteres qui affiche un date lisible
	public static String transformDate(String date) { // Format actuel: 2013-12-24T00:00:00
		// Getting values
		String aa = date.substring(0, 4);
		String mm = date.substring(5, 7);
		String dd = date.substring(8, 10);
		int mois;
		
		// Transformation de la valeur du mois
		try {
			mois = Integer.parseInt(mm);
		}
		catch(NumberFormatException ex) {
			mois = 0;
		}
		
		// Transformation de la date
		switch(mois) {
		case 1:
			mm = "Jan.";
			break;
			
		case 2:
			mm = "Feb.";
			break;
			
		case 3:
			mm = "Mars";
			break;
			
		case 4:
			mm = "Avr.";
			break;
			
		case 5:
			mm = "Mai";
			break;
		case 6:
			mm = "Juin";
			break;
			
		case 7:
			mm = "Jui.";
			break;
			
		case 8:
			mm = "Aout";
			break;
			
		case 9:
			mm = "Sep.";
			break;
			
		case 10:
			mm = "Oct.";
			break;
			
		case 11:
			mm = "Nov.";
			break;
			
		case 12:
			mm = "Dec.";
			break;
			
		default:
			// Ne rien faire, mm garde sa valeur numerique
		}
		
		return dd + " " + mm + " " + aa;
	}

}
