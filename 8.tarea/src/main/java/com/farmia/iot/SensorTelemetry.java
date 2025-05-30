/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.farmia.iot;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class SensorTelemetry extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 1739565162998128593L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"SensorTelemetry\",\"namespace\":\"com.farmia.iot\",\"fields\":[{\"name\":\"sensor_id\",\"type\":{\"type\":\"string\",\"arg.properties\":{\"regex\":\"sensor_[1-9]{3}\"}}},{\"name\":\"temperature\",\"type\":{\"type\":\"float\",\"arg.properties\":{\"range\":{\"min\":0.0,\"max\":45.0}}}},{\"name\":\"humidity\",\"type\":{\"type\":\"float\",\"arg.properties\":{\"range\":{\"min\":0.0,\"max\":100.0}}}},{\"name\":\"soil_fertility\",\"type\":{\"type\":\"float\",\"arg.properties\":{\"range\":{\"min\":0.0,\"max\":100.0}}}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<SensorTelemetry> ENCODER =
      new BinaryMessageEncoder<SensorTelemetry>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<SensorTelemetry> DECODER =
      new BinaryMessageDecoder<SensorTelemetry>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<SensorTelemetry> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<SensorTelemetry> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<SensorTelemetry> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<SensorTelemetry>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this SensorTelemetry to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a SensorTelemetry from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a SensorTelemetry instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static SensorTelemetry fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.lang.CharSequence sensor_id;
   private float temperature;
   private float humidity;
   private float soil_fertility;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public SensorTelemetry() {}

  /**
   * All-args constructor.
   * @param sensor_id The new value for sensor_id
   * @param temperature The new value for temperature
   * @param humidity The new value for humidity
   * @param soil_fertility The new value for soil_fertility
   */
  public SensorTelemetry(java.lang.CharSequence sensor_id, java.lang.Float temperature, java.lang.Float humidity, java.lang.Float soil_fertility) {
    this.sensor_id = sensor_id;
    this.temperature = temperature;
    this.humidity = humidity;
    this.soil_fertility = soil_fertility;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return sensor_id;
    case 1: return temperature;
    case 2: return humidity;
    case 3: return soil_fertility;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: sensor_id = (java.lang.CharSequence)value$; break;
    case 1: temperature = (java.lang.Float)value$; break;
    case 2: humidity = (java.lang.Float)value$; break;
    case 3: soil_fertility = (java.lang.Float)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'sensor_id' field.
   * @return The value of the 'sensor_id' field.
   */
  public java.lang.CharSequence getSensorId() {
    return sensor_id;
  }


  /**
   * Sets the value of the 'sensor_id' field.
   * @param value the value to set.
   */
  public void setSensorId(java.lang.CharSequence value) {
    this.sensor_id = value;
  }

  /**
   * Gets the value of the 'temperature' field.
   * @return The value of the 'temperature' field.
   */
  public float getTemperature() {
    return temperature;
  }


  /**
   * Sets the value of the 'temperature' field.
   * @param value the value to set.
   */
  public void setTemperature(float value) {
    this.temperature = value;
  }

  /**
   * Gets the value of the 'humidity' field.
   * @return The value of the 'humidity' field.
   */
  public float getHumidity() {
    return humidity;
  }


  /**
   * Sets the value of the 'humidity' field.
   * @param value the value to set.
   */
  public void setHumidity(float value) {
    this.humidity = value;
  }

  /**
   * Gets the value of the 'soil_fertility' field.
   * @return The value of the 'soil_fertility' field.
   */
  public float getSoilFertility() {
    return soil_fertility;
  }


  /**
   * Sets the value of the 'soil_fertility' field.
   * @param value the value to set.
   */
  public void setSoilFertility(float value) {
    this.soil_fertility = value;
  }

  /**
   * Creates a new SensorTelemetry RecordBuilder.
   * @return A new SensorTelemetry RecordBuilder
   */
  public static com.farmia.iot.SensorTelemetry.Builder newBuilder() {
    return new com.farmia.iot.SensorTelemetry.Builder();
  }

  /**
   * Creates a new SensorTelemetry RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new SensorTelemetry RecordBuilder
   */
  public static com.farmia.iot.SensorTelemetry.Builder newBuilder(com.farmia.iot.SensorTelemetry.Builder other) {
    if (other == null) {
      return new com.farmia.iot.SensorTelemetry.Builder();
    } else {
      return new com.farmia.iot.SensorTelemetry.Builder(other);
    }
  }

  /**
   * Creates a new SensorTelemetry RecordBuilder by copying an existing SensorTelemetry instance.
   * @param other The existing instance to copy.
   * @return A new SensorTelemetry RecordBuilder
   */
  public static com.farmia.iot.SensorTelemetry.Builder newBuilder(com.farmia.iot.SensorTelemetry other) {
    if (other == null) {
      return new com.farmia.iot.SensorTelemetry.Builder();
    } else {
      return new com.farmia.iot.SensorTelemetry.Builder(other);
    }
  }

  /**
   * RecordBuilder for SensorTelemetry instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<SensorTelemetry>
    implements org.apache.avro.data.RecordBuilder<SensorTelemetry> {

    private java.lang.CharSequence sensor_id;
    private float temperature;
    private float humidity;
    private float soil_fertility;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.farmia.iot.SensorTelemetry.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.sensor_id)) {
        this.sensor_id = data().deepCopy(fields()[0].schema(), other.sensor_id);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.temperature)) {
        this.temperature = data().deepCopy(fields()[1].schema(), other.temperature);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.humidity)) {
        this.humidity = data().deepCopy(fields()[2].schema(), other.humidity);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.soil_fertility)) {
        this.soil_fertility = data().deepCopy(fields()[3].schema(), other.soil_fertility);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
    }

    /**
     * Creates a Builder by copying an existing SensorTelemetry instance
     * @param other The existing instance to copy.
     */
    private Builder(com.farmia.iot.SensorTelemetry other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.sensor_id)) {
        this.sensor_id = data().deepCopy(fields()[0].schema(), other.sensor_id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.temperature)) {
        this.temperature = data().deepCopy(fields()[1].schema(), other.temperature);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.humidity)) {
        this.humidity = data().deepCopy(fields()[2].schema(), other.humidity);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.soil_fertility)) {
        this.soil_fertility = data().deepCopy(fields()[3].schema(), other.soil_fertility);
        fieldSetFlags()[3] = true;
      }
    }

    /**
      * Gets the value of the 'sensor_id' field.
      * @return The value.
      */
    public java.lang.CharSequence getSensorId() {
      return sensor_id;
    }


    /**
      * Sets the value of the 'sensor_id' field.
      * @param value The value of 'sensor_id'.
      * @return This builder.
      */
    public com.farmia.iot.SensorTelemetry.Builder setSensorId(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.sensor_id = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'sensor_id' field has been set.
      * @return True if the 'sensor_id' field has been set, false otherwise.
      */
    public boolean hasSensorId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'sensor_id' field.
      * @return This builder.
      */
    public com.farmia.iot.SensorTelemetry.Builder clearSensorId() {
      sensor_id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'temperature' field.
      * @return The value.
      */
    public float getTemperature() {
      return temperature;
    }


    /**
      * Sets the value of the 'temperature' field.
      * @param value The value of 'temperature'.
      * @return This builder.
      */
    public com.farmia.iot.SensorTelemetry.Builder setTemperature(float value) {
      validate(fields()[1], value);
      this.temperature = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'temperature' field has been set.
      * @return True if the 'temperature' field has been set, false otherwise.
      */
    public boolean hasTemperature() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'temperature' field.
      * @return This builder.
      */
    public com.farmia.iot.SensorTelemetry.Builder clearTemperature() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'humidity' field.
      * @return The value.
      */
    public float getHumidity() {
      return humidity;
    }


    /**
      * Sets the value of the 'humidity' field.
      * @param value The value of 'humidity'.
      * @return This builder.
      */
    public com.farmia.iot.SensorTelemetry.Builder setHumidity(float value) {
      validate(fields()[2], value);
      this.humidity = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'humidity' field has been set.
      * @return True if the 'humidity' field has been set, false otherwise.
      */
    public boolean hasHumidity() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'humidity' field.
      * @return This builder.
      */
    public com.farmia.iot.SensorTelemetry.Builder clearHumidity() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'soil_fertility' field.
      * @return The value.
      */
    public float getSoilFertility() {
      return soil_fertility;
    }


    /**
      * Sets the value of the 'soil_fertility' field.
      * @param value The value of 'soil_fertility'.
      * @return This builder.
      */
    public com.farmia.iot.SensorTelemetry.Builder setSoilFertility(float value) {
      validate(fields()[3], value);
      this.soil_fertility = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'soil_fertility' field has been set.
      * @return True if the 'soil_fertility' field has been set, false otherwise.
      */
    public boolean hasSoilFertility() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'soil_fertility' field.
      * @return This builder.
      */
    public com.farmia.iot.SensorTelemetry.Builder clearSoilFertility() {
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public SensorTelemetry build() {
      try {
        SensorTelemetry record = new SensorTelemetry();
        record.sensor_id = fieldSetFlags()[0] ? this.sensor_id : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.temperature = fieldSetFlags()[1] ? this.temperature : (java.lang.Float) defaultValue(fields()[1]);
        record.humidity = fieldSetFlags()[2] ? this.humidity : (java.lang.Float) defaultValue(fields()[2]);
        record.soil_fertility = fieldSetFlags()[3] ? this.soil_fertility : (java.lang.Float) defaultValue(fields()[3]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<SensorTelemetry>
    WRITER$ = (org.apache.avro.io.DatumWriter<SensorTelemetry>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<SensorTelemetry>
    READER$ = (org.apache.avro.io.DatumReader<SensorTelemetry>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.sensor_id);

    out.writeFloat(this.temperature);

    out.writeFloat(this.humidity);

    out.writeFloat(this.soil_fertility);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.sensor_id = in.readString(this.sensor_id instanceof Utf8 ? (Utf8)this.sensor_id : null);

      this.temperature = in.readFloat();

      this.humidity = in.readFloat();

      this.soil_fertility = in.readFloat();

    } else {
      for (int i = 0; i < 4; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.sensor_id = in.readString(this.sensor_id instanceof Utf8 ? (Utf8)this.sensor_id : null);
          break;

        case 1:
          this.temperature = in.readFloat();
          break;

        case 2:
          this.humidity = in.readFloat();
          break;

        case 3:
          this.soil_fertility = in.readFloat();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










