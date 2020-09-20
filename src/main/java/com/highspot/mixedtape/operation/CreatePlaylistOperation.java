package com.highspot.mixedtape.operation;

import com.highspot.mixedtape.model.CreatePlaylistOperationInput;
import com.highspot.mixedtape.model.Mixedtape;
import com.highspot.mixedtape.model.Playlist;

import java.util.List;

public class CreatePlaylistOperation extends Operation {

    public void execute(Mixedtape mixedtape, CreatePlaylistOperationInput createPlaylistOperationInput) {
        List<Playlist> currentPlayList = mixedtape.getPlaylists();
        createPlaylistOperationInput.getPlaylist().setId(generateNewPlaylistId(currentPlayList));
        currentPlayList.add(createPlaylistOperationInput.getPlaylist());
        mixedtape.setPlaylists(currentPlayList);
    }

    private String generateNewPlaylistId(List<Playlist> currentPlayList) {
        Integer maxPlaylistId = -1;
        for (Playlist playlist : currentPlayList) {
            if (Integer.valueOf(playlist.getId()) > maxPlaylistId) {
                maxPlaylistId = Integer.valueOf(playlist.getId()) + 1;
            }
        }
        return maxPlaylistId.toString();
    }
}
