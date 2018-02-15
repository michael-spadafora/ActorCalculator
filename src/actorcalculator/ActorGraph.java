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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * the actor graph class stores actors by name and movies by title in two hashmaps
 * @author mike spad
 */
public class ActorGraph {

    private static HashMap<String, Actor> actorsByName;
    private static HashMap<String, Movie> moviesByTitle;

    /**
     * post: actorsByName and moviesByTitle are initialized to new hashmaps
     * default constructor for an ActorGraph
     */
    public ActorGraph() {
        actorsByName = new HashMap<>();
        moviesByTitle = new HashMap<>();
    }
    
    /**
     * returns the breadth first search starting from the actor by the name
     * pre: the actor exists in actorsByName
     * post: the path of each actor is set
     * @param name the name to be searched from
     * @return the overall traversal
     * @throws Exception if the name is not in actorsByName
     */

    public static LinkedList<String> bfs(String name) throws Exception {
        if (!actorsByName.containsKey(name)) {
            throw new Exception("actor not in list");
        }
        LinkedList<String> traversal = new LinkedList<>();
        Queue<Actor> queue = new LinkedList<>();

        for (String actorName : actorsByName.keySet()) {
            actorsByName.get(actorName).setVisited(false);
            actorsByName.get(actorName).setPath(null);
        }

        Actor currActor;//= actorsByName.get(name);
        //ArrayList<Actor> adjacentActors = currActor.getFriends();
        queue.add(actorsByName.get(name));
        actorsByName.get(name).setVisited(true);

        LinkedList<String> tempNames = new LinkedList<>();
        tempNames.add(name);
        actorsByName.get(name).setPath(tempNames);
        traversal.add(name);

        while (!queue.isEmpty()) {
            currActor = queue.poll();
            for (Actor friend : currActor.getFriends()) {
                if (!friend.isVisited()) {
                    queue.add(friend);
                    friend.setPath((LinkedList < String >)currActor.getPath().clone());
                    friend.addToPath(friend.getName());
                                  
                    friend.setVisited(true);
                    traversal.add(friend.getName());
                }
            }

        }

        return traversal;
    }
    /**
     * adds an actor to actorsByName 
     * pre: name and actor are both not null
     * post:  actor is added to actorsByName at the key of name
     * @param name the key to be used
     * @param actor the actor to be added
     * @throws Exception if name or actor is null
     */

    public void addActor(String name, Actor actor) throws Exception {
        if (name == null || actor == null) {
            throw new Exception("Name or actor is null");
        }
        actorsByName.put(name, actor);
    }
    
    /**
     * calls addActor on an Actor
     * @param actor the actor to be added to the hashmap
     * @throws Exception if actor is null
     */

    public void addActor(Actor actor) throws Exception {
        addActor(actor.getName(), actor);
    }

    /**
     * adds a movie to the moviesByTitle hashmap
     * @param name the name of the movie to be used as a key
     * @param mov the movie to be added
     * @throws Exception if name or mov is null
     */
    public void addMovie(String name, Movie mov) throws Exception {
        if (name == null || mov == null) {
            throw new Exception("Name or movie is null");
        }
        moviesByTitle.put(name, mov);
    }

    /**
     * calls addMovie(String name, Movie mov) with the name of mov
     * @param mov the movie to be added to moviesByTitle
     * @throws Exception if mov or mov.getTitle() is null
     */
    public void addMovie(Movie mov) throws Exception {
        addMovie(mov.getTitle(), mov);
    }

    /**
     * 
     * @return the hashmap actorsByName 
     */
    public HashMap<String, Actor> getActorsByName() {
        return actorsByName;
    }

    /**
     * 
     * @return the hashmap moviesByTitle
     */
    public HashMap<String, Movie> getMoviesByTitle() {
        return moviesByTitle;
    }

    /**
     * returns one actor from key
     * 
     * @param key the name of the actor to be returned
     * @return the actor at key
     */
    public Actor getActor(String key) {
        return actorsByName.get(key);
    }

    /**
     * returns one specific movie denoted by key
     * @param key the title of the movie to be returned
     * @return the movie to be returned
     */
    public Movie getMovie(String key) {
        return moviesByTitle.get(key);
    }

}
