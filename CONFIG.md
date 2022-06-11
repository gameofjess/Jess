# Configuration

Jess can be configured by editing the config.json-file created when starting the application.

## Sample configuration

The following configuration will be generated on startup:

```json
{
  "logging": {
    "level": "INFO"
  },
  "hostMenuConfiguration": {
    "loadPublicIPAddress": true,
    "ipAddressServer": "https://ipaddr.gameofjess.com"
  },
  "serverConfiguration": {
    "defaultPort": 8887,
    "defaultHostname": "0.0.0.0"
  }
}
```

## Configuration possibilities

The following values can be edited:

### Logging

#### level

This specifies the user log level. By default, this is set to INFO.

##### Possible values:

- FATAL: Logs only fatal exceptions.
- ERROR: Also logs errors.
- WARN: Also logs warnings.
- INFO: Normal logging behavior. Also logs program start etc.
- DEBUG: Logs in-depth information about received messages and in-game board configuration.
- TRACE: Also logs internal chess logic and in-depth WebSocket implementation.

### HostMenuConfiguration

#### loadPublicIPAddress

This specifies whether the public IP Address should be shown and determined in the first place on the host menu.
Disabling this comes in handy when streaming and also allows for more privacy.

By default, this value is set to true.

#### ipAddressServer

This specifies a server that returns the IP address.

Notable examples for such servers are:

- https://checkip.amazonaws.com/ by Amazon Web Services
- https://ifconfig.me/ip

By default, this value is set to our own service [GameOfJess-IP-Service](https://ipaddr.gameofjess.com).
The rather simplistic source code for this service can be found
here: [Source](https://gist.github.com/Uggah/5e3ee8d4ca42d66913964e7e9cec9967)

### serverConfiguration

#### defaultPort

This specifies the standard port for a server instance. We advice not to change this if not necessary,
but it may come in handy in some configurations e.g. when installing a Jess server behind a reverse proxy.

By default, this is set to `8887`.

##### Possible values

All integers from `1000` to `65535`.

### defaultHostname

This specifies the standard hostname for a server instance. We advice not to change this if not necessary,
but it may come in handy in some configurations.

By default, this is set to `0.0.0.0`.

#### Possible values

All possible hostnames. Depends on host system.
