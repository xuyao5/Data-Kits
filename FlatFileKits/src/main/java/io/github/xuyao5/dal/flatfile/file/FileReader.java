package io.github.xuyao5.dal.flatfile.file;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

public interface FileReader {

    void initial();

    int countLines();

    List<String> getFileList();

    int getFileCount(@NotNull String fileName) throws IOException;

    List<String> readHeadLines(@NotNull String fileName);

    List<String> readTailLines(@NotNull String fileName);

    List<String> readSkippedLines(@NotNull String fileName);

    List<String> readAll(@NotNull String fileName);

    List<String> read(@NotNull String fileName);
}
