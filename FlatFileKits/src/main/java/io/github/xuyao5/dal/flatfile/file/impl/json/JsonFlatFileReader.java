package io.github.xuyao5.dal.flatfile.file.impl.json;

import io.github.xuyao5.dal.flatfile.configuration.FileCollectorConfig;
import io.github.xuyao5.dal.flatfile.file.FileReader;
import io.github.xuyao5.dal.flatfile.file.impl.AbstractFlatFile;

import javax.validation.constraints.NotNull;
import java.util.List;

public final class JsonFlatFileReader extends AbstractFlatFile implements FileReader {

    public JsonFlatFileReader(FileCollectorConfig fileCollectorConfig) {
        super(fileCollectorConfig);
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
