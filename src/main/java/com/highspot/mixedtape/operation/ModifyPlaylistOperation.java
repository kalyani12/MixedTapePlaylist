package com.highspot.mixedtape.operation;

import com.highspot.mixedtape.model.Mixedtape;
import com.highspot.mixedtape.model.ModifyPlaylistOperationInput;
import com.highspot.mixedtape.model.Playlist;
import com.highspot.mixedtape.model.Song;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ModifyPlaylistOperation extends Operation {

    public void execute(Mixedtape mixedtape, ModifyPlaylistOperationInput modifyPlaylistOperationInput) {

        List<Playlist> currentPlaylist = mixedtape.getPlaylists();
        boolean isSuccess = false;

        for (Playlist playlist : currentPlaylist) {
            if (playlist.getId().equals(modifyPlaylistOperationInput.getPlaylistId())) {

                List<String> existingSongIds = playlist.getSongIds();
                if (isValidSongId(modifyPlaylistOperationInput.getSongId(), mixedtape.getSongs())) {
                    existingSongIds.add(modifyPlaylistOperationInput.getSongId());
                    isSuccess = true;
                    break;
                } else {
                    throw new IllegalArgumentException(String.format("Song id %s does not exist.",
                            modifyPlaylistOperationInput.getSongId()));
                }
            }
        }

        if (!isSuccess) {
            throw new IllegalArgumentException(String.format("PlaylistId %s does not exist.",
                    modifyPlaylistOperationInput.getPlaylistId()));
        }
    }

    private boolean isValidSongId(String newSongId, List<Song> existingSongs) {
        Set<String> existingSongIds = new HashSet<>();
        for (Song song : existingSongs) {
            existingSongIds.add(song.getId());
        }
        return existingSongIds.contains(newSongId);
    }
}
