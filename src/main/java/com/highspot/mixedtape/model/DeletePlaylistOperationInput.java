package com.highspot.mixedtape.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeletePlaylistOperationInput {

    @JsonProperty("playlist_id")
    String playlistId;
}
