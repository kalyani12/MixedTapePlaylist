package com.highspot.mixedtape.operation;

import com.fasterxml.jackson.databind.JsonNode;
import com.highspot.mixedtape.model.DeletePlaylistOperationInput;
import lombok.Data;

/**
 * Operation class represents a type of change.
 * Eg. operationType = {@link DeletePlaylistOperation}
 * operationInput = {@link DeletePlaylistOperationInput}
 */
@Data
public class Operation {

    private String operationType;
    private JsonNode operationInput;
}
