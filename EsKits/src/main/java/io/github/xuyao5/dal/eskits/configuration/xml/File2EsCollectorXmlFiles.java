package io.github.xuyao5.dal.eskits.configuration.xml;

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
public final class File2EsCollectorXmlFiles {

    private List<File2EsCollectorXmlFile> file;

    public Optional<File2EsCollectorXmlFile> seek(@NotNull String id) {
        return file.parallelStream()
                .filter(file2EsCollectorXmlFile -> file2EsCollectorXmlFile.getId().equalsIgnoreCase(id) && file2EsCollectorXmlFile.isEnable())
                .findFirst();
    }
}