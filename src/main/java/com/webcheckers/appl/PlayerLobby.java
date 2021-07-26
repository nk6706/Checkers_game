package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.ui.PostSignInRoute;
import com.webcheckers.ui.WebServer;

import java.util.*;

/**
 * PlayerLobby handles the storing of players as well as signin/signout
 * functionality for the application. It is used when rendering the home page
 * and whenever else it is necessary to get a Player object.
 */
public class PlayerLobby {

    /** The list of signed-in players key=username, value=player obj */
    private HashMap<String, Player> playerList;

    /**
     * Initializes needed HashMap for storing players
     */
    public PlayerLobby() { this.playerList = new HashMap<String, Player>(); }

    /**
     * Add a new player to the lobby
     * @param player the Player object to add
     */
    public void addPlayer(Player player) { playerList.put(player.getUsername(), player); }

    /**
     * Returns the player with the given username, should make sure player exists first
     * @param username the name of the user to get
     * @return the Player object with the given username
     */
    public Player getPlayer(String username) {
        return playerList.get(username);
    }

    /**
     * Retrieve the current number of players
     * @return int representing how many players are signed in
     */
    public int getNumberOfPlayers() {
        return playerList.size();
    }

    /**
     * Check if the given player (username) exists in the lobby
     * @param username the name of the player to check for
     * @return true if the player is present, false otherwise
     */
    public boolean hasPlayer(String username) {
        return this.playerList.containsKey(username);
    }

    /**
     * Getter for keys of playerList hashmap
     * @return ArrayList of strings representing the usernames of signed in players
     */
    public ArrayList<String> getPlayerList() {
        return new ArrayList<>(this.playerList.keySet());
    }

    /**
     * Overloaded getter for keys of playerList hashmap, removes given username before returning
     * @return ArrayList of strings representing the usernames of signed in players
     */
    public ArrayList<String> getPlayerList(String username) {
        ArrayList<String> result = new ArrayList<>(this.playerList.keySet());
        result.remove(username);
        return result;
    }

    /**
     * Provides username validation and sign-in functionality to the game
     * @param username the name of the user to validate and create a player with
     * @return string representing sign-in status
     */
    public String signin(String username) {
        // Check that username is at least one alphanumeric character and contains no symbol
        if (username.length() > 0 && username.chars().allMatch( c -> Character.isLetterOrDigit(c) || Character.isWhitespace(c)) && username.chars().anyMatch(Character::isLetterOrDigit)) {
            if (!this.hasPlayer(username)) { // Check that username does not already exist
                final Player player = new Player(username);
                this.addPlayer(player);
                return WebServer.HOME_URL;
            }
            else {
                return WebServer.SIGN_IN_URL + "?error=" + PostSignInRoute.NAME_TAKEN_ERR;
            }
        } else {
            return WebServer.SIGN_IN_URL + "?error=" + PostSignInRoute.INVALID_NAME_ERR;
        }
    }

    /**
     * Whether or not the given player is available, in a game, exists
     * @param playerName the username of the player to check for
     * @return string representing player's status
     */
    public String playerAvailable(String playerName){
        if (this.hasPlayer(playerName)) {
            Player opponent = this.getPlayer(playerName);
            if (!opponent.inGame()){ return "available";}
            else return "unavailable";
        }
        return "not found";
    }

    /**
     * Removes a player from the lobby, meaning that they have signed out.
     * @param playerName the name of the player that is signing out
     */
    public void playerSignOut(String playerName) {
        this.playerList.remove(playerName);
    }

}
