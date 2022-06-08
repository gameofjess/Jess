# Configuration

Jess can be configured by editing the config.json-file created when starting the application.

## Sample configuration

The following configuration will be generated on startup:

```json
{
  "hostMenuConfiguration": {
    "loadPublicIPAddress": true,
    "ipAddressServer": "https://ipaddr.gameofjess.com"
  }
}
```

## Configuration possibilities

The following values can be edited:

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

