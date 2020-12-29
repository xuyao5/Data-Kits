package io.github.xuyao5.dkl.eskits.configuration.xml;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 11/10/20 00:48
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Data(staticConstructor = "of")
public final class File2EsTasks {

    private List<File2EsTask> task;

    public Optional<File2EsTask> seek(@NotNull String id) {
        return task.parallelStream()
                .filter(file2EsTask -> file2EsTask.getId().equalsIgnoreCase(id))
                .filter(file2EsTask -> file2EsTask.isEnable())
                .findAny();
    }
}