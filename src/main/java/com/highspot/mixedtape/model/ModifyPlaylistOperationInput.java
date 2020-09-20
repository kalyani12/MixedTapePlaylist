package com.highspot.mixedtape.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ModifyPlaylistOperationInput {
    @JsonProperty("playlist_id")
    private String playlistId;

    @JsonProperty("song_id")
    private String songId;

}
