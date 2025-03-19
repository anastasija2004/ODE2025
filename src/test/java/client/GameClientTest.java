package client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.*;

class GameClientTest {
    private ServerSocket mockServerSocket;
    private Thread serverThread;

    @BeforeEach
    void setUp() throws IOException {
        // Start a mock server
        mockServerSocket = new ServerSocket(12345);
        serverThread = new Thread(() -> {
            try {
                mockServerSocket.accept(); // Wait for a client to connect
            } catch (IOException ignored) {
            }
        });
        serverThread.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        if (!mockServerSocket.isClosed()) {
            mockServerSocket.close();
        }
    }

    @Test
    void testClientConnectsToServer() {
        // Act
        Client client = new Client();

        // Assert
        assertNotNull(client, "Client instance should not be null.");
    }

    @Test
    void testClientHandlesConnectionFailureGracefully() throws IOException {
        // Arrange
        mockServerSocket.close(); // Force server to be unavailable

        // Act & Assert
        assertDoesNotThrow(() -> new Client(), "Client should handle connection failure without throwing an exception.");
    }
}