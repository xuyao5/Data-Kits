package io.github.xuyao5.dal.eskitsserver.demo;

import lombok.Data;

@Data(staticConstructor = "of")
public final class FileMetadata {

    private String name;
    private int seq;
    private String note;
    private long amount;
}
