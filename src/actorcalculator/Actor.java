/*
 * Michael Spadafora
 * Michael.spadafora@stonybrook.edu
 * ID:110992992
 * Homework #7
 * cse214 Recitation section 8
 * Recitation TA: Michael P Rizzo
 * Grading TA: Timothy Zhang
 */
package actorcalculator;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * An actor has a name, an arraylist of movies, an arraylist of friends, a boolean visited, and a linkedlist path
 * @author mike spad
 */
public class Actor {

    private String name;
    private ArrayList<Movie> movies;
    private ArrayList<Actor> friends;
    private boolean visited;
    private LinkedList<String> path;
    //private String previous;

    /**
     * the default constructor for the actor class
     * post: this.name is set to name, and all other variables are initialized
     * @param name the actors name to be set
     */
    public Actor(String name) {
        // movies.add(movie);
        this.name = name;
        movies = new ArrayList<>();
        friends = new ArrayList<>();
        //previous = null;
    }
    
    /**
     * adds name to path
     * post: path now includes name
     * @param name the name to be added to the path
     */
    public void addToPath(String name){
        path.add(name);
    }

    /**
     * 
     * @return the name of the actor 
     */
    public String getName() {
        return name;
    }

    /**
     * adds a movie to the arraylist of movies
     * @param mov the movie to be added
     */
    public void addMovie(Movie mov) {
        movies.add(mov);
        ArrayList<Actor> tempFriends = mov.getActors();
        for (Actor ac : tempFriends) {
            
            if (!friends.contains(ac)&&ac!=this) {
                friends.add(ac);
            }
        }
    }
    

    /**
     * 
     * @return the arraylist of movies 
     */
    public ArrayList<Movie> getMovies() {
        return movies;
    }

    /**
     * sets the arraylist of movies
     * @param movies the movies to be set
     */
    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    /**
     * 
     * @return the arraylist of actors in movies with this actor 
     */
    public ArrayList<Actor> getFriends() {
        return friends;
    }

    /**
     * sets the friends arraylist
     * @param friends the friends to set
     */
    public void setFriends(ArrayList<Actor> friends) {
        this.friends = friends;
    }

    /**
     * 
     * @return true if this has been visited in this bfs, false otherwise 
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * sets visited to visited
     * @param visited the visited to set
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    /**
     * returns the path traversed to get to this actor
     * @return the path
     */

    public LinkedList<String> getPath() {
        return path;
    }

    /**
     * sets the path traversed to get to this actor
     * @param path the path to set
     */
    public void setPath(LinkedList<String> path) {
        this.path = path;
    }

}
