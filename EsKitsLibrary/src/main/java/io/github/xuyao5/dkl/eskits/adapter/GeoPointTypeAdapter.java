package io.github.xuyao5.dkl.eskits.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.elasticsearch.common.geo.GeoPoint;

import java.io.IOException;

/**
 * @author Thomas.XU(xuyao)
 * @version 6/11/21 19:40
 */
public class GeoPointTypeAdapter extends TypeAdapter<GeoPoint> {

    @Override
    public void write(JsonWriter jsonWriter, GeoPoint geoPoint) throws IOException {
        jsonWriter.value(geoPoint.toString());
    }

    @Override
    public GeoPoint read(JsonReader jsonReader) throws IOException {
        return new GeoPoint(jsonReader.nextString());
    }
}
