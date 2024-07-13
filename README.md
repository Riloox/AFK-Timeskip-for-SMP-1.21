# AFK Timeskip Plugin

## Overview
AFK Timeskip is a Minecraft plugin for Spigot 1.21 servers that enhances the AFK (Away From Keyboard) experience. When a player goes AFK, they become invulnerable. If they are the only player on the server, the plugin increases the server tick rate to accelerate time passage, simulating a "time skip" effect.

## Features
- Toggle AFK status with a simple command
- Invulnerability for AFK players
- Increased tick rate when a single player is AFK (configurable)
- Automatic AFK disable on player movement or interaction
- Server-wide notifications for AFK status changes
- Configurable AFK tick rate

## Requirements
- Spigot 1.21 or higher
- Java 8 or higher

## Installation
1. Download the latest release of the AFK Timeskip plugin (.jar file) from the releases page.
2. Place the downloaded .jar file in your server's `plugins` folder.
3. Restart your Minecraft server.
4. The plugin will generate a default configuration file on first run.

## Configuration
After the first run, you can find the configuration file at `plugins/AfkTimeskip/config.yml`. 

You can modify the following setting:
- `afk-tick-rate`: The tick rate to set when a single player is AFK (default: 400)

After modifying the configuration, use the `/afkreload` command or restart your server to apply changes.

## Usage
- `/afk`: Toggle your AFK status
- `/afkreload`: Reload the plugin configuration (requires permission: `afktimeskip.reload`)

## Permissions
- `afktimeskip.reload`: Allows the use of the `/afkreload` command

## Notes
- The increased tick rate only applies when there is a single player on the server and they are AFK.
- Be cautious with very high tick rates as they may cause performance issues or unexpected behavior.
- This plugin uses Minecraft 1.21's built-in `/tick rate` command to change the server tick rate.

## Contributing
Contributions to the AFK Timeskip plugin are welcome! Please feel free to submit pull requests or create issues for bugs and feature requests.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support
If you encounter any issues or have questions, please open an issue on the GitHub repository.

---

Made with ❤️ for the Minecraft community.
