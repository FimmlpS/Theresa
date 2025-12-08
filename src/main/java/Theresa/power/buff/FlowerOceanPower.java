package Theresa.power.buff;

import Theresa.action.DelayActionAction;
import Theresa.power.AbstractTheresaPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FlowerOceanPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:FlowerOceanPower";
    public FlowerOceanPower(AbstractCreature owner, int amt) {
        super(POWER_ID, owner,amt);
        setAmountDescription();
        setOffsetID();
        damageAmt = 1;
        updateDescription();
    }

    public int damageAmt;

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1] + damageAmt + powerStrings.DESCRIPTIONS[2];
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if(reduceAmount > 0)
            this.damageAmt+=reduceAmount;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.flash();
        addToBot(new DelayActionAction(new ReducePowerAction(owner, owner, this, 1)));
    }

    @SpirePatch(clz = AbstractMonster.class,method = "damage")
    public static class DamagePatch {
        @SpireInsertPatch(rloc = 3)
        public static void Insert(AbstractMonster _inst, DamageInfo info) {
            for(AbstractPower p : AbstractDungeon.player.powers) {
                if(p instanceof FlowerOceanPower){
                    //p.flashWithoutSound();
                    info.output += Math.max(0,((FlowerOceanPower) p).damageAmt);
                }
            }
        }
    }
}
