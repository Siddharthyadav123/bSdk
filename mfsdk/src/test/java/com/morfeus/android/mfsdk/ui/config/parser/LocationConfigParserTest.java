package com.morfeus.android.mfsdk.ui.config.parser;

import com.morfeus.android.mfsdk.ui.widget.editor.model.LocationScreenModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LocationConfigParserTest {
    LocationConfigParser mLocationConfigParser;

    @Before
    public void setUp() throws Exception {
        mLocationConfigParser = LocationConfigParser.getInstance();
    }

    @Test
    public void testParse_returnLocationScreenModell() throws Exception {
        LocationScreenModel locationScreenModel = mLocationConfigParser
                .parse(ConfigParserHelper.TEST_LOCATION_SCREEN_JSON);
        assertNotNull("Error: Failed to parse LocationScreenModel" +
                        " json string! Parser returning null.",
                locationScreenModel);
        assertEquals("Error: Invalid id in LocationScreenModel",
                "LocationScreen", locationScreenModel.getId());
    }

    @Test
    public void testLocationHeader() throws Exception {
        LocationScreenModel locationScreenModel = mLocationConfigParser
                .parse(ConfigParserHelper.TEST_LOCATION_SCREEN_JSON);
        assertNotNull("Error: Failed to parse LocationScreenModel" +
                        " json string! Parser returning null.",
                locationScreenModel);
        assertEquals("Error: Invalid id in LocationScreenModel",
                "LocationScreen", locationScreenModel.getId());

        assertNotNull("Error: Failed to parse Header",
                locationScreenModel.getHeader());

        assertNotNull("Error: Failed to parse Header style",
                locationScreenModel.getHeader().getStyle());

        assertNotNull("Error: Failed to parse Header left button",
                locationScreenModel.getHeader().getImageModel());

        assertNotNull("Error: Failed to parse Header text",
                locationScreenModel.getHeader().getTextModel());
    }

    @Test
    public void testLocationBody() throws Exception {
        LocationScreenModel locationScreenModel = mLocationConfigParser
                .parse(ConfigParserHelper.TEST_LOCATION_SCREEN_JSON);
        assertNotNull("Error: Failed to parse LocationScreenModel json" +
                        " string! Parser returning null.",
                locationScreenModel);
        assertEquals("Error: Invalid id in LocationScreenModel",
                "LocationScreen", locationScreenModel.getId());

        assertNotNull("Error: Failed to parse Body",
                locationScreenModel.getBody());

        assertNotNull("Error: Failed to parse Body style",
                locationScreenModel.getBody().getStyle());

        assertNotNull("Error: Failed to parse Body Map",
                locationScreenModel.getBody().getMap());

        assertNotNull("Error: Body Map should have type",
                locationScreenModel.getBody().getMap().getType());

        assertNotNull("Error: Failed to parse Body Map style",
                locationScreenModel.getBody().getMap().getStyle());
    }

    @Test
    public void testLocationFooter() throws Exception {
        LocationScreenModel locationScreenModel = mLocationConfigParser
                .parse(ConfigParserHelper.TEST_LOCATION_SCREEN_JSON);
        assertNotNull("Error: Failed to parse LocationScreenModel " +
                        "json string! Parser returning null.",
                locationScreenModel);
        assertEquals("Error: Invalid id in LocationScreenModel",
                "LocationScreen", locationScreenModel.getId());

        assertNotNull("Error: Failed to parse Footer",
                locationScreenModel.getFooter());

        assertNotNull("Error: Failed to parse Footer style",
                locationScreenModel.getFooter().getStyle());

        assertNotNull("Error: Failed to parse Footer strip",
                locationScreenModel.getFooter().getFotterStripMap());

        assertEquals("Error: There should be 2 types of strip ",
                2, locationScreenModel.getFooter().getFotterStripMap().size());

        assertEquals("Error: Unable to parse Show location strip",
                true, locationScreenModel.getFooter().getFotterStripMap().
                        containsKey("showlocation"));

        assertEquals("Error: Unable to parse Send location strip",
                true, locationScreenModel.getFooter().getFotterStripMap().
                        containsKey("sendlocation"));

    }
}
