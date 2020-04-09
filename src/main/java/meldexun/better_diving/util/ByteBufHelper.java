package meldexun.better_diving.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import meldexun.better_diving.BetterDiving;

public class ByteBufHelper {

	private ByteBufHelper() {

	}

	public static void fromBytes(Object object, ByteBuf buf) {
		Class objectClass = object.getClass();
		Field[] fields = objectClass.getFields();
		byte booleanFields = buf.readByte();
		byte byteFields = buf.readByte();
		byte shortFields = buf.readByte();
		byte integerFields = buf.readByte();
		byte longFields = buf.readByte();
		byte floatFields = buf.readByte();
		byte doubleFields = buf.readByte();
		byte objectFields = buf.readByte();
		try {
			for (int i = 0; i < booleanFields; i++) {
				int fieldNumber = buf.readByte();
				fields[fieldNumber].set(object, buf.readBoolean());
			}
			for (int i = 0; i < byteFields; i++) {
				int fieldNumber = buf.readByte();
				fields[fieldNumber].set(object, buf.readByte());
			}
			for (int i = 0; i < shortFields; i++) {
				int fieldNumber = buf.readByte();
				fields[fieldNumber].set(object, buf.readShort());
			}
			for (int i = 0; i < integerFields; i++) {
				int fieldNumber = buf.readByte();
				fields[fieldNumber].set(object, buf.readInt());
			}
			for (int i = 0; i < longFields; i++) {
				int fieldNumber = buf.readByte();
				fields[fieldNumber].set(object, buf.readLong());
			}
			for (int i = 0; i < floatFields; i++) {
				int fieldNumber = buf.readByte();
				fields[fieldNumber].set(object, buf.readFloat());
			}
			for (int i = 0; i < doubleFields; i++) {
				int fieldNumber = buf.readByte();
				fields[fieldNumber].set(object, buf.readDouble());
			}
			for (int i = 0; i < objectFields; i++) {
				int fieldNumber = buf.readByte();
				ByteBufHelper.fromBytes(fields[fieldNumber].get(object), buf);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			BetterDiving.logger.error("Failed to read bytes for " + objectClass, e);
		}
	}

	public static void toBytes(Object object, ByteBuf buf) {
		Class objectClass = object.getClass();
		Field[] fields = objectClass.getFields();
		Map<Byte, Field> booleanFields = new HashMap<>();
		Map<Byte, Field> byteFields = new HashMap<>();
		Map<Byte, Field> shortFields = new HashMap<>();
		Map<Byte, Field> integerFields = new HashMap<>();
		Map<Byte, Field> longFields = new HashMap<>();
		Map<Byte, Field> floatFields = new HashMap<>();
		Map<Byte, Field> doubleFields = new HashMap<>();
		Map<Byte, Field> objectFields = new HashMap<>();
		for (int i = 0; i < fields.length && i < 128; i++) {
			Field field = fields[i];
			if (field.isAnnotationPresent(Sync.class)) {
				Class fieldClass = field.getType();
				if (fieldClass.equals(Boolean.TYPE)) {
					booleanFields.put((byte) i, field);
				} else if (fieldClass.equals(Byte.TYPE)) {
					byteFields.put((byte) i, field);
				} else if (fieldClass.equals(Short.TYPE)) {
					shortFields.put((byte) i, field);
				} else if (fieldClass.equals(Integer.TYPE)) {
					integerFields.put((byte) i, field);
				} else if (fieldClass.equals(Long.TYPE)) {
					longFields.put((byte) i, field);
				} else if (fieldClass.equals(Float.TYPE)) {
					floatFields.put((byte) i, field);
				} else if (fieldClass.equals(Double.TYPE)) {
					doubleFields.put((byte) i, field);
				} else if (fieldClass.getSuperclass().equals(Object.class) && !fieldClass.equals(objectClass)) {
					objectFields.put((byte) i, field);
				}
			}
		}
		buf.writeByte(booleanFields.size());
		buf.writeByte(byteFields.size());
		buf.writeByte(shortFields.size());
		buf.writeByte(integerFields.size());
		buf.writeByte(longFields.size());
		buf.writeByte(floatFields.size());
		buf.writeByte(doubleFields.size());
		buf.writeByte(objectFields.size());
		try {
			for (Map.Entry<Byte, Field> entry : booleanFields.entrySet()) {
				buf.writeByte(entry.getKey());
				buf.writeBoolean(entry.getValue().getBoolean(object));
			}
			for (Map.Entry<Byte, Field> entry : byteFields.entrySet()) {
				buf.writeByte(entry.getKey());
				buf.writeByte(entry.getValue().getByte(object));
			}
			for (Map.Entry<Byte, Field> entry : shortFields.entrySet()) {
				buf.writeByte(entry.getKey());
				buf.writeShort(entry.getValue().getShort(object));
			}
			for (Map.Entry<Byte, Field> entry : integerFields.entrySet()) {
				buf.writeByte(entry.getKey());
				buf.writeInt(entry.getValue().getInt(object));
			}
			for (Map.Entry<Byte, Field> entry : longFields.entrySet()) {
				buf.writeByte(entry.getKey());
				buf.writeLong(entry.getValue().getLong(object));
			}
			for (Map.Entry<Byte, Field> entry : floatFields.entrySet()) {
				buf.writeByte(entry.getKey());
				buf.writeFloat(entry.getValue().getFloat(object));
			}
			for (Map.Entry<Byte, Field> entry : doubleFields.entrySet()) {
				buf.writeByte(entry.getKey());
				buf.writeDouble(entry.getValue().getDouble(object));
			}
			for (Map.Entry<Byte, Field> entry : objectFields.entrySet()) {
				buf.writeByte(entry.getKey());
				ByteBufHelper.toBytes(entry.getValue().get(object), buf);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			BetterDiving.logger.error("Failed to write bytes for " + objectClass, e);
		}
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface Sync {

	}

	public static void copy(Object source, Object target, boolean copyServerSettings) {
		if (source == null || target == null || source.getClass() != target.getClass()) {
			return;
		}
		Field[] fields = source.getClass().getFields();

		for (Field field : fields) {
			if (copyServerSettings || !field.isAnnotationPresent(ByteBufHelper.Sync.class)) {
				Class fieldClass = field.getType();

				try {
					if (fieldClass.equals(Boolean.TYPE) || fieldClass.equals(Byte.TYPE) || fieldClass.equals(Short.TYPE) || fieldClass.equals(Integer.TYPE) || fieldClass.equals(Long.TYPE) || fieldClass.equals(Float.TYPE)
							|| fieldClass.equals(Double.TYPE)) {
						field.set(target, field.get(source));
					} else if (fieldClass.getSuperclass().equals(Object.class)) {
						Object source2 = field.get(source);
						Object target2 = field.get(target);
						if (source2 != source && target2 != target) {
							ByteBufHelper.copy(field.get(source), field.get(target), copyServerSettings);
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					BetterDiving.logger.error("Failed to copy data from " + source + " to " + target, e);
				}
			}
		}
	}

}
