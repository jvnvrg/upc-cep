package upc.edu.cep.kafka.producers;

/**
 * Created by osboxes on 04/04/17.
 */

import com.google.common.io.Resources;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class fecProducer {

    public static void main(String[] args) throws Exception {
        KafkaProducer<String, String> producer;
        try (InputStream props = Resources.getResource("producer.props").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            properties.put("groupid","test");
            producer = new KafkaProducer<>(properties);
        }

        String key = "key1";
        String userSchema = "{\"type\":\"record\"," +
                "\"name\":\"myrecord\"," +
                "\"fields\":[{\"name\":\"mylog\",\"type\":\"string\"}]}";
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(userSchema);
        int i = 0, j=0;
        while (true) {

            GenericRecord avroRecord = new GenericData.Record(schema);
            avroRecord.put("mylog", i+"");
            //  byte[] bytes = recordInjection.apply(avroRecord);

            //  ProducerRecord<String, byte[]> record = new ProducerRecord<>("mytopic", bytes);
            ProducerRecord record;
            if(j%2==0) {
                record = new ProducerRecord<String, byte[]>("logtest",0, key, datumToByteArray(schema, avroRecord));
            }
            else
            {
                record = new ProducerRecord<String, byte[]>("logtest",1, key, datumToByteArray(schema, avroRecord));
            }
            try {
                producer.send(record);
            } catch (SerializationException e) {
                // may need to do something with it
                e.printStackTrace();
            }

            Thread.sleep(10);
            i++;
            j++;
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
}
