package com.example.mybatis.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.*;

public class SerializeUtils {

    private static Logger logger = LoggerFactory.getLogger(SerializeUtils.class);

    /**
     * 序列化
     * @param object
     * @return
     * @throws SerializationException
     */
    public static byte[] serialize(Object object) throws SerializationException {
        byte[] result = null;

        if (object == null) {
            return new byte[0];
        }
        try (
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream)
        ){

            if (!(object instanceof Serializable)) {
                throw new IllegalArgumentException(SerializeUtils.class.getSimpleName() + " requires a Serializable payload " +
                        "but received an object of type [" + object.getClass().getName() + "]");
            }

            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            result =  byteStream.toByteArray();
        } catch (Exception ex) {
            logger.error("Failed to serialize",ex);
        }
        return result;
    }

    /**
     * 反序列化
     * @param bytes
     * @return
     * @throws SerializationException
     */
    public static Object deserialize(byte[] bytes) throws SerializationException {

        Object result = null;

        if (isEmpty(bytes)) {
            return null;
        }

        try (
                ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteStream)
        ){
            result = objectInputStream.readObject();
        } catch (Exception e) {
            logger.error("Failed to deserialize",e);
        }
        return result;
    }

    public static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }
}