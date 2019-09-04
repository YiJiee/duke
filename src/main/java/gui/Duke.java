package gui;

import javafx.scene.paint.Color;
import main.*;
import task.*;
import command.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class Duke extends Application{
    private boolean isOpen = true;
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;
    private Image user = new Image(this.getClass().getResourceAsStream("/images/Vanillite.jpg"));
    private Image duke = new Image(this.getClass().getResourceAsStream("/images/Rowlet.jpg"));
    private Ui ui = new Ui();
    private Storage storage = new Storage("./src/main/java/DukeData.txt");
    private TaskList taskList;

    public Duke() {

    }

    /**
     * Iteration 2:
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    private void handleUserInput(Stage stage) {
        Label userText = new Label(userInput.getText());
        Label dukeText = new Label(getResponse(userInput.getText(), stage));
        ImageView userImage = new ImageView(user);
        userImage.setClip(new Circle(50,50,50));
        ImageView dukeImage = new ImageView(duke);
        dukeImage.setClip(new Circle(50,50,50));
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getDukeDialog(dukeText, dukeImage)
        );
        userInput.clear();
    }

    /**
     * You should have your own function to generate a response to user input.
     * Replace this stub with your completed method.
     */
    private String getResponse(String input, Stage stage) {
        try {
            Command c = Parser.parse(input);
            c.execute(taskList, ui, storage);
            this.isOpen = !c.isExit();
            return ui.showLine();
        } catch (InsufficientTaskArgumentException e) {
            return e.getMessage();
        } catch (DukeException e) {
            return e.getMessage();
        } catch (InvalidTaskException e) {
            return e.getMessage();
        } finally {
            if (!this.isOpen) {
                stage.close();
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Step 1. Setting up required components
        taskList = new TaskList(storage.load());

        //The container for the content of the chat to scroll.
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);
        //Step 2. Formatting the window to look as expected
        stage.setTitle("gui.Duke");
        stage.setResizable(false);
        stage.setMinHeight(595.0);
        stage.setMinWidth(500.0);

        mainLayout.setPrefSize(500.0, 595.0);

        scrollPane.setPrefSize(495, 538);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        // You will need to import `javafx.scene.layout.Region` for this.
        ImageView dukeImg = new ImageView(duke);
        dukeImg.setClip(new Circle(50,50,50));
        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
        dialogContainer.getChildren().add(DialogBox.getDukeDialog(new Label(ui.showWelcome()), dukeImg));

        userInput.setPrefWidth(325.0);
        userInput.setPrefHeight(50.0);
        // Giving "Enter" functionality to input.
        userInput.setOnAction((event) -> {
            handleUserInput(stage);
        });

        sendButton.setPrefWidth(55.0);
        sendButton.setPrefHeight(50.0);
        // Giving onClick listener to send button.
        sendButton.setOnMouseClicked((event) -> {
            handleUserInput(stage);
        });

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput , 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        stage.setScene(scene);
        stage.show();
    }
}
