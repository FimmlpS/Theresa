package Theresa.patch;

import Theresa.power.buff.HatePower;
import Theresa.power.buff.HatredTimePower;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BobEffect;

public class MonsterPatch {
    //显示困难难度下额外伤害
    private static boolean isChanging = false;
    private static AbstractMonster monster = null;

    @SpirePatch(clz = AbstractMonster.class,method = "renderDamageRange")
    public static class RenderDamageRangePatch {
        @SpirePrefixPatch
        public static void Prefix(AbstractMonster _inst, SpriteBatch sb) {
            isChanging = true;
            monster = _inst;
            if(_inst.hasPower(HatredTimePower.POWER_ID) && !_inst.intent.name().contains("ATTACK")){
                BobEffect bobEffect = ReflectionHacks.getPrivate(_inst,AbstractMonster.class,"bobEffect");
                Color intentColor = ReflectionHacks.getPrivate(_inst,AbstractMonster.class,"intentColor");
                FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, "     ", _inst.intentHb.cX - 30.0F * Settings.scale, _inst.intentHb.cY + bobEffect.y - 12.0F * Settings.scale, intentColor);
            }
        }

        @SpirePostfixPatch
        public static void Postfix(AbstractMonster _inst, SpriteBatch sb) {
            isChanging = false;
            monster = null;
        }
    }

    @SpirePatch(clz = FontHelper.class,method = "renderFontLeftTopAligned")
    public static class RenderFontLeftTopAlignedPatch {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, Color c) {
            if(isChanging && monster != null && monster.powers!=null) {
                AbstractPower ht = monster.getPower(HatredTimePower.POWER_ID);
                if(ht instanceof HatredTimePower && ((HatredTimePower) ht).showedAmount == 1) {
                    int extraDmg = 0;
                    AbstractPower h =monster.getPower(HatePower.POWER_ID);
                    if(h != null) {
                        extraDmg = h.amount+1;
                    }
                    else {
                        extraDmg = 1;
                    }
                    msg[0] += " + "+extraDmg;
                }
            }
        }
    }
}
