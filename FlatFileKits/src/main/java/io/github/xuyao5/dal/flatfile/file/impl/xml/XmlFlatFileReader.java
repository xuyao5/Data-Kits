package io.github.xuyao5.dal.flatfile.file.impl.xml;

import io.github.xuyao5.dal.flatfile.configuration.FlatFileKitsConfig;
import io.github.xuyao5.dal.flatfile.file.FileReader;
import io.github.xuyao5.dal.flatfile.file.impl.AbstractFlatFile;

import javax.validation.constraints.NotNull;
import java.util.List;

public final class XmlFlatFileReader extends AbstractFlatFile implements FileReader {

    public XmlFlatFileReader(FlatFileKitsConfig flatFileKitsConfig) {
        super(flatFileKitsConfig);
    }

    @Override
    public void initial() {

    }

    @Override
    public int countLines() {
        return 0;
    }

    @Override
    public List<String> getFileList() {
        return null;
    }

    @Override
    public int getFileCount(@NotNull String fileName) {
        return 0;
    }

    @Override
    public List<String> readHeadLines(@NotNull String fileName) {
        return null;
    }

    @Override
    public List<String> readTailLines(@NotNull String fileName) {
        return null;
    }

    @Override
    public List<String> readSkippedLines(@NotNull String fileName) {
        return null;
    }

    @Override
    public List<String> readAll(@NotNull String fileName) {
        return null;
    }

    @Override
    public List<String> read(@NotNull String fileName) {
        return null;
    }
}
