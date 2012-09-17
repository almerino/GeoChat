package fr.utbm.geochat;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.utbm.geochat.activity.ChannelAdapter;
import fr.utbm.geochat.host.Channel;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;
import android.location.Location;
/**
 * 
 * @author Bfrost
 * La classe AapplicationManager est la classe abstraite qui permettra d'impl�menter
 * la partie client et la partie serveur
 */
public abstract class ApplicationManager {

	protected String username;
	protected String currentChannel = "Main";
	protected Location myLocation;
	protected Intent myIntentService;
	protected Map<String, LinkedList<Message>> channelsJoined;

	protected Set<Channel> listChannels;
	protected ChannelAdapter channelAdapter;
	protected Map<String, Location> listLocations;

	/**
	 * Envoyer un message � un utilisateur
	 * @param message est le message envoy�
	 * @param receiver la personne cible du message
	 */
	public abstract void whisp(String message, String receiver);
	/**
	 * 
	 * @param message message envoy� au serveur
	 */
	public abstract void sendServer(String message);

	/**
	 * Cr�er un channel
	 * @param name nom du channel
	 */
	public abstract void createChannel(String name);
	/**
	 * Cr�er un channel priv�e
	 * @param name nom du channel 
	 * @param password mot de passe du channel
	 */
	public abstract void createPrivateChannel(String name, String password);
	/**
	 * Quitter un channel
	 */
	public abstract void quitChannel();
	
	/**
	 * Lancer le service de geolocalisation
	 */
	public abstract void runServiceGPS();
	/**
	 * Stoper le service de geolocalisation
	 */
	public abstract void stopServiceGPS();
	
	/**
	 * Accepter la reception d'un fichier
	 */
	public abstract void acceptFile();
	
	/**
	 * Refuser la reception d'un fichier
	 */
	public abstract void refuseFile();
	
	/**
	 * Quitter
	 * @throws IOException
	 */
	public abstract void exit() throws IOException;

	/**
	 * R�cuper sa localisation
	 * @return
	 */
	public Location getMyLocation() {
		return myLocation;
	}

	/**
	 * Liste des channels
	 * @return
	 */
	public Set<Channel> getListChannels() {
		return listChannels;
	}
	
	/**
	 * Setter sa localisation
	 * @param myLocation sa localisation
	 */
	public void setMyLocation(Location myLocation) {
		this.myLocation = myLocation;
	}

	/**
	 * V�rifier si le service de g�olocalisation est actif
	 * @param nomService service cibl�
	 * @return
	 */
	protected boolean isServiceActif(String nomService) {
		final ActivityManager activityManager = (ActivityManager) GeoChat.getInstance().getSystemService(GeoChat.ACTIVITY_SERVICE);
		final List<RunningServiceInfo> services = activityManager
				.getRunningServices(Integer.MAX_VALUE);

		int i = 0;
		while (i < services.size()
				&& !nomService.equals(services.get(i).service.getClassName())) {
			i++;
		}

		return i < services.size();
	}
	
	/**
	 * Ajouter un nouveau channel
	 * @param channelName nom du channel
	 */
	protected void addNewChannelToList(String channelName) {
		channelsJoined.put(channelName, new LinkedList<Message>());
	}
	
	/**
	 * Ajouter un message au channel
	 * @param channelName channel cibl�e
	 * @param message message � diffuser
	 */
	protected void addMessagesToChannel(String channelName, Message message) {
		channelsJoined.get(channelName).add(message);
	}
	
	/**
	 * V�rifier si l'utilisateur est sur le channel
	 * @param channelName nom du channel cible
	 * @return vrai si l'utilisateur � �t� trouv�
	 */
	protected boolean isOnChannel(String channelName) {
		if(channelsJoined.containsKey(channelName)) {
			return true;
		}
		return false;
	}
	
	/**
	 * R�cuperer la liste des messages du channel cible
	 * @param idChannel channel cible
	 * @return
	 */
	public List<Message> getMessages(String idChannel) {
		return channelsJoined.get(idChannel);
	}
	
	/**
	 * Quitter un channel
	 * @param channelName channel cible
	 */
	protected void quitChannel(String channelName) {
		if(channelsJoined.containsKey(channelName)) {
			channelsJoined.remove(channelName);
		}
	}
	
	/**
	 * R�cuperer l'adapter des channel
	 * @return
	 */
	public ChannelAdapter getAdapter() {
		return channelAdapter;
	}
	
	/**
	 * R�cuperer la liste des localisations des utilisateurs
	 * @return liste des geolocalisations
	 */
	public Map<String, Location> getLocations() {
		return listLocations;
	}
	
	/**
	 * Channels rejoint
	 * @return
	 */
	public Map<String, LinkedList<Message>> getChannelsJoined() {
		return channelsJoined;
	}

	/**
	 * Setter les channels rejoint
	 * @param channelsJoined
	 */
	public void setChannelsJoined(Map<String, LinkedList<Message>> channelsJoined) {
		this.channelsJoined = channelsJoined;
	}
	
}
