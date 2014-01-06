package com.projet.hiredoo;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map_activity extends Activity {
	
	private GoogleMap map;
	private String address;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        
        // Recuperation de la map
        this.map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        
        // Test de la map
        if(this.map == null) {
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Erreur Google Map");
			builder.setMessage("Impossible de récupérer la map. Installer les services Google");
			builder.create().show();
			return;
        }
        
        this.map.setMyLocationEnabled(true);// Activer le bouton ma position et afficher ma position courante si disponible
        
        // Recuperation des données
        address = getIntent().getExtras().getString("address");
        
        // Affichage des adresse
        this.afficher_adresse();
    }
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);

	    outState.putInt("map_type", this.map.getMapType());
	    outState.putFloat("zoom", this.map.getCameraPosition().zoom);
	    outState.putDouble("latitude", this.map.getCameraPosition().target.latitude);
	    outState.putDouble("longitude", this.map.getCameraPosition().target.longitude);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	    
	    if(savedInstanceState == null)
	    	return;
	    
        LatLng myLatLng = new LatLng(savedInstanceState.getDouble("latitude"), savedInstanceState.getDouble("longitude"));
	    CameraPosition myPosition = new CameraPosition.Builder().target(myLatLng).zoom(savedInstanceState.getFloat("zoom")).build();
	    
	    this.map.setMapType(savedInstanceState.getInt("map_type"));
	    this.map.animateCamera(CameraUpdateFactory.newCameraPosition(myPosition));
	    
	}
	
	@Override
    public void onBackPressed() {
        finish();
    }
	
	private void afficher_adresse() {
		
		Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
		
		try {
			List<Address> addresses = geoCoder.getFromLocationName(this.address, 5);
	        if(addresses.size() == 0) {
	        	Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
	        }
	        else {
	        	for(int i=0 ; i<addresses.size() ; i++) {
	        		Double lat = (double) (addresses.get(i).getLatitude());
		            Double lon = (double) (addresses.get(i).getLongitude());
		            LatLng location = new LatLng(lat, lon);
		            
		            // Move the camera instantly to hamburg with a zoom of 15.
		            this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));

		            // Zoom in, animating the camera.
		            map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		            
		    		// ajout du marker pour l'adresse dela societe
		        	this.map.addMarker(new MarkerOptions().position(location).title("My Location").snippet(this.address));
	        	}
	        }
		}
		catch(Exception ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Exception");
			builder.setMessage("Class: " + ex.getClass() + "\n\nCause: " + ex.getCause() + "\n\nMessage: " + ex.getMessage());
			builder.create().show();
		}
	}

}
