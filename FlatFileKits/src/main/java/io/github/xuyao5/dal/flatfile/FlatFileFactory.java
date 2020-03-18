package io.github.xuyao5.dal.flatfile;

import io.github.xuyao5.dal.flatfile.reader.FlatFileReader;
import io.github.xuyao5.dal.flatfile.writer.FlatFileWriter;
import org.springframework.stereotype.Component;

@Component
public final class FlatFileFactory<T, R> {

    public FlatFileReader<T, R> getFlatFileReader() {
        return null;
    }

    public FlatFileWriter<T> getFlatFileWriter() {
        return null;
    }
}
