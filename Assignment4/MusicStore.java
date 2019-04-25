//Rene Gagnon, 260801777
import java.util.ArrayList;

public class MusicStore {
    //ADD YOUR CODE BELOW HERE
    private MyHashTable<String, Song> songsTable;
    private MyHashTable<String, ArrayList<Song>> artistTable;
    private MyHashTable<Integer, ArrayList<Song>> yearsTable;
    //ADD YOUR CODE ABOVE HERE
    
    
    public MusicStore(ArrayList<Song> songs) {
        //ADD YOUR CODE BELOW HERE
    	if(songs != null) {
	    	ArrayList<Song> tmp;
	    	songsTable = new MyHashTable<String, Song>(2*songs.size());
	    	artistTable = new MyHashTable<String, ArrayList<Song>>(2*songs.size());
	    	yearsTable = new MyHashTable<Integer, ArrayList<Song>>(2*songs.size());
	    	
	        for(Song e : songs) {
	        	// construct the song hashtable
	        	songsTable.put(e.getTitle(), e);
	        	
	        	// construct the artist table
	        	if(artistTable.get(e.getArtist()) != null) {
	        		//artist is already in the table
	        		tmp = (ArrayList<Song>) artistTable.get(e.getArtist());
	        		tmp.add(e);
	        		artistTable.put(e.getArtist(), tmp);
	        	}else {
	        		//artist is not already in the table
	        		tmp = new ArrayList<Song>();
	        		tmp.add(e);
	    			artistTable.put(e.getArtist(), tmp);
	        	}
	        	
	        	// construct the year table
	        	if(yearsTable.get(e.getYear()) != null) {
	        		//year is already in the table
	        		tmp = (ArrayList<Song>) yearsTable.get(e.getYear());
	        		tmp.add(e);
	        		yearsTable.put(e.getYear(), tmp);
	        	}else {
	        		//year is not already in the table
	        		tmp = new ArrayList<Song>();
	        		tmp.add(e);
	        		yearsTable.put(e.getYear(), tmp);
	        	}
	        }
    	}else {
    		songsTable = new MyHashTable<String, Song>(10);
	    	artistTable = new MyHashTable<String, ArrayList<Song>>(10);
	    	yearsTable = new MyHashTable<Integer, ArrayList<Song>>(10);
    	}
        //ADD YOUR CODE ABOVE HERE
    }
    
    /**
     * Add Song s to this MusicStore
     */
    public void addSong(Song s) {
        // ADD CODE BELOW HERE
    	if(s != null) {
	    	ArrayList<Song> tmp;
	    	songsTable.put(s.getTitle(), s);
	    	
	    	// construct the artist table
	    	if(artistTable.get(s.getArtist()) != null && !artistTable.get(s.getArtist()).isEmpty()) {
	    		//artist is already in the table
	    		tmp = (ArrayList<Song>) artistTable.get(s.getArtist());
	    		tmp.add(s);
	    		artistTable.put(s.getArtist(), tmp);
	    	}else {
	    		//artist is not already in the table
	    		tmp = new ArrayList<Song>();
	    		tmp.add(s);
				artistTable.put(s.getArtist(), tmp);
	    	}
	    	
	    	// construct the year table
	    	if(yearsTable.get(s.getYear()) != null && !yearsTable.get(s.getYear()).isEmpty()) {
	    		//year is already in the table
	    		tmp = (ArrayList<Song>) yearsTable.get(s.getYear());
	    		tmp.add(s);
	    		yearsTable.put(s.getYear(), tmp);
	    	}else {
	    		//year is not already in the table
	    		tmp = new ArrayList<Song>();
	    		tmp.add(s);
	    		yearsTable.put(s.getYear(), tmp);
	    	}
    	}
        // ADD CODE ABOVE HERE
    }
    
    /**
     * Search this MusicStore for Song by title and return any one song 
     * by that title 
     */
    public Song searchByTitle(String title) {
        //ADD CODE BELOW HERE
        return songsTable.get(title);
        //ADD CODE ABOVE HERE
    }
    
    /**
     * Search this MusicStore for song by `artist' and return an 
     * ArrayList of all such Songs.
     */
    public ArrayList<Song> searchByArtist(String artist) {
        //ADD CODE BELOW HERE
    	return artistTable.get(artist);
        //ADD CODE ABOVE HERE
    }
    
    /**
     * Search this MusicSotre for all songs from a `year'
     *  and return an ArrayList of all such  Songs  
     */
    public ArrayList<Song> searchByYear(Integer year) {
        //ADD CODE BELOW HERE
    	return yearsTable.get(year);
        //ADD CODE ABOVE HERE
        
    }
}
