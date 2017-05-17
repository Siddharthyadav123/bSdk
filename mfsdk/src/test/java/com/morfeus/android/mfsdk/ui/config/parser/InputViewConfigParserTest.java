package com.morfeus.android.mfsdk.ui.config.parser;

import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.EditTextModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.ImageButtonModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.InputViewModel;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class InputViewConfigParserTest {

    InputViewConfigParser mInputViewConfigParser;

    @Before
    public void setUp() throws Exception {
        mInputViewConfigParser = InputViewConfigParser.getInstance();
    }

    @Test
    public void testParse_returnInputViewModel() throws Exception {
        BaseModel baseModel = mInputViewConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse inputView json string! Parser returning null.",
                baseModel);
        assertTrue("Error: Failed to parse inputView json string. Not returning InputViewModel!",
                baseModel instanceof InputViewModel);
        assertEquals("Error: Invalid id in InputViewModel", "inputview", baseModel.getId());
    }

    @Test
    public void testParse_inputViewModelContainsStyle() {
        BaseModel baseModel = mInputViewConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse inputView json string! Parser returning null.",
                baseModel);
        assertNotNull("Error: InputViewModel doesn't containing Style", baseModel.getStyle());
    }

    @Test
    public void testParse_containsEditTextModelAndSendButton() {
        BaseModel baseModel = mInputViewConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse inputView json string! Parser returning null.",
                baseModel);

        for (BaseModel subModel : ((InputViewModel) baseModel).getBaseModels()) {
            if (subModel instanceof EditTextModel) {
                assertEquals("textinput", ((EditTextModel) subModel).getId());
            } else if (subModel instanceof ImageButtonModel) {
                assertEquals("sendButton", ((ImageButtonModel) subModel).getId());
            } else {
                fail("Error: InputViewModel not containing UserEditTextModel or ImageButtonModel(send)!");
            }
        }
    }

    @Test
    public void testParse_EditTextModel() {
        BaseModel baseModel = mInputViewConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse inputView json string! Parser returning null.",
                baseModel);
        testParse_containsEditTextModelAndSendButton();
        InputViewModel inputViewModel = (InputViewModel) baseModel;
        List<BaseModel> baseModels = inputViewModel.getBaseModels();

        assertNotNull("Error: UserEditTextModel having null Style!",
                ((EditTextModel) baseModels.get(0)).getStyle());
    }

}