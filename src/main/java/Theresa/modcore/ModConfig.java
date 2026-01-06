package Theresa.modcore;

import Theresa.effect.BlushEffect;
import Theresa.effect.FinaleEffect;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.io.IOException;
import java.util.Properties;

public class ModConfig {
    private static final String ENABLE_SHADER = "theresa:ENABLE_SHADER";
    private static final String BAN_ENDING = "theresa:BAN_ENDING";
    public static boolean enableShader = true;
    public static boolean banEnding = false;
    public static SpireConfig config = null;
    private static Properties defaultSetting = new Properties();
    private static ModPanel settingsPanel;

    public static void initModSettings(){
        defaultSetting.setProperty(ENABLE_SHADER, String.valueOf(enableShader));
        defaultSetting.setProperty(BAN_ENDING, String.valueOf(banEnding));
        try {
            config = new SpireConfig("Theresa_FimmlpS","Common",defaultSetting);
            config.load();
            enableShader = config.getBool(ENABLE_SHADER);
            banEnding = config.getBool(BAN_ENDING);
        }
        catch (Exception e){
            TheresaMod.logSomething("Init Config Failed" + e.getLocalizedMessage());
        }
    }

    public static void initModConfigMenu(){
        settingsPanel = new ModPanel();
        addEnableMenu();
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theresa:Config");
        String modConfDesc = uiStrings.TEXT[1];
        Texture badge  = ImageMaster.loadImage("TheresaResources/img/orbs/EnergyOrb.png");
        BaseMod.registerModBadge(badge,"theresa","FimmlpS",modConfDesc,settingsPanel);
    }

    private static void addEnableMenu(){
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theresa:Config");
        ModLabeledToggleButton btn = new ModLabeledToggleButton(uiStrings.TEXT[0],350F,650F, Settings.CREAM_COLOR, FontHelper.charDescFont, enableShader,settingsPanel, modLabel -> {
        },modToggleButton -> {
            enableShader = modToggleButton.enabled;
            if(!enableShader){
                BlushEffect.clear();
                FinaleEffect.clear();
            }
            config.setString(ENABLE_SHADER, String.valueOf(enableShader));
            try {
                config.save();
            } catch (IOException e) {
                TheresaMod.logSomething("save config credit failed" + e.getLocalizedMessage());
            }
        });

        ModLabeledToggleButton btn2 = new ModLabeledToggleButton(uiStrings.TEXT[2],350F,560F, Settings.CREAM_COLOR, FontHelper.charDescFont, banEnding,settingsPanel, modLabel -> {
        },modToggleButton -> {
            banEnding = modToggleButton.enabled;
            config.setString(BAN_ENDING, String.valueOf(banEnding));
            try {
                config.save();
            } catch (IOException e) {
                TheresaMod.logSomething("save config credit failed" + e.getLocalizedMessage());
            }
        });

        settingsPanel.addUIElement(btn);
        settingsPanel.addUIElement(btn2);
    }
}
