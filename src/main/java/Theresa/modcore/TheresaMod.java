package Theresa.modcore;

import Theresa.action.PlayCardAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.card.attack.MindOscillation;
import Theresa.card.attack.TheAnswer;
import Theresa.card.attack.TheBlooder;
import Theresa.card.attack.TheWar;
import Theresa.card.power.ThousandsWish;
import Theresa.card.skill.*;
import Theresa.character.Theresa;
import Theresa.dungeon.TheBabel;
import Theresa.effect.BlushEffect;
import Theresa.helper.AttackHelper;
import Theresa.helper.CivilightHelper;
import Theresa.helper.RegisterHelper;
import Theresa.interfaces.LockedIt;
import Theresa.monster.Deadman;
import Theresa.monster.Empty;
import Theresa.patch.*;
import Theresa.relic.*;
import Theresa.silk.MindSilk;
import basemod.BaseMod;
import basemod.abstracts.CustomUnlockBundle;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

@SpireInitializer
public class TheresaMod implements SetUnlocksSubscriber,PreStartGameSubscriber,PreUpdateSubscriber,RenderSubscriber,StartGameSubscriber,OnPlayerTurnStartPostDrawSubscriber,PostBattleSubscriber,OnPlayerTurnStartSubscriber,OnStartBattleSubscriber,PostInitializeSubscriber,EditStringsSubscriber, EditKeywordsSubscriber, EditCardsSubscriber, EditCharactersSubscriber, EditRelicsSubscriber {
    private static final Logger logger = LogManager.getLogger(TheresaMod.class);

    public static Color tColor = new Color(234f/255f,221f/255f,212f/255f,1);

    public static final String cardBg512 = "TheresaResources/img/512/cardbg.png";
    public static final String energy512 = "TheresaResources/img/512/energybg.png";
    public static final String cardBg1024 = "TheresaResources/img/1024/cardbg.png";
    public static final String energy1024 = "TheresaResources/img/1024/energybg.png";

    
    public static void initialize(){
        new TheresaMod();

        try{
            Properties defaults = new Properties();
            defaults.setProperty("defaultType","0");
            defaults.setProperty("shouldResetUnlock","true");
            SpireConfig config = new SpireConfig("Theresa_FimmlpS","Common",defaults);
            DefaultTypeIndex = config.getInt("defaultType");
            ShouldResetUnlock = config.getBool("shouldResetUnlock");
            ShouldResetUnlock = false;

        }catch (IOException var1){
            var1.printStackTrace();
        }
    }
    
    public static void logSomething(String message){
        logger.info(message);
    }

    public static int DefaultTypeIndex = 0;
    public static boolean ShouldResetUnlock = false;
    
    public TheresaMod(){
        BaseMod.subscribe(this);
        BaseMod.addColor(ColorEnum.Theresa_COLOR,tColor.cpy(),tColor.cpy(),tColor.cpy(),tColor.cpy(),tColor.cpy(),tColor.cpy(),tColor.cpy(),cardBg512,cardBg512,cardBg512,energy512,cardBg1024,cardBg1024,cardBg1024,energy1024,"TheresaResources/img/orbs/EnergyOrb.png");
        ModConfig.initModSettings();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(
                new Theresa("Theresa"),
                "TheresaResources/img/charSelect/TheresaButton.png",
                "TheresaResources/img/charSelect/TheresaBG.png",
                ClassEnum.Theresa_CLASS
        );
    }

    @Override
    public void receiveEditStrings() {
        String relic = "relics.json",
                card = "cards.json",
                power= "powers.json",
                potion="potions.json",
                event="events.json",
                character="characters.json",
                ui="uis.json",
                monster="monsters.json",
                score ="scores.json",
                stance ="stances.json";
        String fore = "TheresaResources/localizations/";
        String lang;
        if(Settings.language == Settings.GameLanguage.ZHS||Settings.language == Settings.GameLanguage.ZHT){
            lang = "zh";
        }
        else{
            lang = "en";
        }
        relic = fore + lang + "/" + relic;
        card = fore + lang + "/" + card;
        power = fore + lang + "/" + power;
        potion = fore + lang + "/" + potion;
        event = fore + lang + "/" + event;
        character = fore + lang + "/" + character;
        ui = fore + lang + "/" + ui;
        monster = fore + lang + "/" + monster;
        score = fore + lang + "/" + score;
        stance = fore + lang + "/" + stance;

        String relicStrings = Gdx.files.internal(relic).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        String cardStrings = Gdx.files.internal(card).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        String powerStrings = Gdx.files.internal(power).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        //String eventStrings = Gdx.files.internal(event).readString(String.valueOf(StandardCharsets.UTF_8));
        //BaseMod.loadCustomStrings(EventStrings.class,eventStrings);
        String characterStrings = Gdx.files.internal(character).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CharacterStrings.class,characterStrings);
        String uiStrings = Gdx.files.internal(ui).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class,uiStrings);
        String monsterStrings = Gdx.files.internal(monster).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(MonsterStrings.class,monsterStrings);
        String scoreStrings = Gdx.files.internal(score).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(ScoreBonusStrings.class,scoreStrings);
        String stanceStrings = Gdx.files.internal(stance).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(StanceStrings.class,stanceStrings);
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "en";
        if (Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT) {
            lang = "zh";
        }
        else {
            lang = "en";
        }

        String json = Gdx.files.internal("TheresaResources/localizations/"+lang+"/keywords.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = (Keyword[])gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            Keyword[] var5 = keywords;
            int var6 = keywords.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Keyword keyword = var5[var7];
                BaseMod.addKeyword("theresa", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    private void initializeMonsters(){
        String[] names = CardCrawlGame.languagePack.getUIString("theresa:Monster").TEXT;
        BaseMod.addMonster(Deadman.ID,names[0],()->{
            return new MonsterGroup(new AbstractMonster[]{new Deadman(2,true),new Empty()});
        });
        BaseMod.addBoss(TheBabel.ID, Deadman.ID,Deadman.BOSS_ICON,Deadman.BOSS_ICON_O);
    }

    @Override
    public void receiveEditCards() {
        for (AbstractCard c : RegisterHelper.getCardsToAdd()) {
            BaseMod.addCard(c);
        }
    }

    @Override
    public void receiveEditRelics() {
        for(AbstractRelic r: RegisterHelper.getRelicsToAdd(true)){
            BaseMod.addRelicToCustomPool(r,ColorEnum.Theresa_COLOR);
        }

        for(AbstractRelic r:RegisterHelper.getRelicsToAdd(false)){
            BaseMod.addRelic(r, RelicType.SHARED);
        }
    }

    @Override
    public void receiveStartGame() {
        DustPatch.preBattle();
        AttackHelper.Instance.clear();
        BlushEffect.initialize();
    }

    @Override
    public void receivePreStartGame() {
        PlayCardAction.clear();
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        //DustPatch.preBattle();
        CivilightHelper.atBattleStart();
        //AttackHelper.Instance.clear();
        MindSilk.resetMindSilk();
        BlushEffect.initialize();
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        DustPatch.postBattle();
        PlayCardAction.clear();
        MindSilk.resetMindSilk();
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        PlayCardAction.clear();
        DustPatch.atTurnStart();
        SilkPatch.atTurnStart();
    }

    @Override
    public void receiveOnPlayerTurnStartPostDraw() {
        DustPatch.atTurnStartPostDraw();
    }

    @Override
    public void receivePostInitialize() {
        unlockCards();
        unlockRelics();
        initializeMonsters();
        ModConfig.initModConfigMenu();
    }

    @Override
    public void receiveRender(SpriteBatch spriteBatch) {
        //BlackPatch.render(spriteBatch);
    }

    @Override
    public void receivePreUpdate() {
        //BlackPatch.update();
    }

    @Override
    public void receiveSetUnlocks() {
        //level 0
        BaseMod.addUnlockBundle(new CustomUnlockBundle(AbstractUnlock.UnlockType.CARD, MindOscillation.ID, DeadBreath.ID, MemorizedMemory.ID),ClassEnum.Theresa_CLASS,0);

        //level 1
        BaseMod.addUnlockBundle(new CustomUnlockBundle(AbstractUnlock.UnlockType.CARD, TheAmiya.ID, TheDoctor.ID, TheCat.ID),ClassEnum.Theresa_CLASS,1);

        //level 2
        BaseMod.addUnlockBundle(new CustomUnlockBundle(AbstractUnlock.UnlockType.RELIC, TheEnd.ID, TheRecall.ID, TenRings.ID),ClassEnum.Theresa_CLASS,2);

        //level 3
        BaseMod.addUnlockBundle(new CustomUnlockBundle(AbstractUnlock.UnlockType.CARD, EndDust.ID, Forgive.ID, Rare.ID),ClassEnum.Theresa_CLASS,3);

        //level 4
        BaseMod.addUnlockBundle(new CustomUnlockBundle(AbstractUnlock.UnlockType.CARD, InsideCrystal.ID, ThousandsWish.ID, DustFalls.ID),ClassEnum.Theresa_CLASS,4);

    }

    private static void unlockCards(){
        ArrayList<AbstractCard> list = RegisterHelper.getCardsToAdd();

        if(ShouldResetUnlock){
            UnlockTracker.resetUnlockProgress(ClassEnum.Theresa_CLASS);
            for(AbstractCard c : list){
                if(c instanceof AbstractTheresaCard && ((AbstractTheresaCard) c).shouldLocked){
                    String key = c.cardID;
                    AbstractCard tmp = CardLibrary.getCard(key);
                    tmp.isSeen = false;
                    tmp.setLocked();
                    UnlockTracker.seenPref.putInteger(key,0);
                    UnlockTracker.unlockPref.putInteger(key,0);
                    if(!UnlockTracker.lockedCards.contains(key)){
                        UnlockTracker.lockedCards.add(key);
                    }
                }
            }

            for(AbstractRelic r :RegisterHelper.getRelicsToAdd(true)){
                if(r instanceof LockedIt){
                    String key = r.relicId;
                    AbstractRelic tmp = RelicLibrary.getRelic(key);
                    tmp.isSeen = false;
                    UnlockTracker.seenPref.putInteger(key,0);
                    UnlockTracker.unlockPref.putInteger(key,0);
                    if(!UnlockTracker.lockedRelics.contains(key)){
                        UnlockTracker.lockedRelics.add(key);
                    }
                }
            }
            UnlockTracker.seenPref.flush();
            UnlockTracker.unlockPref.flush();
            ShouldResetUnlock = false;
            try{
                SpireConfig config = new SpireConfig("Theresa_FimmlpS","Common");
                config.setBool("shouldResetUnlock",false);
                config.save();

            }catch (IOException var1){
                var1.printStackTrace();
            }
        }

        Iterator<AbstractCard> var1 = list.iterator();
        while (var1.hasNext()){
            AbstractCard c = var1.next();
            if(c instanceof AbstractTheresaCard){
                if(((AbstractTheresaCard) c).shouldLocked)
                    continue;
            }
            String key = c.cardID;
            AbstractCard tmp = CardLibrary.getCard(key);
            if (tmp != null && !CardLibrary.getCard(key).isSeen) {
                tmp.isSeen = true;
                tmp.unlock();
                UnlockTracker.seenPref.putInteger(key, 1);
            }
        }
        UnlockTracker.seenPref.flush();
    }

    private static void unlockRelics(){
        for(AbstractRelic r:RegisterHelper.getRelicsToAdd(true)){
            if(r instanceof LockedIt)
                continue;
            UnlockTracker.markRelicAsSeen(r.relicId);
        }

        for(AbstractRelic r:RegisterHelper.getRelicsToAdd(false)){
            UnlockTracker.markRelicAsSeen(r.relicId);
        }
    }
}
