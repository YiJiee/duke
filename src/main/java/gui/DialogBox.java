package gui;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


public class DialogBox extends HBox {
    private Label text;
    private ImageView displayPicture;

    private DialogBox(Label l, ImageView iv) {
        text = l;
        displayPicture = iv;

        text.setWrapText(true);
        text.getStyleClass().add("duke-text");

        displayPicture.getStyleClass().add("display-picture");
        displayPicture.setFitWidth(120.0);
        displayPicture.setFitHeight(120.0);

        this.setAlignment(Pos.TOP_RIGHT);
        this.getChildren().addAll(text, displayPicture);
    }

    public void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        text.getStyleClass().add("user-text");
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    public static DialogBox getDukeDialog(Label l, ImageView iv) {
        DialogBox dukeDialog = new DialogBox(l, iv);
        return dukeDialog;
    }

    public static DialogBox getUserDialog(Label l, ImageView iv) {
        DialogBox userDialog = new DialogBox(l, iv);
        userDialog.flip();
        return userDialog;
    }
}