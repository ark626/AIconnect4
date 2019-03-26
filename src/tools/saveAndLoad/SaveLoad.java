package tools.saveAndLoad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import org.springframework.util.ReflectionUtils;

public class SaveLoad {



    public static void prepareSave(Object obj, String filepath) throws IOException {
        File f = new File(filepath);
        FileOutputStream fileOut = new FileOutputStream(f.getAbsolutePath());
        ObjectOutputStream out = new ObjectOutputStream(fileOut);

        loopOverObjectWithFieldsSavingThem(obj, out);
        out.close();
        fileOut.close();
    }

    public static Object prepareLoad(String filepath, Class contentClass) throws IOException, InstantiationException, IllegalAccessException {
        File f = new File(filepath);
        FileInputStream fileIn = new FileInputStream(f.getAbsolutePath());
        ObjectInputStream out = new ObjectInputStream(fileIn);

        Object obj = contentClass.newInstance(); 
        Object result = loopOverObjectWithFieldsLoadingThem(obj, out);

        out.close();
        fileIn.close();

        return result;
    }

    public static void loopOverObjectWithFieldsSavingThem(Object obj, ObjectOutputStream out) throws IOException {

        out.writeBoolean(obj == null);
        ReflectionUtils.doWithFields(obj.getClass(), field -> {

            // System.out.println("Field name: " + field.getName());
            field.setAccessible(true);
            // System.out.println("Field name: " + field);
            // System.out.println("Field value: " + field.get(obj));

            try {
                saveField(field, obj, out);
            } catch (ClassNotFoundException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        });
    }

    public static Object loopOverObjectWithFieldsLoadingThem(Object obj, ObjectInputStream in) throws IOException {


        if (!in.readBoolean()) {
            ReflectionUtils.doWithFields(obj.getClass(), field -> {

                System.out.println("Field name: " + field.getName());
                field.setAccessible(true);

                // System.out.println("Field name: " + field);
                System.out.println("Field value: " + field.get(obj));

                try {
                    loadField(field, obj, in);
                } catch (ClassNotFoundException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }



            });
        }
        return obj;
    }

    public static void main(String[] args) {

        System.out.println("Started");
        SaveLoadTest pool = new SaveLoadTest();
        SaveLoad saveLoad = new SaveLoad();


        ArrayList<String> strings = new ArrayList<>();
        strings.add("A");
        strings.add("B");
        strings.add("C");
        pool.testInt = 1;
        pool.testStrings = strings;

        SaveLoadTest saveLoad1 = new SaveLoadTest();
        SaveLoadTest saveLoad2 = new SaveLoadTest();
        SaveLoadTest saveLoad3 = new SaveLoadTest();

        saveLoad1.testInt = 3;
        saveLoad1.testStrings = strings;
        saveLoad2.testInt = 4;
        saveLoad2.testStrings = strings;
        saveLoad3.testInt = 5;
        saveLoad3.testStrings = strings;

        ArrayList<SaveLoadTest> saveLoads = new ArrayList<>();
        saveLoads.add(saveLoad1);
        saveLoads.add(saveLoad2);
        saveLoads.add(saveLoad3);

        pool.rekursions = saveLoads;

        try {
            saveLoad.prepareSave(pool, "S");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            System.out.println("LOADING_--------------------------------");
            SaveLoadTest second = (SaveLoadTest) saveLoad.prepareLoad("S", SaveLoadTest.class);
            // System.out.println(""+second.toString()+pool.toString() +" "+pool.equals(second));
            System.out.println("" + second.toString());
            System.out.println("" + pool.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    private static void saveField(Field field, Object obj, ObjectOutputStream out)
            throws IllegalArgumentException, IllegalAccessException, IOException, ClassNotFoundException {


        // System.out.println(field.getType().getName());
        System.out.println(field.getGenericType());

        boolean isArrayList = isFieldArrayList(field);
        boolean isPrimitive = isFieldPrimitiveType(field);
        boolean isTransient = isFieldTransient(field);

        if (isTransient) {
            return;
        } ;
        if (isArrayList) {

            ArrayList<?> list = (ArrayList<?>) field.get(obj);

            ParameterizedType listType = (ParameterizedType) field.getGenericType();
            Class contentClass = (Class) listType.getActualTypeArguments()[0];

            out.writeInt(list.size());
            for (Object element : list) {
                // System.out.println(contentClass.getName() + " SubElementAnalyse");
                // System.out.println("");
                loopOverObjectWithFieldsSavingThem(contentClass.cast(element), out);


            }
            return;
        }
        if (isPrimitive) {
            // System.out.println(
            // " Draw Primitive Element " + field.getType()
            // .getName());
            System.out.println("");
            isFieldPrimitiveType(field);
            return;
        }

        // System.out.println(
        // field.getType()
        // .getName() + " SubElementAnalyse");
        System.out.println("");
        loopOverObjectWithFieldsSavingThem(
                field.getType()
                        .cast(field.get(obj)),
                    out);


        // writing KI's
    }

    private static void loadField(Field field, Object obj, ObjectInputStream out)
            throws IllegalArgumentException, IllegalAccessException, IOException, ClassNotFoundException, InstantiationException {


        // System.out.println(field.getType().getName());
        

        boolean isArrayList = isFieldArrayList(field);
        boolean isPrimitive = isFieldPrimitiveType(field);
        boolean isTransient = isFieldTransient(field);
        
        System.out.println(field.getGenericType() + "Array?"+isArrayList+"Primitive?"+isPrimitive+"Transient?"+isTransient);

        if (isTransient) {
            return;
        } ;
        if (isPrimitive) {
            System.out.println(
                    " Draw Primitive Element " + field.getType()
                            .getName());
            System.out.println(";;;");
            setPrimitiveField(field, obj, out);
            return;
        }
        if (isArrayList) {

            ArrayList<Object> list = (ArrayList<Object>) field.get(obj);

            ParameterizedType listType = (ParameterizedType) field.getGenericType();

            Class contentClass = (Class) listType.getActualTypeArguments()[0];

            int size = out.readInt();
            for (int i = 0; i < size; i++) {
                System.out.println(contentClass.getName() + " SubElementAnalyse " + contentClass.getName());
                System.out.println("");
                Object temp = loopOverObjectWithFieldsLoadingThem(contentClass.newInstance(), out);
                // temp = out.readObject();
                list.add(contentClass.cast(temp));

            }
            field.set(obj, list);
            return;
        }
        

        System.out.println(
                field.getType()
                        .getName() + " SubElementAnalyse");
        System.out.println("");

        field.set(
                obj,
                    loopOverObjectWithFieldsLoadingThem(
                            field.getType()
                                    .cast(field.get(obj)),
                                out));


        // writing KI's
    }

    private static boolean isFieldPrimitiveType(Field field) {

        String typ = field.getType()
                .getTypeName();
        return "int".equals(typ) || "double".equals(typ) || "float".equals(typ) || "long".equals(typ) || "boolean".equals(typ) || "char".equals(typ)
                || "byte".equals(typ) || "short".equals(typ) || "java.lang.String".equals(typ) || "char[]".equals(typ);
    }

    private static void savePrimitiveField(Field field, Object obj, ObjectOutputStream out)
            throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, IOException {

        String typ = field.getType()
                .getTypeName();
        if ("int".equals(typ)) {
            out.writeInt(field.getInt(obj));
        }
        if ("double".equals(typ)) {
            out.writeDouble(field.getDouble(obj));
        }
        if ("float".equals(typ)) {
            out.writeFloat(field.getFloat(obj));
        }
        if ("long".equals(typ)) {
            out.writeLong(field.getLong(obj));
        }
        if ("boolean".equals(typ)) {
            out.writeBoolean(field.getBoolean(obj));
        }
        if ("char".equals(typ)) {
            out.writeChar(field.getChar(obj));
        }
        if ("byte".equals(typ)) {
            out.writeByte(field.getByte(obj));
        }
        if ("short".equals(typ)) {
            out.writeShort(field.getShort(obj));
        }
        if ("java.lang.String".equals(typ)) {
            String string = (String) field.get(obj);
            out.writeBoolean(string == null);
            if (string != null) {
                out.writeInt(string.length());
                out.writeChars(string);
            }
        }
        if ("char[]".equals(typ)) {
            String string = (String) field.get(obj);
            out.writeBoolean(string == null);
            if (string != null) {
                out.writeInt(string.length());
                out.writeChars(string);
            }
        }

    }

    private static void setPrimitiveField(Field field, Object obj, ObjectInputStream out)
            throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, IOException {

        String typ = field.getType()
                .getTypeName();
        if ("int".equals(typ)) {
            field.set(obj, out.readInt());
        }
        if ("double".equals(typ)) {
            field.set(obj, out.readDouble());
        }
        if ("float".equals(typ)) {
            field.set(obj, out.readFloat());
        }
        if ("long".equals(typ)) {
            field.set(obj, out.readLong());
        }
        if ("boolean".equals(typ)) {
            field.set(obj, out.readBoolean());
        }
        if ("char".equals(typ)) {
            field.set(obj, out.readChar());
        }
        if ("byte".equals(typ)) {
            field.set(obj, out.readByte());
        }
        if ("short".equals(typ)) {
            field.set(obj, out.readShort());
        }
        if ("java.lang.String".equals(typ)) {

            boolean isNull = out.readBoolean();

            if (!isNull) {
                int size = out.readInt();
                String result = "";
                for (int i = 0; i < size; i++) {
                    result += out.readChar();
                }
                field.set(obj, result);
            }
        }
        if ("char[]".equals(typ)) {

            boolean isNull = out.readBoolean();

            if (!isNull) {
                int size = out.readInt();
                String result = "";
                for (int i = 0; i < size; i++) {
                    result += out.readChar();
                }
                field.set(obj, result);
            }
        }
        
    }

    private static boolean isFieldArrayList(Field field) {

        String typ = field.getType()
                .getName();
        return "java.util.ArrayList".equals(typ) ||typ.startsWith("java.util.ArrayList");
    }

    private static boolean isFieldTransient(Field field) {
        return Modifier.isTransient(field.getModifiers()) || Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())
                || Modifier.isPrivate(field.getModifiers());
    }



}
