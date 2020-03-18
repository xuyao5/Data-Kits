package io.github.xuyao5.dal.flatfile.factory;

import io.github.xuyao5.dal.flatfile.configuration.Configuration;
import io.github.xuyao5.dal.flatfile.reader.FlatFileReader;
import io.github.xuyao5.dal.flatfile.writer.FlatFileWriter;

import javax.xml.bind.JAXB;
import java.nio.file.Paths;

public final class FlatFileAccessFactory {

    public static FlatFileReader getFlatFileReader() {
        return null;
    }

    public static FlatFileWriter getFlatFileWriter() {
        return null;
    }

    public void test() {
        Configuration unmarshal = JAXB.unmarshal(Paths.get("/Users/xuyao/Downloads/FlatFileCollector.xml").toFile(), Configuration.class);
        System.out.println(unmarshal);
    }
}
