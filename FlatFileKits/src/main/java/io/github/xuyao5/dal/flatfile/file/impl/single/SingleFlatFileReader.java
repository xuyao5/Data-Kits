package io.github.xuyao5.dal.flatfile.file.impl.single;

import io.github.xuyao5.dal.flatfile.configuration.FlatFileKitsConfiguration;
import io.github.xuyao5.dal.flatfile.file.FileReader;
import io.github.xuyao5.dal.flatfile.file.impl.AbstractFlatFile;

import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public final class SingleFlatFileReader extends AbstractFlatFile implements FileReader {

    public SingleFlatFileReader(FlatFileKitsConfiguration flatFileKitsConfiguration) {
        super(flatFileKitsConfiguration);
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
    public int getFileCount(@NotNull String fileName) throws IOException {
        try (InputStream file = new BufferedInputStream(new FileInputStream(fileName))) {
            byte[] c = new byte[1024];

            int readChars = file.read(c);
            if (readChars == -1) {
                // bail out if nothing to read
                return 0;
            }

            // make it easy for the optimizer to tune this loop
            int count = 0;
            while (readChars == 1024) {
                for (int i = 0; i < 1024; ) {
                    if (c[i++] == '\n') {
                        ++count;
                    }
                }
                readChars = file.read(c);
            }

            // count remaining characters
            while (readChars != -1) {
//                System.out.println(readChars);
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
                readChars = file.read(c);
            }

            return count == 0 ? 1 : count;
        }
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
