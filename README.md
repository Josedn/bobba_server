
# [bobba.io](https://bobba.io/) server &middot; [![GitHub license](https://img.shields.io/badge/license-GNU-blue.svg)](https://github.com/josedn/bobba_client/blob/master/LICENSE)
![bobba](https://i.imgur.com/wGITX22.png)

Habbo Hotel (r60+) remake made with modern (as 2019) technologies.
This repo features a compatible [bobba_client](https://github.com/Josedn/bobba_client) server written in Java.

## Features:
* Basic room manager
* Pathfinding with A* algorithm
* Catalogue
* Navigator
* Console  

## Requirements
* Maven
* Java 8
* MySQL server
  
## Configuration
There's a basic database dump included in the repo called **bobba.sql**. You should upload it to a MySQL server in order to run the server.
Also, the first time you run the server, a **config.txt** file will be created. Be sure to fill it out.

## Building
> mvn package

## Related projects
* [bobba_client](https://github.com/Josedn/bobba_client): Official bobba client.

##
![Preview](https://i.imgur.com/u9RcF7y.png)
##
Made with <3 by Relevance.
