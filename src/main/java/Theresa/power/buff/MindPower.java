package Theresa.power.buff;

import Theresa.power.AbstractTheresaPower;
import Theresa.silk.MindSilk;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class MindPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:MindPower";

    public MindPower(AbstractCreature owner) {
        super(POWER_ID,owner);
        setAmountDescription();
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        if(amount!= MindSilk.paddingRemains){
            this.fontScale = 8.0F;
            this.amount = MindSilk.paddingRemains;
            updateDescription();
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.flashWithoutSound();
        MindSilk.resetMindSilk();
    }
}
