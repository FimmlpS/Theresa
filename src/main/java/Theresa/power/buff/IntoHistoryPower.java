package Theresa.power.buff;

import Theresa.action.DustToPileAction;
import Theresa.action.MakeTempCardInDustAction;
import Theresa.action.UpdateTurnCostAction;
import Theresa.helper.TheresaHelper;
import Theresa.power.AbstractTheresaPower;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class IntoHistoryPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:IntoHistoryPower";

    public IntoHistoryPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        setAmountDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        //super.stackPower(stackAmount);
    }

    @Override
    public void onSpecificTrigger() {
        this.flashWithoutSound();
        for(int i = 0;i<this.amount;i++) {
            int tryBegin = 0;
            AbstractCard random = null;
            while (random == null && tryBegin < 30) {
                random = AbstractDungeon.returnTrulyRandomCardInCombat();
                if(!TheresaHelper.canBecomeDust(random,true))
                    random = null;
                tryBegin++;
            }
            if(random != null){
                AbstractCard copy = random.makeCopy();
                CardModifierManager.addModifier(copy,new ExhaustMod());
                addToBot(new MakeTempCardInDustAction(copy,1));
            }
        }
    }
}
