package com.bruce.backend;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.bruce.backend.common.Request;
import com.bruce.backend.common.Reply;
import com.bruce.backend.server.ManageUser;
import com.bruce.backend.server.WebContentFetcher;

public class App extends Application {
    private TextField urlField;
    private TextArea outputArea;
    private Button cloneButton;
    private ProgressIndicator progressIndicator;

    private ManageUser manageUser;

    @Override
    public void start(Stage primaryStage) {
        // Initialize UI Components
        urlField = new TextField();
        urlField.setPromptText("Enter URL to clone");

        outputArea = new TextArea();
        outputArea.setWrapText(true);
        outputArea.setEditable(false); // Make outputArea read-only

        cloneButton = new Button("Clone Website");
        cloneButton.setOnAction(e -> {
            String url = urlField.getText().trim();
            if (!url.isEmpty()) {
                String formattedURL = formatURL(url);
                if (!isValidURL(formattedURL)) {
                    outputArea.appendText("Please enter a valid URL.\n");
                    return;
                }

                outputArea.clear();
                cloneButton.setDisable(true);
                progressIndicator.setVisible(true);

                // Create a Request object with the appropriate constructor
                Request req = new Request("clone", new Object[]{formattedURL}, 0L);

                // Start the website content fetcher
                new WebContentFetcher(req, manageUser);
            } else {
                outputArea.appendText("Please enter a valid URL.\n");
            }
        });

        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false); // Initially hidden
        progressIndicator.setPrefSize(25, 25);

        // Create an anonymous subclass of ManageUser with the correct method signature
        manageUser = new ManageUser(null) {
            @Override
            public void write(Reply reply) {
                Platform.runLater(() -> {
                    String call = reply.getCall();
                    Object content = reply.getContent();

                    if (call.startsWith("error:")) {
                        outputArea.appendText("Error: " + content + "\n");
                    } else {
                        outputArea.appendText(content.toString() + "\n");
                    }

                    cloneButton.setDisable(false);
                    progressIndicator.setVisible(false);
                });
            }
        };

        VBox root = new VBox(10, urlField, cloneButton, progressIndicator, outputArea);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Website Clone Tool");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Formats the URL to ensure it starts with http:// or https://
    private String formatURL(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "http://" + url;
        }
        return url;
    }

    // Validates if the URL is well-formed
    private boolean isValidURL(String url) {
        try {
            new java.net.URL(url);
            return true;
        } catch (java.net.MalformedURLException e) {
            return false;
        }
    }

    // Entry point of the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}
