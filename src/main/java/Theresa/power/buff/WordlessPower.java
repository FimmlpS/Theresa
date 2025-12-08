package Theresa.power.buff;

import Theresa.power.AbstractTheresaPower;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class WordlessPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:WordlessPower";
    public WordlessPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner,amount);
        setAmountDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @SpirePatch(clz = DrawCardAction.class,method = "update")
    public static class StopDrawPatch{
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(DrawCardAction _inst){
            if(AbstractDungeon.player.hasPower(POWER_ID)){
                if(_inst.amount>0){
                    AbstractDungeon.actionManager.addToTop(new GainEnergyAction(AbstractDungeon.player.getPower(POWER_ID).amount));
                }
                _inst.amount = 0;
                if(ReflectionHacks.getPrivate(_inst,DrawCardAction.class,"clearDrawHistory"))
                    DrawCardAction.drawnCards.clear();
                ReflectionHacks.privateMethod(DrawCardAction.class,"endActionWithFollowUp").invoke(_inst);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}
