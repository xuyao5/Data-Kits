package io.github.xuyao5.dal.generator.service.initial.impl;

import io.github.xuyao5.dal.generator.consts.DriverClassConst;
import io.github.xuyao5.dal.generator.service.AbstractService;
import io.github.xuyao5.dal.generator.service.initial.MyBatisInitializeService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Log4j2
@Service("myBatisInitializeServiceImpl")
public class MyBatisInitializeServiceImpl extends AbstractService implements MyBatisInitializeService {

    @Override
    public String generateFilePath(@NotNull String rootPath, @NotNull String appName) {
        return rootPath + appName + "/";
    }

    @Override
    public void createSourceFile(@NotNull String templateFile) {
        List<String> warningList = new CopyOnWriteArrayList<>();

        try {
//            File configFile = ResourceUtils.getFile("classpath:generatorConfig.xml");
            File configFile = Paths.get(templateFile).toFile();
            ConfigurationParser cp = new ConfigurationParser(warningList);
            Configuration config = cp.parseConfiguration(configFile);
            config.validate();
            DefaultShellCallback callback = new DefaultShellCallback(true);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warningList);
            myBatisGenerator.generate(null);
        } catch (IOException | SQLException | InterruptedException | InvalidConfigurationException | XMLParserException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    @SneakyThrows
    @Override
    public String createTemplateFile(@NotNull String filePath, @NotNull List<String> tableList) {
        final SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(ResourceUtils.getFile("classpath:generatorConfig.xml"));

        /*
         * 增加 Table Node
         */
        final Node tableNode = document.selectSingleNode("//table");
        final Element parent = tableNode.getParent();

        tableList.forEach(s -> {
            final Element myElement = (Element) tableNode.clone();
            myElement.addAttribute("tableName", s);

            parent.addText(System.lineSeparator() + StringUtils.repeat(StringUtils.SPACE, 8));
            parent.add(myElement);
        });
        parent.addText(System.lineSeparator() + StringUtils.repeat(StringUtils.SPACE, 4));

        parent.remove(tableNode);

        /*
         * 处理jdbcConnection变量替换
         */
        final Element jdbcConnectionElement = (Element) document.selectSingleNode("//jdbcConnection");
        jdbcConnectionElement.addAttribute("driverClass", DriverClassConst.MYSQL_DRIVER.getDriverClass());
        jdbcConnectionElement.addAttribute("connectionURL", "jdbc:mysql://127.0.0.1:32769/test?useUnicode=true&characterEncoding=UTF-8");
        jdbcConnectionElement.addAttribute("userId", "root");
        jdbcConnectionElement.addAttribute("password", "123456");

        /*
         * 处理javaModelGenerator变量替换
         */
        final Element javaModelGeneratorElement = (Element) document.selectSingleNode("//javaModelGenerator");
        javaModelGeneratorElement.addAttribute("targetPackage", "io.github.xuyao5.dal.generator.repository.paces.model");
        javaModelGeneratorElement.addAttribute("targetProject", filePath);

        /*
         * 处理javaClientGenerator变量替换
         */
        final Element javaClientGeneratorElement = (Element) document.selectSingleNode("//javaClientGenerator");
        javaClientGeneratorElement.addAttribute("targetPackage", "io.github.xuyao5.dal.generator.repository.paces.dao");
        javaClientGeneratorElement.addAttribute("targetProject", filePath);

        if (!Files.exists(Paths.get(filePath))) {
            Files.createDirectories(Paths.get(filePath));
        }

        final String templateFile = filePath + "generatorConfig.xml";
        FileWriter out = new FileWriter(templateFile);
        document.write(out);
        out.close();

        return templateFile;
    }
}
