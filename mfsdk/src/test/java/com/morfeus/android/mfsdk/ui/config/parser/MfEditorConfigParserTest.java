package com.morfeus.android.mfsdk.ui.config.parser;

import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.MFEditorViewModel;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MfEditorConfigParserTest {
    private MfEditorConfigParser mConfigParser;

    private static List<String> TEST_SUBVIEW_IDS = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        mConfigParser = MfEditorConfigParser.getInstance();
        TEST_SUBVIEW_IDS.add("inputview");
        TEST_SUBVIEW_IDS.add("toolbarview");
        TEST_SUBVIEW_IDS.add("shortcutview");
        TEST_SUBVIEW_IDS.add("subview");

    }

    @Test
    public void parse() throws Exception {
        BaseModel model = mConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);

        assertNotNull(model);
        assertTrue(model instanceof MFEditorViewModel);

        List<BaseModel> subModel = ((MFEditorViewModel) model).getSubComponentModels();
        assertEquals(4, subModel.size());

        Set<String> ids = new HashSet<>();
        for (BaseModel bm : subModel) {
            ids.add(bm.getId());
        }

        for (String id : TEST_SUBVIEW_IDS) {
            assertTrue(ids.contains(id));
        }

    }

}