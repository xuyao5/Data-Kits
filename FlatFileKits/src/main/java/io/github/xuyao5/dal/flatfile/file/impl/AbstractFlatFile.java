package io.github.xuyao5.dal.flatfile.file.impl;

import io.github.xuyao5.dal.flatfile.configuration.FlatFileKitsConfiguration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractFlatFile {

    final protected FlatFileKitsConfiguration flatFileKitsConfiguration;

    protected List<String> fileList = new CopyOnWriteArrayList<>();

    protected Map<String, Integer> fileLinesCount = new ConcurrentHashMap<>();

    protected List<String> headLines;

    protected List<String> tailLines;

    private List<Integer> skippedLines;

    protected AbstractFlatFile(FlatFileKitsConfiguration flatFileKitsConfiguration) {
        this.flatFileKitsConfiguration = flatFileKitsConfiguration;
    }
}
