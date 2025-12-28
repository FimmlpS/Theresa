package Theresa.power.buff;

import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class FlowerOceanPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:FlowerOceanPower";
    public FlowerOceanPower(AbstractCreature owner, int amt) {
        super(POWER_ID, owner,amt);
        setAmountDescription();
    }

    @Override
    public void atStartOfTurn() {
        int maxEnergy = Math.min(amount, EnergyPanel.getCurrentEnergy());
        if(maxEnergy>0){
            this.flash();
            addToBot(new GainEnergyAction(maxEnergy));
        }
    }
}
