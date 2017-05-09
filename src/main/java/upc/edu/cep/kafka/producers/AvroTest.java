package upc.edu.cep.kafka.producers;

/**
 * Created by osboxes on 04/04/17.
 */

import com.google.common.io.Resources;
import kafka.producer.KeyedMessage;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.flume.source.avro.AvroFlumeEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class AvroTest {

    private static SpecificDatumReader<AvroFlumeEvent> reader = new SpecificDatumReader<AvroFlumeEvent>(AvroFlumeEvent.class);
    private static BinaryDecoder decoder = null;


    //hi
    public static void main(String[] args) throws Exception {
        KafkaProducer<String, String> producer;
        try (InputStream props = Resources.getResource("producer.props").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            producer = new KafkaProducer<>(properties);
        }
        String key = "key1";
        String userSchema = "{\"type\":\"record\"," +
                "\"name\":\"myrecord\"," +
                "\"fields\":[{\"name\":\"mylog\",\"type\":\"string\"}]}";
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(userSchema);
        int i = 0;
        while (true) {

            GenericRecord payload1 = new GenericData.Record(schema);
            //Step2 : Put data in that genericrecord object
            payload1.put("mylog", "'testdata'");
            //payload1.put("name", "अasa");
//            payload1.put("name", "dbevent1");
//            payload1.put("id", 111);
            System.out.println("Original Message : "+ payload1);
            //Step3 : Serialize the object to a bytearray
            DatumWriter<GenericRecord>writer = new SpecificDatumWriter<>(schema);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
            writer.write(payload1, encoder);
            encoder.flush();
            out.close();

            byte[] serializedBytes = out.toByteArray();
            System.out.println("Sending message in bytes : " + serializedBytes);
            System.out.println("Sending message in bytes : " + new String(serializedBytes));


            DatumReader<GenericRecord> reader = new SpecificDatumReader<GenericRecord>(schema);
            Decoder decoder = DecoderFactory.get().binaryDecoder(serializedBytes, null);
            GenericRecord payload2 = null;
            payload2 = reader.read(null, decoder);
            System.out.println("Message received : " + payload2.get("mylog"));

//            ByteArrayInputStream in =
//                    new ByteArrayInputStream(serializedBytes);
//            decoder = DecoderFactory.get().directBinaryDecoder(in, decoder);
//
//            AvroFlumeEvent avroevent = reader.read(null, decoder);
//
//            byte[] eventBody = avroevent.getBody().array();
//            Map headers = toStringMap(avroevent.getHeaders());

            Thread.sleep(10000);
        }
        //producer.close();
    }

    public static byte[] datumToByteArray(Schema schema, GenericRecord datum) throws IOException {
        GenericDatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(schema);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            Encoder e = EncoderFactory.get().binaryEncoder(os, null);
            writer.write(datum, e);
            e.flush();
            byte[] byteData = os.toByteArray();
            return byteData;
        } finally {
            os.close();
        }
    }

    private static Map<String, String> toStringMap(Map<CharSequence, CharSequence> charSeqMap) {
        Map<String, String> stringMap = new HashMap<String, String>();
        for (Map.Entry<CharSequence, CharSequence> entry : charSeqMap.entrySet()) {
            stringMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return stringMap;
    }

}
