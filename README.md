# InvisibleServer

Backend server for the Invisible anonymous messaging platform. Handles secure client connections and message routing using a custom packet-based protocol.

## Features

- 🔌 Handles multiple client connections simultaneously
- 🔒 Supports encrypted message routing
- ⚡ Efficient packet-based communication protocol
- 📊 Connection monitoring and keep-alive system
- 📝 Comprehensive logging with Log4j2
- 🔄 Cross-platform compatibility

## Requirements

- Java 16 or higher
- Maven 3.6+ (for building from source)

## Installation

### Direct Download
Download the latest pre-built JAR from [Releases](https://github.com/HastG99/InvisibleServer/releases) and run:
```bash
java -jar InvisibleServer.jar [port]
```

### Building from Source
```bash
git clone https://github.com/yourusername/InvisibleServer.git
cd InvisibleServer
mvn clean package
java -jar target/InvisibleServer-1.0-SNAPSHOT.jar [port]
```

## Usage

### Starting the Server
```bash
# Use default port (1337)
java -jar InvisibleServer.jar

# Specify custom port
java -jar InvisibleServer.jar 8080
```

### Server Commands
The server runs in the background and handles client connections automatically. Use Ctrl+C to stop the server.

## Configuration

Server logging is configured in `src/main/resources/log4j2.xml`:
- Console logging (info level)
- File logging to `invisibleserver.log` (info level)
- Detailed logging to `invisibleserver-all.log` (all levels)

## Protocol Specification

The server uses a custom binary packet protocol:

### Packet Types
- Handshake (0x0) - Client connection initialization
- KeepAlive (0x1) - Connection maintenance
- Message (0x2) - Encrypted message transmission
- Ping/Response (0x3) - Server status requests

### Packet Structure
```
[1 byte: packet ID] [4 bytes: data length] [n bytes: packet data]
```

## Project Structure

```
InvisibleServer/
├── src/main/java/
│   └── ru/study/
│       ├── objects/           # Session management
│       ├── packets/           # Packet handling classes
│       ├── server/            # Server core components
│       └── Main.java          # Application entry point
└── src/main/resources/
    └── log4j2.xml            # Logging configuration
```

## Development

### Dependencies
- Lombok 1.18.26
- HikariCP 5.0.1
- Log4j2 2.23.1

### Building with Maven
```bash
mvn clean compile
```

### Package for Distribution
```bash
mvn clean package
```

## Performance

The server is designed to handle:
- Multiple concurrent client connections
- Efficient message routing with low latency
- Automatic connection cleanup for inactive clients

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

For support and questions, please open an issue on GitHub.

## Related Projects

- [InvisibleDesktop](https://github.com/HastG99/InvisibleDesktop) - Client application for the messaging platform

---

**Note**: This server is designed for educational purposes. Always ensure compliance with local laws and regulations when deploying network services.
