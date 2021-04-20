package io.github.xuyao5.datakitsserver.vo;

import io.github.xuyao5.dkl.eskits.schema.BaseDocument;
import io.github.xuyao5.dkl.eskits.schema.StandardFileLine;
import io.github.xuyao5.dkl.eskits.schema.range.DateRange;
import io.github.xuyao5.dkl.eskits.schema.range.DoubleRange;
import io.github.xuyao5.dkl.eskits.schema.range.IpRange;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data(staticConstructor = "of")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class AllTypeDocument extends BaseDocument {

    private String str;
    private String[] strArray;
    private List<String> strList;
    private char chr;
    private Character character;
    private char[] chrArray;
    private Character[] characterArray;
    private List<Character> characterList;
    private long l;
    private Long lo;
    private long[] lArray;
    private Long[] loArray;
    private List<Long> longList;

    private int i;
    private Integer in;
    private int[] iArray;
    private Integer[] inArray;
    private List<Integer> integerList;

    private short s;
    private Short sh;
    private short[] sArray;
    private Short[] shArray;
    private List<Short> shortList;

    private byte b;
    private Byte by;
    private byte[] bArray;
    private Byte[] byArray;
    private List<Byte> byteList;

    private double d;
    private Double dou;
    private double[] dArray;
    private Double[] douArray;
    private List<Double> doubleList;

    private DateRange dateRange;
    private DoubleRange doubleRange;
    private IpRange ipRange;
    private StringBuilder stringBuilder;
    private StringBuffer stringBuffer;

    private StandardFileLine tags;
}
