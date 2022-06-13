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

### Appearance

#### iconPath

This specifies a custom icon pack.

By default, this is set to an empty string, which indicates that Jess shall use the internal icon pack.

##### Possible values:

Any folder that contains icons that meet the following criteria:

- PNG-Format and .png-ending
- Tested resolution: 1024x1024 - lower or higher should be no problem, but that's untested.

The following files are looked for:

- bPawn.png -> black pawn
- bRook.png -> black rook
- bKnight.png -> black knight
- bBishop.png -> black bishop
- bKing.png -> black king
- bQueen.png -> black queen

- wPawn.png -> white pawn
- wRook.png -> white rook
- wKnight.png -> white knight
- wBishop.png -> white bishop
- wKing.png -> white king
- wQueen.png -> white queen

Many icon packs can be found here:

[Lichess Icons](https://github.com/lichess-org/lila/tree/master/public/piece)

These icons are in the svg-format. To convert them to PNGs, you may use the following bash script:

```shell
#!/bin/bash
FILES="./*.svg"
for f in $FILES
do
	echo "Processing $f file..."
	inkscape -w 1024 -h 1024 $f -o $(basename $f .svg).png
	mkdir -p input
	mv $f input
done

mv ./bP.png ./bPawn.png
mv ./bR.png ./bRook.png
mv ./bN.png ./bKnight.png
mv ./bB.png ./bBishop.png
mv ./bK.png ./bKing.png
mv ./bQ.png ./bQueen.png

mv ./wP.png ./wPawn.png
mv ./wR.png ./wRook.png
mv ./wN.png ./wKnight.png
mv ./wB.png ./wBishop.png
mv ./wK.png ./wKing.png
mv ./wQ.png ./wQueen.png
```

Note: Inkscape is needed for this script to work.

#### Colors

The different board colors can also be set.

All of them have three or four values depending on their type, but they always mean the same:

- R: Red channel
- G: Green channel
- B: Blue channel
- A: Alpha channel (transparency, not always available)

The default values are the following:

```json
"blackCellColor": {
"R": 61,
"G": 71,
"B": 83
},
"whiteCellColor": {
"R": 255,
"G": 255,
"B": 255
},
"activatedCellColor": {
"R": 127,
"G": 255,
"B": 122,
"A": 0.75
},
"selectedCellColor": {
"R": 100,
"G": 224,
"B": 255,
"A": 0.75
},
"checkCellColor": {
"R": 255,
"G": 100,
"B": 100,
"A": 0.25
}
```

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
