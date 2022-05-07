package ru.hoty.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializer {
    public static byte[] serialize(Object obj) throws IOException {
        try(ByteArrayOutputStream a = new ByteArrayOutputStream()) {
            try(ObjectOutputStream b = new ObjectOutputStream(a)) {
                b.writeObject(obj);
            }
            return a.toByteArray();
        }
    }
    
    public static Object deserialize(byte[] buf) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream a = new ByteArrayInputStream(buf)) {
            try(ObjectInputStream b = new ObjectInputStream(a)) {
                return b.readObject();
            }
        }
    }
}
