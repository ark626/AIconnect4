//package neat;
//
//import sun.misc.Unsafe;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.Serializable;
//import java.lang.reflect.Field;
//import java.nio.ByteBuffer;
//import java.util.Arrays;
//    
//    public class MemoryBuffer {
//        // getting Unsafe by reflection
//        public static final Unsafe unsafe = UnsafeUtil.getUnsafe();
//
//        private final byte[] buffer;
//
//        private static final long byteArrayOffset = unsafe.arrayBaseOffset(byte[].class);
//        private static final long longArrayOffset = unsafe.arrayBaseOffset(long[].class);
//        /* other offsets */
//
//        private static final int SIZE_OF_LONG = 8;
//        /* other sizes */
//
//        private long pos = 0;
//
//        public MemoryBuffer(int bufferSize) {
//          this.buffer = new byte[bufferSize];
//        }
//
//        public final byte[] getBuffer() {
//          return buffer;
//        }
//
//        public final void putLong(long value) {
//          unsafe.putLong(buffer, byteArrayOffset + pos, value);
//          pos += SIZE_OF_LONG;
//        }
//
//        public final long getLong() {
//          long result = unsafe.getLong(buffer, byteArrayOffset + pos);
//          pos += SIZE_OF_LONG;
//          return result;
//        }
//
//        public final void putLongArray(final long[] values) {
//          putInt(values.length);
//          long bytesToCopy = values.length << 3;
//          unsafe.copyMemory(values, longArrayOffset, buffer, byteArrayOffset + pos, bytesToCopy);
//          pos += bytesToCopy;
//        }
//
//
//        public final long[] getLongArray() {
//          int arraySize = getInt();
//          long[] values = new long[arraySize];
//          long bytesToCopy = values.length << 3;
//          unsafe.copyMemory(buffer, byteArrayOffset + pos, values, longArrayOffset, bytesToCopy);
//          pos += bytesToCopy;
//          return values;
//        }
//
//        /* other methods */
//      } 
//
//}
