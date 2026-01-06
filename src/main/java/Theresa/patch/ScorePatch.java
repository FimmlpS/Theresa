package Theresa.patch;

import Theresa.character.Theresa;
import Theresa.screen.TypeSelectScreen;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.ScoreBonusStrings;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import com.megacrit.cardcrawl.screens.GameOverStat;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import javassist.CtBehavior;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ScorePatch {
    public static final ScoreBonusStrings theBabel = CardCrawlGame.languagePack.getScoreString("theresa:TheBabel");

    @SpirePatch(clz = GameOverScreen.class,method = "checkScoreBonus")
    public static class CheckScorePatch{
        @SpireInsertPatch(locator = Locator.class, localvars = {"points"})
        public static void Insert(boolean victory, @ByRef int[] points) {
            if (BabelPatch.KilledDead) {
                points[0] += 300;
            }
            if(BabelPatch.NoKilledDead){
                points[0] += 50;
            }
            if(TypeSelectScreen.getType()==2){
                points[0] += 100;
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
                return LineFinder.findInOrder(ctBehavior, (Matcher)methodCallMatcher);
            }
        }
    }

    @SpirePatch(clz = VictoryScreen.class, method = "createGameOverStats")
    public static class VictoryStatsPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(VictoryScreen _inst) {
            ScorePatch.addStats((GameOverScreen)_inst);
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(VictoryScreen.class, "IS_POOPY");
                return LineFinder.findInOrder(ctBehavior, (Matcher)fieldAccessMatcher);
            }
        }
    }

    @SpirePatch(clz = DeathScreen.class, method = "createGameOverStats")
    public static class DeathStatsPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(DeathScreen _inst) {
            ScorePatch.addStats((GameOverScreen)_inst);
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(DeathScreen.class, "IS_POOPY");
                return LineFinder.findInOrder(ctBehavior, (Matcher)fieldAccessMatcher);
            }
        }
    }

    private static void addStats(GameOverScreen _inst) {
        try {
            Field stats = GameOverScreen.class.getDeclaredField("stats");
            stats.setAccessible(true);
            if (BabelPatch.KilledDead) {
                ((ArrayList<GameOverStat>) stats.get(_inst)).add(new GameOverStat(theBabel.DESCRIPTIONS[0], theBabel.DESCRIPTIONS[1], Integer.toString(300)));
            }
            if (BabelPatch.NoKilledDead) {
                ((ArrayList<GameOverStat>) stats.get(_inst)).add(new GameOverStat(theBabel.DESCRIPTIONS[2], theBabel.DESCRIPTIONS[3], Integer.toString(50)));
            }
            if((AbstractDungeon.player instanceof Theresa) && (_inst instanceof VictoryScreen)){
                if(TypeSelectScreen.getType()==2){
                    ((ArrayList<GameOverStat>) stats.get(_inst)).add(new GameOverStat(theBabel.DESCRIPTIONS[4], theBabel.DESCRIPTIONS[5], Integer.toString(100)));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Unable to set game over stats.", e);
        }
    }

    @SpirePatch(clz = GameOverStat.class,method = SpirePatch.CLASS)
    public static class GameOverStatField {
        public static SpireField<Color> preColor = new SpireField<>(() -> Color.WHITE);
    }

    @SpirePatch(clz = GameOverStat.class,method = "render")
    public static class GameOverStatPatch {
        @SpirePrefixPatch
        public static void Prefix(GameOverStat _inst, SpriteBatch sb, float x, float y) {
            if(_inst.label != null && (_inst.label.equals(theBabel.DESCRIPTIONS[0])||_inst.label.equals(theBabel.DESCRIPTIONS[2])||_inst.label.equals(theBabel.DESCRIPTIONS[4]))){
                Color current = ReflectionHacks.getPrivate(_inst, GameOverStat.class, "color");
                GameOverStatField.preColor.set(_inst,current);
                Color golden = Color.GOLDENROD.cpy();
                golden.a = current.a;
                ReflectionHacks.setPrivate(_inst, GameOverStat.class, "color", golden);
            }
        }

        @SpirePostfixPatch
        public static void Postfix(GameOverStat _inst, SpriteBatch sb, float x, float y) {
            Color pre = GameOverStatField.preColor.get(_inst);
            ReflectionHacks.setPrivate(_inst, GameOverStat.class, "color",pre);
        }
    }
}
