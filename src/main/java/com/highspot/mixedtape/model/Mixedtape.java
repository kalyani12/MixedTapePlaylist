package com.highspot.mixedtape.model;

import lombok.Data;

import java.util.List;

@Data
public class Mixedtape {

    private List<User> users;
    private List<Playlist> playlists;
    private List<Song> songs;
}
