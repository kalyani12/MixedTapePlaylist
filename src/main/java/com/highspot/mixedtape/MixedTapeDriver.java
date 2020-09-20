package com.highspot.mixedtape;

/**
 * Main method.
 */
public class MixedTapeDriver {


    public static void main(String[] args) {

        MixedTapeProcessor processor = new MixedTapeProcessor();
        processor.process();
        System.out.println("Successfully Completed executing changes file. Check the output json.");
    }
}
