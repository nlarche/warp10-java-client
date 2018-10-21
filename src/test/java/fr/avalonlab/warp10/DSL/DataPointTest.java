package fr.avalonlab.warp10.DSL;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;


class DataPointTest {

    @Test
    public void emptyDataPoint() {
        DataPoint dp = new DataPoint(null, null, null, null, null);

        assertThat(dp.isEmpty()).isTrue();
        assertThat(dp).isEqualTo(DataPoint.empty());
    }

    @Test
    public void timedDataPoint() {
        DataPoint dp = DataPoint.of("toto", 12345670000L);

        assertThat(dp).isEqualTo(new DataPoint(12345670000L, null, null, null, "toto"));
        assertThat(dp.getMsTimestamp()).isEqualTo(12345670000L);
        assertThat(dp.getValue()).isEqualTo("toto");
    }

    @Test
    public void geoLocDataPoint() {
        DataPoint dp = DataPoint.of("toto", 12345670000L)
                .atLongitude(877633883000L)
                .atLatitude(9874333000L);

        assertThat(dp).isEqualTo(new DataPoint(12345670000L, 9874333000L, 877633883000L, null, "toto"));
        assertThat(dp.getLongitude()).isEqualTo(877633883000L);
        assertThat(dp.getLatitude()).isEqualTo(9874333000L);
    }

    @Test
    public void geoLocWithElevationDataPoint() {
        DataPoint dp = DataPoint.of("toto", 12345670000L)
                .atLongitude(877633883000L)
                .atLatitude(9874333000L)
                .atElevation(-94333344000L);

        assertThat(dp).isEqualTo(new DataPoint(12345670000L, 9874333000L, 877633883000L, -94333344000L, "toto"));
        assertThat(dp.getElevation()).isEqualTo(-94333344000L);
    }

}