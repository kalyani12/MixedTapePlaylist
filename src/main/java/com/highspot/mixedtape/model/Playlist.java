package com.highspot.mixedtape.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Playlist {

    private String id;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("song_ids")
    private List<String> songIds;
}
