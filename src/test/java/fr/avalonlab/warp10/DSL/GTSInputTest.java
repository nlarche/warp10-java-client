package fr.avalonlab.warp10.DSL;

import fr.avalonlab.warp10.exception.MissingMandatoryDataException;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GTSInputTest {

    @Test
    void withoutLatLonElev() {
        GTSInput newGTSInput = GTSInput.builder()
                .TS(1380475081000000L)
                .NAME("foo")
                .LABEL("label1", "val1")
                .VALUE("Toto");

        String result = newGTSInput.toInputFormat();

        assertThat(result).isEqualTo("1380475081000000// foo{label1=val1} 'Toto'");
    }

    @Test
    void withoutTsAndElev() {
        Map<String, String> mapOfLabels = new HashMap<>();
        mapOfLabels.put("label2", "val2");

        GTSInput newGTSInput = GTSInput.builder()
                .LAT(48.0)
                .LON(-4.5)
                .NAME("bar")
                .LABELS(mapOfLabels)
                .VALUE(-3.14);

        String result = newGTSInput.toInputFormat();

        assertThat(result).isEqualTo("/48.0:-4.5/ bar{label2=val2} -3.14");
    }

    @Test
    void fullDataGTS() {
        GTSInput newGTSInput = GTSInput.builder()
                .TS(1380475081123456L)
                .LAT(45.0)
                .LON(-0.01)
                .ELEV(10000000L)
                .NAME("foobar")
                .LABEL("label0", "val0")
                .LABEL("label1", "val1")
                .VALUE(Boolean.TRUE);

        String result = newGTSInput.toInputFormat();

        assertThat(result).isEqualTo("1380475081123456/45.0:-0.01/10000000 foobar{label0=val0,label1=val1} T");
    }

    @Test
    void withZoneDateTimeTS() {
        GTSInput newGTSInput = GTSInput.builder()
                .TS(ZonedDateTime.parse("2018-05-26T00:00:00+02:00"))
                .LAT(45.0)
                .LON(-0.01)
                .ELEV(10000000L)
                .NAME("foobar")
                .LABEL("label0", "val0")
                .LABEL("label1", "val1")
                .VALUE("Toto");

        String result = newGTSInput.toInputFormat();

        assertThat(result).isEqualTo("1527285600000000/45.0:-0.01/10000000 foobar{label0=val0,label1=val1} 'Toto'");
    }

    @Test
    void labelsAreMandatory() {
        Throwable exception = assertThrows(MissingMandatoryDataException.class, () -> GTSInput.builder().NAME("toto").toInputFormat());

        assertThat(exception).isInstanceOf(MissingMandatoryDataException.class);
        assertThat(exception.getMessage()).isEqualTo("The data 'LABELS' was not set.");
    }

    @Test
    void nameIsMandatory() {
        Throwable exception = assertThrows(MissingMandatoryDataException.class, () -> GTSInput.builder().toInputFormat());

        assertThat(exception).isInstanceOf(MissingMandatoryDataException.class);
        assertThat(exception.getMessage()).isEqualTo("The data 'NAME' was not set.");
    }

    @Test
    void valueIsMandatory() {
        Throwable exception = assertThrows(MissingMandatoryDataException.class, () -> GTSInput.builder().NAME("toto").LABEL("plip", "plop").toInputFormat());

        assertThat(exception).isInstanceOf(MissingMandatoryDataException.class);
        assertThat(exception.getMessage()).isEqualTo("The data 'VALUE' was not set.");
    }
}
