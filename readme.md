### Application demo
* Import postman_collection.json
* "search for artists" - returns list of artist by term provided as parameter in url localhost:8080/artists/{term};
* "add favourite artist" - creates relation between userId provided in the headers and artist provided as request body. Can be called only once per userId, second time api returns forbidden status;
* "get favourite artists top albums" - returns top 5 user favourite artist's albums by userId provided as header;