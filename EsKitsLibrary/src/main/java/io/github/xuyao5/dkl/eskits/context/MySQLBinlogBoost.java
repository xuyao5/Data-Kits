package io.github.xuyao5.dkl.eskits.context;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import com.github.shyiko.mysql.binlog.jmx.BinaryLogClientMXBean;
import io.github.xuyao5.dkl.eskits.repository.Columns;
import io.github.xuyao5.dkl.eskits.repository.ColumnsMapper;
import lombok.Builder;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static io.github.xuyao5.dkl.eskits.repository.ColumnsDynamicSqlSupport.tableName;
import static io.github.xuyao5.dkl.eskits.repository.ColumnsDynamicSqlSupport.tableSchema;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isIn;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 7/07/21 22:44
 * @apiNote MySQLBinlogBoost
 * @implNote MySQLBinlogBoost
 */
@Slf4j
@Builder(builderMethodName = "context", buildMethodName = "create")
public final class MySQLBinlogBoost {

    @Builder.Default
    private String driver = "com.mysql.cj.jdbc.Driver";

    @Builder.Default
    private String hostname = "localhost";

    @Builder.Default
    private int port = 3306;

    private String username;
    private String password;

    @SneakyThrows
    private List<Columns> queryColumns(@NonNull String schema, @NonNull String... table) {
        Properties properties = new Properties();
        switch (driver) {
            case "com.mysql.cj.jdbc.Driver":
                properties.setProperty("mybatis.mysql.url", StringUtils.join("jdbc:mysql://", hostname, ":", port, "/information_schema?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC"));
                break;
            case "com.mysql.jdbc.Driver":
                properties.setProperty("mybatis.mysql.url", StringUtils.join("jdbc:mysql://", hostname, ":", port, "/information_schema?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC"));
                break;
        }
        properties.setProperty("mybatis.mysql.driver", driver);
        properties.setProperty("mybatis.mysql.username", username);
        properties.setProperty("mybatis.mysql.password", password);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"), properties);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.getMapper(ColumnsMapper.class).select(dsl -> dsl.where(tableSchema, isEqualTo(schema)).and(tableName, isIn(table)));
        }
    }

    @SneakyThrows
    public BinaryLogClientMXBean open(@NonNull String schema, @NonNull String... table) {
        List<Columns> columns = queryColumns(schema, table);
        columns.stream().findFirst();
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG
//                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );
        final BinaryLogClient client = new BinaryLogClient(hostname, port, schema, username, password);
        client.setEventDeserializer(eventDeserializer);
        client.registerEventListener(event -> {
            final EventType eventType = event.getHeader().getEventType();
            if (EventType.isRowMutation(eventType)) {
                if (EventType.isWrite(eventType)) {
                    WriteRowsEventData data = event.getData();
                    List<Serializable[]> rows = data.getRows();
                    rows.forEach(objs -> {
                        for (Serializable obj : objs) {
                            System.out.println(obj);
                        }
                    });
                }

                if (EventType.isDelete(eventType)) {
                    DeleteRowsEventData data = event.getData();
                    List<Serializable[]> rows = data.getRows();
                    rows.forEach(System.out::println);
                }

                if (EventType.isUpdate(eventType)) {
                    UpdateRowsEventData data = event.getData();
                    List<Entry<Serializable[], Serializable[]>> rows = data.getRows();
                    rows.forEach(System.out::println);
                }
            } else {
                switch (eventType) {
                    case ROTATE:
                        Consumer<RotateEventData> rotateEventConsumer = this::rotateEventHandler;
                        rotateEventConsumer.accept(event.getData());
                        break;
                    case FORMAT_DESCRIPTION:
                        Consumer<FormatDescriptionEventData> formatDescriptionEventConsumer = formatDescriptionEventData -> log.info("【FORMAT_DESCRIPTION】binlogVersion=[{}], serverVersion={}, headerLength={}, dataLength={}, checksumType={}", formatDescriptionEventData.getBinlogVersion(), formatDescriptionEventData.getServerVersion(), formatDescriptionEventData.getHeaderLength(), formatDescriptionEventData.getDataLength(), formatDescriptionEventData.getChecksumType());
                        formatDescriptionEventConsumer.accept(event.getData());
                        break;
                    case QUERY:
                        Consumer<QueryEventData> queryEventConsumer = queryEventData -> log.info("【QUERY】threadId={},executionTime={}, errorCode={}, database={}, sql={}", queryEventData.getThreadId(), queryEventData.getExecutionTime(), queryEventData.getErrorCode(), queryEventData.getDatabase(), queryEventData.getSql());
                        queryEventConsumer.accept(event.getData());
                        break;
                    case TABLE_MAP:
                        Consumer<TableMapEventData> tableMapEventConsumer = tableMapEventData -> log.info("【TABLE_MAP】tableId={}, database={}, table={}, columnTypes={}, columnMetadata={}, columnNullability={}, eventMetadata={}", tableMapEventData.getTableId(), tableMapEventData.getDatabase(), tableMapEventData.getTable(), tableMapEventData.getColumnTypes(), tableMapEventData.getColumnMetadata(), tableMapEventData.getColumnNullability(), tableMapEventData.getEventMetadata());
                        tableMapEventConsumer.accept(event.getData());
                        break;
                    case XID:
                        Consumer<XidEventData> xidEventConsumer = xidEventData -> log.info("【XID】xid={}", xidEventData.getXid());
                        xidEventConsumer.accept(event.getData());
                        break;
                    case ANONYMOUS_GTID:
                        log.info("【ANONYMOUS_GTID】ANONYMOUS_GTID={}", event);
                        break;
                    default:
                        log.warn("当前版本暂不支持本事件={}", event);
                        break;
                }
            }
        });
        client.connect(TimeUnit.SECONDS.toMillis(6));
        return client;
    }

    @SneakyThrows
    public void close(@NonNull BinaryLogClientMXBean bean) {
        if (bean.isConnected()) {
            bean.disconnect();
        }
    }

    private void rotateEventHandler(RotateEventData rotateEventData) {
        log.info("【ROTATE】binlogFilename={}, binlogPosition={}", rotateEventData.getBinlogFilename(), rotateEventData.getBinlogPosition());
    }
}