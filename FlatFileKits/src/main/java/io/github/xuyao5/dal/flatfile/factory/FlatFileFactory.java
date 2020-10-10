package io.github.xuyao5.dal.flatfile.factory;

import io.github.xuyao5.dal.flatfile.FlatFileReader;
import io.github.xuyao5.dal.flatfile.FlatFileWriter;
import io.github.xuyao5.dal.flatfile.configuration.FlatFileKitsConfiguration;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXB;
import java.io.File;
import java.io.FileNotFoundException;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

@Component
public final class FlatFileFactory<T, R> {

    private File configFile;

    @Getter
    private FlatFileKitsConfiguration flatFileKitsConfiguration;

    @PostConstruct
    void initial() throws FileNotFoundException {
        configFile = ResourceUtils.getFile(CLASSPATH_URL_PREFIX + "FlatFileCollector.xml");
        flatFileKitsConfiguration = JAXB.unmarshal(configFile, FlatFileKitsConfiguration.class);
    }

    public FlatFileReader<T, R> getFlatFileReader() {
        return null;
    }

    public FlatFileWriter<T> getFlatFileWriter() {
        return null;
    }
}
