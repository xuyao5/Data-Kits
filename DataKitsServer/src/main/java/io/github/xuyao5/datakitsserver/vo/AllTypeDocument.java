package io.github.xuyao5.datakitsserver.vo;

import io.github.xuyao5.dkl.eskits.schema.base.BaseDocument;
import io.github.xuyao5.dkl.eskits.schema.standard.StandardFileLine;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.lucene.document.BinaryRange;
import org.apache.lucene.document.DoubleRange;
import org.apache.lucene.document.InetAddressRange;
import org.elasticsearch.common.geo.GeoPoint;

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

    private BinaryRange dateRange;
    private DoubleRange doubleRange;
    private InetAddressRange ipRange;
    private StringBuilder stringBuilder;
    private StringBuffer stringBuffer;

    private GeoPoint geoPoint;

    private StandardFileLine tags;
}
