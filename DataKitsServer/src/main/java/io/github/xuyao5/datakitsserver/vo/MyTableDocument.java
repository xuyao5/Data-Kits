package io.github.xuyao5.datakitsserver.vo;

import io.github.xuyao5.dkl.eskits.schema.base.BaseDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Data(staticConstructor = "of")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class MyTableDocument extends BaseDocument {

    private int id1;

    private int id2;

    private BigDecimal amount;

    private String desc;

    private long dateTime1;

    private int discount1;

    private int discount2;

    private String momo;
}
/*
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `MyTable`;
CREATE TABLE `MyTable` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `amount` decimal(5,2) DEFAULT NULL,
  `desc` varchar(500) DEFAULT NULL,
  `dateTime1` datetime DEFAULT NOW(),
  `discount1` int(3) unsigned DEFAULT 100,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;
 */