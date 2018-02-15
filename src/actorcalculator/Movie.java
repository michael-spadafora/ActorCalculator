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

import java.util.Scanner;

import big.data.DataSource;
import java.util.ArrayList;

/**
 * The Movie class has a title, actors, and a year (for some reason)
 * @author mike spad
 */
public class Movie {

    private String title;
    private ArrayList<Actor> actors;
    private int year;

    /**
     * creates a movie with title actors and years based on info from a database
     * pre: the title is the title of a movie in the database
     * @param title the title of the movie to be constructed
     * @throws Exception if the movie is not in the database
     */
    public Movie(String title) throws Exception{// Loads (using BigData) a movie using the passed title to create the URL and makes a new Movie object from it.
        Scanner sc = new Scanner(System.in);

        String prefix = "http://www.omdbapi.com/?t=";
        String postfix = "&y=&plot=short&r=xml";
        try {
            if (title.length() > 0) {
                DataSource ds = DataSource.connectXML(prefix + title.replace(' ', '+') + postfix);
                ds.load();
                this.title = ds.fetchString("movie/title");

                String actorString = ds.fetchString("movie/actors");
                int commas = 0;
                for (int i = 0; i < actorString.length(); i++) {
                    if (actorString.charAt(i) == ',') {
                        commas++;
                    }
                }
                actors = new ArrayList<>(); //"Jeff Bridges, John Goodman, Julianne Moore, Steve Buscemi"

                for (int i = 0; i < commas; i++) {
                    actors.add(new Actor(actorString.substring(0, actorString.indexOf(','))));
                    actorString = actorString.substring(actorString.indexOf(',') + 1).trim();
                }
                actors.add(new Actor(actorString));
                //actorString = actorString.substring(actorString.indexOf(',')+1).trim();

                this.year = ds.fetchInt("movie/year");

            }

        } catch (Exception ex) {
            System.out.print(ex.getMessage());

        }
    }

    /**
     * 
     * @return the title of the movie 
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title the title to be set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return the actors of this movie 
     */
    public ArrayList<Actor> getActors() {
        return actors;
    }

    /**
     * 
     * @param actors the actors to be set
     */
    public void setActors(ArrayList<Actor> actors) {
        this.actors = actors;
    }

    /**
     * removes an actor from this movie
     * 
     * @param actor the actor to be removed
     */
    public void removeActor(Actor actor) {
        actors.remove(actor);

    }

    /**
     * adds an actor to this movie
     * @param actor the actor to be added
     */
    public void addActor(Actor actor) {
        actors.add(actor);
    }

    /**
     * adds an actor to this movie at index i in the ArrayList of actors
     * @param i the index to be added to
     * @param actor the actor to be added
     */
    public void addActor(int i, Actor actor) {
        actors.add(i, actor);
    }

    /**
     * 
     * @return the year of the movie
     */
    public int getYear() {
        return year;
    }

    /**
     * 
     * @param year the year to be set
     */
    public void setYear(int year) {
        this.year = year;
    }

}
