package io.github.xuyao5.dal.file2es.configuration.xml;

import lombok.Data;

import java.util.List;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 11/10/20 00:48
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Data(staticConstructor = "of")
public final class File2EsConfigXmlFiles {

    private List<File2EsConfigXmlFile> file;
}