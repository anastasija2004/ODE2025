package server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameServerTest {
    private Server server;

    @BeforeEach
    void setUp() {
        server = new Server();
    }

    @AfterEach
    void tearDown() {
        server.stopServer();
    }

    @Test
    void testStartGameServer() throws InterruptedException {
        // Act
        server.startServer();
        Thread.sleep(100); // Allow some time for the server to start

        // Assert
        assertTrue(server.isRunning(), "Server should be running after start.");
    }

    @Test
    void testStopGameServer() throws InterruptedException {
        // Arrange
        server.startServer();
        Thread.sleep(100); // Ensure the server has started

        // Act
        server.stopServer();
        Thread.sleep(100); // Allow some time for the server to stop

        // Assert
        assertFalse(server.isRunning(), "Server should not be running after stop.");
    }
}