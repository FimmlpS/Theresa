package Theresa.patch;

import Theresa.dungeon.TheBabel;
import Theresa.modcore.TheresaMod;
import Theresa.relic.KnownRelic;
import Theresa.relic.UnknownRelic;
import Theresa.room.DeadBossRoom;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;

import java.util.Iterator;

public class BabelPatch {
    public static String BOSS_KEY = "";

    public static boolean EnterBabel = false;
    public static boolean EnteredBabel = false;
    public static boolean KilledDead = false;
    public static boolean NoKilledDead = false;

    public static boolean enableExtra(){
        return EnterBabel && !EnteredBabel;
    }

    @SpirePatch(clz = CardCrawlGame.class,method = "getDungeon",paramtypez = {String.class, AbstractPlayer.class})
    public static class GetDungeonPatch{
        @SpirePostfixPatch
        public static AbstractDungeon Postfix(AbstractDungeon _ret, CardCrawlGame _inst, String key, AbstractPlayer p){

            if(key.equals(TheBabel.ID))
                return new TheBabel(p,AbstractDungeon.specialOneTimeEventList);

            return _ret;
        }
    }

    @SpirePatch(clz = CardCrawlGame.class,method = "getDungeon",paramtypez = {String.class,AbstractPlayer.class, SaveFile.class})
    public static class GetDungeonOnSavePatch{
        @SpirePostfixPatch
        public static AbstractDungeon Postfix(AbstractDungeon _ret, CardCrawlGame _inst, String key, AbstractPlayer p,SaveFile file){

            if(key.equals(TheBabel.ID))
                return new TheBabel(p,file);

            return _ret;
        }
    }

    @SpirePatch(
            clz = MainMusic.class,
            method = "getSong"
    )
    public static class MainMusicPatch{
        @SpirePostfixPatch
        public static Music Postfix(Music _result, MainMusic _inst, String key){
            if(key.equals(TheBabel.ID)){
                return MainMusic.newMusic("TheresaResources/audio/main.ogg");
            }

            return _result;
        }
    }

    @SpirePatch(
            clz = TempMusic.class,
            method = "getSong"
    )
    public static class TempMusicPatch{
        @SpirePostfixPatch
        public static Music Postfix(Music _result, TempMusic _inst,String key){
            if(key.equals("BOSS_BEYOND")){
                if(BOSS_KEY.equals("BOSS1")){
                    TheresaMod.logSomething("=== Playing BOSS1 ===");
                    return MainMusic.newMusic("TheresaResources/audio/boss1.ogg");
                }
                else if(BOSS_KEY.equals("BOSS2")){
                    TheresaMod.logSomething("=== Playing BOSS2 ===");
                    return MainMusic.newMusic("TheresaResources/audio/boss2.ogg");
                }
            }
            return _result;
        }
    }


    @SpirePatch(clz = DungeonMap.class,method = "calculateMapSize")
    public static class MapSizePatch{
        @SpirePostfixPatch
        public static float Postfix(float _ret,DungeonMap _inst){
            if(AbstractDungeon.id.equals(TheBabel.ID))
                return Settings.MAP_DST_Y * 4F - 1280F*Settings.scale;
            return _ret;
        }
    }

    @SpirePatch(clz = DungeonMap.class,method = "update")
    public static class UpdatePatch {
        @SpireInsertPatch(rloc = 40)
        public static void Insert(DungeonMap _inst) {
            if (_inst.bossHb.hovered && (InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed())) {
                if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMPLETE && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MAP) {
                    if ((Settings.isDebug || AbstractDungeon.id.equals(TheBabel.ID))) {
                        AbstractDungeon.getCurrMapNode().taken = true;
                        MapRoomNode node2 = AbstractDungeon.getCurrMapNode();
                        Iterator var2 = node2.getEdges().iterator();

                        while (var2.hasNext()) {
                            MapEdge e = (MapEdge) var2.next();
                            if (e != null) {
                                e.markAsTaken();
                            }
                        }

                        InputHelper.justClickedLeft = false;
                        CardCrawlGame.music.fadeOutTempBGM();
                        MapRoomNode node = new MapRoomNode(-1, 15);
                        node.room = new DeadBossRoom();
                        AbstractDungeon.nextRoom = node;
                        if (AbstractDungeon.pathY.size() > 1) {
                            AbstractDungeon.pathX.add(AbstractDungeon.pathX.get(AbstractDungeon.pathX.size() - 1));
                            AbstractDungeon.pathY.add((Integer) AbstractDungeon.pathY.get(AbstractDungeon.pathY.size() - 1) + 1);
                        } else {
                            AbstractDungeon.pathX.add(1);
                            AbstractDungeon.pathY.add(15);
                        }

                        AbstractDungeon.nextRoomTransitionStart();
                        _inst.bossHb.hovered = false;
                    }
                }
            }
        }
    }

    @SpirePatch(clz = DungeonMap.class,method = "render")
    public static class RenderPatch{
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(DungeonMap _inst, SpriteBatch sb){
            if(AbstractDungeon.id.equals(TheBabel.ID)){
                ReflectionHacks.privateMethod(DungeonMap.class,"renderFinalActMap", SpriteBatch.class).invoke(_inst, sb);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = DungeonMap.class,method = "renderMapBlender")
    public static class RenderBlenderPatch{
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(DungeonMap _inst, SpriteBatch sb){
            if(AbstractDungeon.id.equals(TheBabel.ID))
                return SpireReturn.Return();
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = DungeonMapScreen.class,method = "open")
    public static class OpenMapPatch{
        @SpireInsertPatch(rloc = 8)
        public static void Insert(DungeonMapScreen _inst,boolean doScrollingAnimation){
            if(AbstractDungeon.id.equals(TheBabel.ID)){
                ReflectionHacks.setPrivate(_inst,DungeonMapScreen.class,"mapScrollUpperLimit",-800.0F * Settings.scale);
            }
        }
    }

    @SpirePatch(clz = ProceedButton.class,method = "update")
    public static class EndSiestaPatch{
        @SpireInsertPatch(rloc = 40)
        public static void Insert(ProceedButton _inst){
            if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
                if (AbstractDungeon.id.equals(TheBabel.ID)) {
                    CardCrawlGame.music.fadeOutBGM();
                    MapRoomNode node = new MapRoomNode(3, 7);
                    node.room = new TrueVictoryRoom();
                    AbstractDungeon.nextRoom = node;
                    AbstractDungeon.closeCurrentScreen();
                    AbstractDungeon.nextRoomTransitionStart();
                    _inst.hide();
                }
            }
        }
    }

    //change true victory
    @SpirePatch(clz = TrueVictoryRoom.class,method = "onPlayerEntry")
    public static class TrueVictoryPatch{
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(TrueVictoryRoom _inst){
            if(EnteredBabel)
                return SpireReturn.Continue();
            //todo
            boolean canEnter = false;
            AbstractRelic r = AbstractDungeon.player.getRelic(UnknownRelic.ID);
            if(r == null){
                r = AbstractDungeon.player.getRelic(KnownRelic.ID);
            }
            if(r!=null){
                canEnter = r.counter>=10000;
                if(BaseMod.hasModID("spireTogether:")){
                    canEnter = r.counter>=7500;
                }
            }
            if(canEnter&&!EnterBabel){
                CardCrawlGame.stopClock = false;
                EnterBabel = true;
                CardCrawlGame.music.fadeOutTempBGM();
                MapRoomNode node = new MapRoomNode(-1, 15);
                node.room = new TreasureRoomBoss();
                AbstractDungeon.nextRoom = node;
                AbstractDungeon.closeCurrentScreen();
                CardCrawlGame.dungeon.nextRoomTransition();
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    //标记已经进入过
    @SpirePatch(clz = AbstractDungeon.class, method = "nextRoomTransition",paramtypez = {SaveFile.class})
    public static class EnteredPatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractDungeon _inst, SaveFile saveFile){
            if(CardCrawlGame.dungeon instanceof TheBabel && AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss){
                EnteredBabel = true;
            }
        }
    }

    //dungeon
    @SpirePatch(clz = TreasureRoomBoss.class,method = "getNextDungeonName")
    public static class TreasureBossPatch{
        @SpirePostfixPatch
        public static String Postfix(String _ret,TreasureRoomBoss _inst){
            if(EnteredBabel)
                return _ret;
            if(EnterBabel&&!AbstractDungeon.id.equals(TheBabel.ID)){
                return TheBabel.ID;
            }
            return _ret;
        }
    }

    //dungeon actlikeit
    @SpirePatch(clz = ProceedButton.class,method = "goToNextDungeon")
    public static class ProceedButtonTPatch{
        @SpirePostfixPatch
        public static void Postfix(ProceedButton _inst){
            if(EnteredBabel)
                return;
            if(EnterBabel&&!AbstractDungeon.id.equals(TheBabel.ID)){
                CardCrawlGame.nextDungeon = TheBabel.ID;
            }
        }
    }

    //room保存
    @SpirePatch(clz = SaveFile.class,method = SpirePatch.CONSTRUCTOR,paramtypez = {SaveFile.SaveType.class})
    public static class SaveFilePatch{
        @SpirePostfixPatch
        public static void Postfix(SaveFile _inst, SaveFile.SaveType type){
            if(AbstractDungeon.id !=null && AbstractDungeon.id.equals(TheBabel.ID)){
                _inst.room_x = 3;
                _inst.room_y = 2;
            }
        }
    }

    @SpirePatch(clz = AbstractDungeon.class,method = "populatePathTaken")
    public static class PathTakenPatch{
        @SpireInsertPatch(rloc = 5,localvars = {"node"})
        public static void Insert(AbstractDungeon _inst,SaveFile saveFile,MapRoomNode node){
            if(saveFile.level_name != null && saveFile.level_name.equals(TheBabel.ID)){
                node.room = new DeadBossRoom();
            }
        }
    }

    //召唤敌人
    @SpirePatch(clz = MonsterGroup.class,method = "applyEndOfTurnPowers")
    public static class MonsterGroupApplyEndOfTurnPatch{
        @SpirePostfixPatch
        public static void Postfix(MonsterGroup _inst){
            DeadBossRoom d = DeadBossRoom.getCurrent();
            if(d!=null){
                d.spawnDeadmen();
            }
        }
    }

    //不会死亡
    @SpirePatch(clz = AbstractPlayer.class,method = "damage")
    public static class LostHPActionPatch {
        @SpireInsertPatch(rloc = 150)
        public static SpireReturn<Void> Insert(AbstractPlayer _inst, DamageInfo info){
            DeadBossRoom d = DeadBossRoom.getCurrent();
            if(d!=null && _inst.currentHealth < 1) {
                _inst.currentHealth = 1;
                _inst.isDead = false;
                d.endWithoutWinning();
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}
