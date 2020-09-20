package com.highspot.mixedtape;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.highspot.mixedtape.model.CreatePlaylistOperationInput;
import com.highspot.mixedtape.model.DeletePlaylistOperationInput;
import com.highspot.mixedtape.model.Mixedtape;
import com.highspot.mixedtape.model.ModifyPlaylistOperationInput;
import com.highspot.mixedtape.operation.CreatePlaylistOperation;
import com.highspot.mixedtape.operation.DeletePlaylistOperation;
import com.highspot.mixedtape.operation.ModifyPlaylistOperation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * MixedTapeProcessor class reads the input json and the changes file.
 * It executes the changes on the input and writes the output json.
 */

public class MixedTapeProcessor {

    private Mixedtape mixedtape;
    private final DeletePlaylistOperation deletePlaylistOperation = new DeletePlaylistOperation();
    private final CreatePlaylistOperation createPlaylistOperation = new CreatePlaylistOperation();
    private final ModifyPlaylistOperation modifyPlaylistOperation = new ModifyPlaylistOperation();
    private final ObjectMapper mapper = new ObjectMapper();
    private final ClassLoader classloader = Thread.currentThread().getContextClassLoader();

    public void process() {

        readMixedtape();
        ArrayNode changes = readChangesFile();
        executeChanges(changes);
        writeOutputFile();
    }

    private void executeChanges(ArrayNode changes) {
        try {

            for (JsonNode change : changes) {
                String operationType = change.get("operationType").asText();
                if (operationType.equals("deletePlaylist")) {
                    DeletePlaylistOperationInput deletePlaylistOperationInput = mapper.readValue(change.get("operationInput").toString(), DeletePlaylistOperationInput.class);
                    deletePlaylistOperation.execute(mixedtape, deletePlaylistOperationInput);
                } else if (operationType.equals("createPlaylist")) {
                    CreatePlaylistOperationInput createPlaylistOperationInput = mapper.readValue(change.get("operationInput").toString(), CreatePlaylistOperationInput.class);
                    createPlaylistOperation.execute(mixedtape, createPlaylistOperationInput);
                } else if (operationType.equals("updatePlaylist")) {
                    ModifyPlaylistOperationInput modifyPlaylistOperationInput = mapper.readValue(change.get("operationInput").toString(), ModifyPlaylistOperationInput.class);
                    modifyPlaylistOperation.execute(mixedtape, modifyPlaylistOperationInput);
                } else {
                    throw new IllegalArgumentException(String.format("Unsupported Operation: %s", operationType));
                }
            }
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot parse change input", e);
        }
    }

    private void readMixedtape() {
        try {

            InputStream inputStream = classloader.getResourceAsStream("mixedtape.json");
            mixedtape = mapper.readValue(inputStream, Mixedtape.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Cannot parse mixedtape.json.", e);
        }
    }

    private ArrayNode readChangesFile() {
        ArrayNode instructions;
        try {
            InputStream inputStream = classloader.getResourceAsStream("changes.json");
            instructions = mapper.readValue(inputStream, ArrayNode.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Cannot parse changes.json.", e);
        }
        return instructions;
    }

    private void writeOutputFile() {
        try {
            mapper.writeValue(new File("output.json"), mixedtape);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot write output.json.", e);
        }
    }
}
