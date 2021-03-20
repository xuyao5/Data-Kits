package io.github.xuyao5.datakitsserver.vo;

import io.github.xuyao5.dkl.eskits.schema.BaseDocument;
import io.github.xuyao5.dkl.eskits.schema.range.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.atomic.LongAdder;

@Data(staticConstructor = "of")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class AllTypeDocument extends BaseDocument {

    private String a;
    private char b;
    private long c;
    private int d;
    private short e;
    private byte f;
    private double g;
    private float h;
    private boolean i;
    private Date j;
    private BigDecimal k;
    private BigInteger l;
    private LongAdder m;
    private InetAddress n;
    private IntegerRange o;
    private LongRange p;
    private FloatRange q;
    private DoubleRange r;
    private DateRange s;
    private IpRange t;
    private Object z;
}
