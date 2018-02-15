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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author mike spad
 */
public class DetailFXMLController implements Initializable {

    @FXML
    private TextArea friendList;
    @FXML
    private Text profileText;
    @FXML
    private TextArea movieList;
    @FXML
    private Text firstText;
    @FXML
    private Text secondText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * receives the data to be displayed for an actor
     *
     * @param actor the actor whose information will be displayed
     */
    public void initData(Actor actor) {
        profileText.setText(actor.getName() + "'s profile");
        String friendListString = "";
        String movieListString = "";
        for (Actor friend : actor.getFriends()) {
            friendListString += friend.getName() + "\n";
        }
        friendList.setText(friendListString);
        for (Movie mov : actor.getMovies()) {
            movieListString += mov.getTitle() + "(" + mov.getYear() + ")"+ "\n";
        }
        movieList.setText(movieListString);

    }

    /**
     * receives the data to be displayed for an movie
     *
     * @param movie the movie whose information will be displayed
     */
    public void initData(Movie movie) throws NullPointerException {
        profileText.setText(movie.getTitle() + "(" + movie.getYear() + ")");
        String actorString = "";
        for (Actor actor : movie.getActors()) {
            actorString += actor.getName() + "\n";
        }

        firstText.setText("Actors");
        friendList.setText(actorString);
        secondText.setText("");

    }

}
