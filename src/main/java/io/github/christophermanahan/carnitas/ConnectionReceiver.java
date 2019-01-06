package io.github.christophermanahan.carnitas;

import java.io.BufferedReader;
import java.io.IOException;

public class ConnectionReceiver implements Receiver {

    private final BufferedReader reader;

    public ConnectionReceiver(BufferedReader reader) {
        this.reader = reader;
    }

    public String receiveLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public String receiveCharacters(int amount) {
        try {
            char[] characters = new char[amount];
            reader.read(characters);
            return new String(characters);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
