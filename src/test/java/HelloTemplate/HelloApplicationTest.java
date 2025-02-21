package HelloTemplate;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.embed.swing.JFXPanel;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloApplicationTest {

    private Stage stage;
    private Label welcomeText;

    @BeforeEach
    public void setUp() throws Exception {
        new JFXPanel();  // Initialize JavaFX
        Platform.runLater(() -> {
            try {
                HelloApplication app = new HelloApplication();
                stage = new Stage();
                app.start(stage);
                welcomeText = (Label) stage.getScene().lookup("#welcomeText");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread.sleep(3000); // Allow JavaFX to initialize
    }

    @Test
    public void testHelloButtonClick() {
        Platform.runLater(() -> {
            Button helloButton = (Button) stage.getScene().lookup(".button");
            assertEquals("", welcomeText.getText());

            // Simulate button click
            helloButton.fire();

            // Verify label update
            assertEquals("Welcome to JavaFX Application!", welcomeText.getText());
        });
    }

    @AfterEach
    public void tearDown() {
        Platform.runLater(() -> stage.close());
    }
}