package Theresa.power.buff;

import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class HatredTimePower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:HatredTimePower";

    public HatredTimePower(AbstractCreature owner) {
        super(POWER_ID, owner, 1);
        setAmountDescription();
        loadRegion("firebreathing");
    }

    @Override
    public void atStartOfTurn() {
        this.amount--;
        if(amount<=0){
            this.amount = 2;
            this.flash();
            addToBot(new ApplyPowerAction(owner,owner,new HatePower(owner,1),1));
        }
        updateDescription();
    }
}
