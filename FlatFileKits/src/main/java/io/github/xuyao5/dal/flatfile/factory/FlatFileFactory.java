package io.github.xuyao5.dal.flatfile.factory;

import io.github.xuyao5.dal.core.factory.AbstractFactory;
import io.github.xuyao5.dal.flatfile.configuration.FlatFileKitsConfig;
import io.github.xuyao5.dal.flatfile.reader.FlatFileReader;
import io.github.xuyao5.dal.flatfile.writer.FlatFileWriter;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXB;
import java.io.File;
import java.io.FileNotFoundException;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

@Component
public final class FlatFileFactory<T, R> extends AbstractFactory {

    private File configFile;

    @Getter
    private FlatFileKitsConfig flatFileKitsConfig;

    @PostConstruct
    void initial() throws FileNotFoundException {
        configFile = ResourceUtils.getFile(CLASSPATH_URL_PREFIX + "FlatFileCollector.xml");
        flatFileKitsConfig = JAXB.unmarshal(configFile, FlatFileKitsConfig.class);
    }

    public FlatFileReader<T, R> getFlatFileReader() {
        return null;
    }

    public FlatFileWriter<T> getFlatFileWriter() {
        return null;
    }
}
