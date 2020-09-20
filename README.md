### Mixed Tape Playlist 

The application provides functionality to manage playlists for a user. 
It applies the changes on the exiting data to produce the updated playlist output. 


### Prerequisites:
* Java 7 or above.
* Maven

### Clone repository:


### To build the application:
In the project directory run:
* `mvn clean install`
* `mvn exec:java -Dexec.mainClass="com.highspot.mixedtape.MixedTapeDriver"`

### File Path:
* Input filepath - /resources/mixedtape.json
* Changes filepath - /resources/changes.json
* Output filepath - ./output.json


### Development considerations:
* Applying the changes from changes.json 
    * Creating a new Playlist - Input is user id and song ids
```{
         "operationType": "createPlaylist",
         "operationInput": {
           "playlist": {
             "user_id": "2",
             "song_ids": [
               "4",
               "5"
             ]
           }
         }
       }
 ```
  * Deleting a playlist - Input is playlist id to be deleted. 
```
{
    "operationType": "deletePlaylist",
    "operationInput": {
      "playlist_id": "1"
    }
  }
```
   * Updating a playlist - Input is playlist id to be updated with the song ids
```
{
"operationType": "updatePlaylist",
    "operationInput": {
      "playlist_id": "2",
      "song_id": "1"
    }
}
```


### Future improvements
* Validations for the user id, song id to be updated. Support to apply partial changes in case of intermediate change failure.
* Adding new operations (eg: adding new song) can be be easily supported by adding OperationType and OperationInput Json. 
* Unit testing and logging. 

 
### Scaling the application:
* Handling large input file -
    * Stream the file instead of reading the entire file in memory.  
    * Using database instead of mixedtape.json as input file.
        * Create 3 tables to store the mixed tape data - 
            * User - stores the  user data partitioned by the user Id.
            * Songs - stores the songs data partitioned song id. 
            * Playlist - stores the playlist data partitioned by playlist Id.
    * Further scaling can be achieved by deploying the application as a service. Expose the API for CRUD operations for the playlist, 
      song and users. Horizontally scale the application to support the large requests.  

* Handling very large changes file -
    *  We can have a algorithm which decides on the changes that can be executed in parallel. 
    Eg : Changes for two different users can be run in parallel or different playlist id can be modified in parallel.  
    * The changes event can also be processed by event streaming using kafka. We can define different Kafka topic for operations 
   that can be executed out of sequence.  

* Improving the efficiency - 
    * When the input is in form on json : We can have a in memory Map/Set of all the valid song ids, playlists, and users for faster 
       lookup and validations. 
    * When the data is stored in database we can use distributed cache eg. Redis for faster lookup and achieve low latency. 

