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

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * the controller for the main FXML
 * @author mike spad
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    @FXML
    private TextField movieBox;
    @FXML
    private TextArea pathBox;
    @FXML
    private TextField actorFromBox;
    @FXML
    private TextField actorToBox;
    @FXML
    private ListView<String> actorListView;
    @FXML
    private ListView<String> movieListView;
    private ObservableList<String> actorObservableList;
    private SelectionModel<String> actorSelectionModel;
    private ObservableList<String> movieObservableList;
    private SelectionModel<String> movieSelectionModel;
    private ActorGraph actorGraph;
    private NameComparator nameComparator;
    private TitleComparator titleComparator;
    @FXML
    private TextField importOutbox;
    @FXML
    private TextField actorSearchInputField;

    /**
     * initializes actor and movie ObservableLists, actor and movie selection models, name and title comparators, and actorGraph
     * post: all these objects are initialized
     * @param url the url
     * @param rb the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actorObservableList = FXCollections.observableArrayList();
        movieObservableList = FXCollections.observableArrayList();
        actorSelectionModel = actorListView.getSelectionModel();
        movieSelectionModel = movieListView.getSelectionModel();
        nameComparator = new NameComparator();
        titleComparator = new TitleComparator();
        actorGraph = new ActorGraph();
    }

    /**
     * imports a movie from the database. the title of the movie is gotten from movieBox
     * pre: the movie exists in the database. the movieBox is not empty
     * post: a movie is imported
     * 
     * @param event the click of the import movie button
     */
    @FXML
    private void importMovie(ActionEvent event) { //gotta make sure this works. I need to make sure the old actor isnt deleted if a name is repeated.
        String movieName = movieBox.getText();
        try {
            Movie tempMovie = new Movie(movieName);
            if (actorGraph.getMoviesByTitle().containsKey(tempMovie.getTitle())) {
                throw new Exception("Movie already added");
            }
            actorGraph.addMovie(tempMovie);
            ArrayList<Actor> actors = tempMovie.getActors();
            // HashMap<String, Actor> actorsByName = actorGraph.getActorsByName();
            for (int i = 0; i < actors.size(); i++) {
                Actor actor = actors.get(i);
                if (actorGraph.getActorsByName().containsKey(actor.getName())) {
                    tempMovie.removeActor(actor);
                    tempMovie.addActor(i, actorGraph.getActor(actor.getName()));
                    actorGraph.getActor(actor.getName()).addMovie(tempMovie);
                } else {

                    actorGraph.addActor(actor);
                    actor.addMovie(tempMovie);

                    //actor.addFriends()
                }
            }

            refreshListViews();
            movieDetail(tempMovie);
            importOutbox.setText("Successfully imported " + tempMovie.getTitle());
            movieBox.setText("");

            //importOutbox.setText("Hello! enter a movie, then press the button");
        } catch (Exception ex) {
            importOutbox.setText(ex.getMessage());
        }
    }
    /**
     * finds the shortest path between two actors
     * pre: there is a path between the two actors. the actors entered are
     * in the database of movies entered. the actorFromBox and actorToBox are not
     * null
     * @param event the click of the shortest path button 
     */

    @FXML
    private void shortestPath(ActionEvent event) {
        try {
            ActorGraph.bfs(actorFromBox.getText());
            String fromString = "";
            Actor toActor = actorGraph.getActor(actorToBox.getText());

            if (toActor == null){
                throw new Exception ("To actor not found");
            }
            if (toActor.getPath() == null) {
                throw new Exception("No path found");
            }
            for (String act : toActor.getPath()) {
                fromString += act + "\n";
            }

            pathBox.setText(fromString);

        } catch (Exception ex) {
            pathBox.setText(ex.getMessage());
        }

    }

    /**
     * finds the breadth first search of the actor entered in the actorFromBox
     * pre: the actorFromBox is not empty, and the name in the box is in the database
     * @param event the click of the bfs button
     */
    @FXML
    private void breadthFirstSearch(ActionEvent event) {
        Actor bfsActor = actorGraph.getActor(actorFromBox.getText());
        try {
            LinkedList<String> bfs = ActorGraph.bfs(bfsActor.getName());
            String bfsString = "";
            for (String actorName : bfs) {
                bfsString += actorName + "\n";
            }
            pathBox.setText(bfsString);

        } catch (Exception ex) {
            pathBox.setText("actor not in list");
        }

    }

    /**
     * refreshes the list views to have the correct actors and movies
     */
    private void refreshListViews() {
        refreshActorListView();
        refreshMovieListView();

    }

    /**
     * refreshes the actor list view
     */
    private void refreshActorListView() {
        actorObservableList.clear();

        for (String key : actorGraph.getActorsByName().keySet()) {
            actorObservableList.add(key);
            actorObservableList.sort(new StringComparator());
        }

        actorListView.setItems(actorObservableList);
    }

    /**
     * refreshes the movie list view
     */
    private void refreshMovieListView() {
        movieObservableList.clear();

        for (String key : actorGraph.getMoviesByTitle().keySet()) {
            movieObservableList.add(key);
            movieObservableList.sort(new StringComparator());
        }
        movieListView.setItems(movieObservableList);
    }

    /**
     * pre: an actor is selected
     * post: opens the detail FXML for the actor selected in the actorListView
     * @param event the click of the actor detail button
     */
    @FXML
    private void actorDetail(ActionEvent event) {
        try {
            String selectedString = actorSelectionModel.getSelectedItem();
            Actor actor = actorGraph.getActor(selectedString);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "detailFXML.fxml"));
            Parent root = loader.load();
            DetailFXMLController detailFXMLController
                    = loader.<DetailFXMLController>getController();
            detailFXMLController.initData(actor);

            Stage stage = new Stage();
            stage.setTitle(actor.getName() + "'s profile");
            stage.setScene(new Scene(root, 600, 600));
            stage.show();
        } catch (Exception ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * opens the detailFXML for the movie currently selected in the movie list view
     * post: detailFXML is opened for the movie currently selected in the movie list view
     * @param event the click of the movieDetail button
     */
    @FXML
    private void movieDetail(ActionEvent event) {
        try {
            String selectedString = movieSelectionModel.getSelectedItem();
            Movie movie = actorGraph.getMovie(selectedString);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "detailFXML.fxml"));
            Parent root = loader.load();
            DetailFXMLController detailFXMLController
                    = loader.<DetailFXMLController>getController();
            detailFXMLController.initData(movie);

            Stage stage = new Stage();
            stage.setTitle(movie.getTitle());
            stage.setScene(new Scene(root, 600, 600));
            stage.show();
        } catch (Exception ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * opens the detailFXML for the movie that was just added
     * post: detailFXML is opened for the movie that was just added
     * @param movie the movie that was just added
     */
    private void movieDetail(Movie movie) {
        try {
           

            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "detailFXML.fxml"));
            Parent root = loader.load();
            DetailFXMLController detailFXMLController
                    = loader.<DetailFXMLController>getController();
            detailFXMLController.initData(movie);

            Stage stage = new Stage();
            stage.setTitle(movie.getTitle());
            stage.setScene(new Scene(root, 600, 600));
            stage.show();
        } catch (Exception ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * pre: an actor is selected
     * post: the actorFromBox is set to the name of the actor currently selected
     * in the listview
     * @param event the click of the select as starting Actor button
     */

    @FXML
    private void selectAsStartingActor(ActionEvent event) {
        try {
            actorFromBox.setText(
                    actorSelectionModel.getSelectedItem());
        } catch (Exception ex) {

        }

    }

    /**
     * pre: an actor is selected
     * post: the actorToBox is set to the name of the actor currently selected
     * in the listview
     * @param event the click of the select as goal Actor button
     */
    @FXML
    private void selectAsGoalActor(ActionEvent event) {
        try {
            actorToBox.setText(
                    actorSelectionModel.getSelectedItem());
        } catch (Exception ex) {

        }
    }

    /**
     * 
     * pre: the actor entered is in the actorGraph
     * post: detailFXML is opened with the details of the actor entered
     * @param event the press of the search button
     */
    @FXML
    private void actorSearch(ActionEvent event) {
        
         try {
             Actor actor = actorGraph.getActor(actorSearchInputField.getText());
             if( actor == null){
                 actorSearchInputField.setText("Actor not found");
                 throw new Exception();
             }
           

            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "detailFXML.fxml"));
            Parent root = loader.load();
            DetailFXMLController detailFXMLController
                    = loader.<DetailFXMLController>getController();
            detailFXMLController.initData(actor);

            Stage stage = new Stage();
            stage.setTitle(actor.getName());
            stage.setScene(new Scene(root, 600, 600));
            stage.show();
        } catch (Exception ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
