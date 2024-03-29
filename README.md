# ES-Kits SDK Manual

## 1. Quick Start

### 1.1 Maven Config

```XML

<dependency>
    <groupId>io.github.xuyao5</groupId>
    <artifactId>es-kits-lib</artifactId>
    <version>0.13</version>
</dependency>

```

### 1.2 Spring Config

```Java

@SpringBootApplication()
@Import({})//注入@Configuration
@EnableConfigurationProperties({EsClientConfig.class})//注入@ConfigurationProperties
public class DataKitsConfiguration {

    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient(@Value("${es.client.hosts}") String hosts, @Value("${es.client.username}") String username, @Value("${es.client.password}") String password) {
        return new EsClient(new String[]{hosts}, username, password).getClient();
    }
}

```

## 8. API

### 8.1 File to ES

### 8.2 Batch Insert/Update

### 8.3 Stored Search

## 21. Background and Goal

随着平安银行信用卡中心业务不断的发展，数据类需求在应用系统中独立落地的诉求愈发显现。应用研发团队强烈希望把Elasticsearch引入到日常开发的技术栈中去，使业务应用中的大量数据具有搜索、分析和聚合的能力。 应用系统在构建这种能力的过程中，通常会形成数据采集、处理、存储、分发等工作流程，这些流程中又往往会产生大量重复性的开发任务，而这些开发任务又必须严格遵循一定的规范才能保证数据的质量。

信用卡中心在历史发展的过程中搭建过一套通用的数据综合搜索平台（**D**ata **I**ntegration **S**earch **P**latform，DISP），但随着数据应用的深入，DISP暴露出了诸多问题（例如：边界模糊/缺乏准入准出标准/职责归属不清等），于是引出了如何更好的实现数据需求在信用卡中心落地这一全新的课题，这个课题的实施路径也逐步演变成了对DISP的改造和拆分。

"数据准出，应用准入"的八字方针是信用卡在实践数据应用改造中遵循的基本原则，同时在改造的过程中领导也提出针对目前信用卡离线&实时数据服务场景，应采用公共数据/领域数据独立的架构策略，即在领域数据的场景中由应用开发组/模块自行承接，以离线&实时平台为数据源实施准入，由信用卡标准化SDK进行相关的查询和聚合操作。

在上述的背景下，CC ES-KitsSDK依托于DISP改造项目的立项实现推动，并且在信用卡生息研发组率先落地上线。CC ES-Kits SDK的立项目标也逐步清晰化，目标即：

- 为卡中心每个应用开发组提供一套模式标准化的ES SDK;
- 将ES应用开发规范和应用最佳实践整合到SDK中去，统一数据准入准出；
- 实现业务场景化API接入，贴合目前大数据File/MySQL等应用场景；
- 全面管理ES数据的生命周期，智能化数据管理，明确数据应用边界和责任分工；
- 降低ES应用的开发难度，避免重复制造轮子，减少生产问题和故障；
- 简单&高性能；

## 22. SDK Summary

- 支持File2ES：以多种模式将大数据Z+产出的文件导入到ES中去，支持无锁式高性能异步自定义计算，支持编码、分隔符、主键行、注释行等大量配置化；
- 支持MySQL2ES：以Binlog监听模式将MySQL中的数据全量/增量同步到ES中去，支持无锁式高性能异步自定义计算，支持多表智能denormalization（通过智能反范式将数据扁平化处理），从而在访问时避免进行关联操作；
- 支持大批量异步更新：ES常规的同步更新机制非常容易发生问题且性能不佳，SDK开发了数据异步更新的最佳实践方案，调用一个API即可实现高性能数据更新；
- 服务端存储查询：通过在服务器中存储查询的DSL/SQL，客户端只需要简单使用Search ID + Params Map即可进行查询，并支持多租户跨领域复用查询；
- 智能Mapping：框架开发了@AutoMappingField等注解，实现了普通JavaBeans和ES字段的自动类型映射，智能识别数据类型，避免了手工定义Mapping导致的问题；
- 高性能ES Client：在ES High Level Client的基础上进行了全新的封装，支持client全周期的管理和高并发访问的支持；
- 断点续传：对处理过程中的异常不会直接中断处理过程，框架会将问题数据原格式持久化到磁盘，供开发运维人员进一步处理；
- 预定义字段：框架中会在写入索引时自动加入一系列预定义字段，开发人员可开箱即用；
- ES API功能增强：框架中不仅针对Cluster(集群管理)、Index(索引管理)、Document(文档管理)、Search(数据搜索)等API进行了优化封装，并且对Bulk(批量写入)、UpdateByQuery(批量更新)、Reindex(重建索引)、Scroll(快照游标)四个类型的批量操作进行了强化封装；
- 别名优先：以别名优先的模式为应用开发提供了多索引别名原子化切换的功能包；
- 一键动态设置：针对ES中许多参数支持动态调整的特性，提供了一系列支持动态参数设置的方法；
- 内嵌最佳实践：将官方推荐的一系列读写优化模式整合封装到了SDK中去；
- 工具包：提供了针对数据应用场景的大量工具包/类；

## 23. Scene

- 平安银行100%自主知识产权及合规性需求的场景；
- 以ES为目标的数据导入和采集的场景；
- 以ES为目标的数据查询和聚合的场景；
- 对ES的读写性能有需求的场景；
- 对ES的快速开发有需求的场景；
- 对导入ES的数据有二次计算/标签/下钻需求的场景；
- 需要将数据功能以jar包形式内嵌到应用中去的场景；
- 有海量动态/静态业务数据、日志数据、指标数据、监控数据、性能数据、地理数据、安全数据、BI数据、时间序列数据有分布式、低成本存储需求的场景；

## 24. Creative

- 通过无锁设计实现了在高并发情形下的高性能；
- 解决了计算过程伪共享问题；
- 内置多种最佳实践模式；
- 以内嵌方式处理Binlog；
- 规范在代码层面而非PPT层面落地；

## 25. Analysis

- 明确了以领域为边界的数据应用模式；
- 简化ES接入的时效，降低落地成本；
- 降低ES的使用门槛，规避生产事故和问题；
- 填补目前信用卡数据领域应用实践模式的空白；
- 统一标准化ES的访问管理，便于进一步扩展应用模式，如：双活/灰度；

## 26. 风险点和监管要求

- 创新模式的开发存在较高的技术难度；
- 对接入的ES有一定版本要求；