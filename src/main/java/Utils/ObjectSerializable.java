package Utils;

import java.io.*;

/**
 * Created by carlosmorais on 07/03/16.
 */
public abstract class ObjectSerializable {

    public static byte[] ObjectToBytes(Object o){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] res = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(o);
            res = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public static Object BytesToObject(byte[] bytes){
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        Object o = null;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return o;
    }
}
