package com.highspot.mixedtape.operation;

import com.highspot.mixedtape.model.DeletePlaylistOperationInput;
import com.highspot.mixedtape.model.Mixedtape;
import com.highspot.mixedtape.model.Playlist;

import java.util.List;

public class DeletePlaylistOperation extends Operation {

    public void execute(Mixedtape mixedtape, DeletePlaylistOperationInput deletePlaylistOperationInput) {
        List<Playlist> currentPlaylist = mixedtape.getPlaylists();
        Playlist playListToRemove = null;
        for (Playlist playlist : currentPlaylist) {
            if (playlist.getId().equals(deletePlaylistOperationInput.getPlaylistId())) {
                playListToRemove = playlist;
                break;
            }
        }
        if (playListToRemove == null) {
            throw new IllegalArgumentException(String.format("Playlist id %s does not exist.", deletePlaylistOperationInput.getPlaylistId()));
        }
        currentPlaylist.remove(playListToRemove);
    }
}
