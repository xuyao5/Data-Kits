package io.github.xuyao5.dal.flatfile.mapper;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.List;

public interface LineMapper {

    List<String> read(@NotNull File file);
}
