package io.github.xuyao5.dal.flatfile.pojo;

import lombok.Data;

@Data(staticConstructor = "of")
public class MyFileFormat {

    private String name;

    private int score;
}
