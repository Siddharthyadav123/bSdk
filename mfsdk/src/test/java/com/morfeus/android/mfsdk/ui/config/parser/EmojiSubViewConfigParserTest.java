package com.morfeus.android.mfsdk.ui.config.parser;

import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SubViewItemModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SubViewModel;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class EmojiSubViewConfigParserTest {
    SubViewConfigParser mSubViewConfigParser;

    @Before
    public void setUp() throws Exception {
        mSubViewConfigParser = SubViewConfigParser.getInstance();
    }

    @Test
    public void testParse_returnSubViewModel() throws Exception {
        BaseModel baseModel = mSubViewConfigParser.parse(ConfigParserHelper.TEST_NEW_SUB_VIEW);
        assertNotNull("Error: Failed to parse subview json string! Parser returning null.",
                baseModel);
        assertTrue("Error: Failed to parse subview json string. Not returning SubViewModel!",
                baseModel instanceof SubViewModel);
        assertEquals("Error: Invalid id of SubViewModel!", "subview", ((SubViewModel) baseModel)
                .getId());
    }

    @Test
    public void testParse_subViewModelContiansSubViews() {
        BaseModel baseModel = mSubViewConfigParser.parse(ConfigParserHelper.TEST_NEW_SUB_VIEW);
        assertNotNull("Error: Failed to parse subview json string! Parser returning null.",
                baseModel);
        assertNotNull("Error: SubViewModel doesn't containing sub views", ((SubViewModel) baseModel)
                .getSubComponentModels());
    }

    @Test
    public void testParse_SmileyViewAndSimleyModelExistOrNot() {
        BaseModel baseModel = mSubViewConfigParser.parse(ConfigParserHelper.TEST_NEW_SUB_VIEW);
        assertNotNull("Error: Failed to parse subview json string! Parser returning null.",
                baseModel);

        List<BaseModel> subComponents = ((SubViewModel) baseModel).getSubComponentModels();
        SubViewItemModel subViewItemModel = getSmileySubviewItemModel(subComponents);
        assertNotNull("Error: SubViewModel doesn't containing smiley views", subViewItemModel);
        assertNotNull("Error: SubViewItemModel doesn't containing smiley list", subViewItemModel.getSmileySubViewModelList());
        assertTrue("Error: SubViewItemModeList is not SmileySubview",
                subViewItemModel.getSmileySubViewModelList().get(0) instanceof SubViewItemModel.SmileySubViewModel);
    }

    @Test
    public void testParse_smileyEachSmileySubViewContainsImageAndEmojis() {
        BaseModel baseModel = mSubViewConfigParser.parse(ConfigParserHelper.TEST_NEW_SUB_VIEW);
        assertNotNull("Error: Failed to parse subview json string! Parser returning null.",
                baseModel);

        List<BaseModel> subComponents = ((SubViewModel) baseModel).getSubComponentModels();
        SubViewItemModel subViewItemModel = getSmileySubviewItemModel(subComponents);

        if (subViewItemModel != null) {
            List<SubViewItemModel.SmileySubViewModel> smileySubViewModelList
                    = subViewItemModel.getSmileySubViewModelList();
            if (smileySubViewModelList != null) {
                for (int i = 0; i < smileySubViewModelList.size(); i++) {
                    SubViewItemModel.SmileySubViewModel smileySubViewModel
                            = smileySubViewModelList.get(i);
                    assertNotNull("Error: Smiley " + i + " item doesn't contains tab image", smileySubViewModel.getImage());
                    assertNotNull("Error: Smiley " + i + " item doesn't contains emoji contents", smileySubViewModel.getContents());
                }
            }
        }

    }

    private SubViewItemModel getSmileySubviewItemModel(List<BaseModel> subComponents) {
        if (subComponents != null) {
            for (BaseModel baseModel : subComponents) {
                if (baseModel.getId().equalsIgnoreCase(ConfigParser.KEY_SMILEY_VIEW)) {
                    return (SubViewItemModel) baseModel;
                }
            }
        }
        return null;
    }
}
