package Theresa.power.buff;

import Theresa.action.HateAction;
import Theresa.patch.DustPatch;
import Theresa.patch.SilkPatch;
import Theresa.power.AbstractTheresaPower;
import Theresa.relic.TheRecall;
import Theresa.silk.AbstractSilk;
import Theresa.silk.MindSilk;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class HatePower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:HatePower";
    public HatePower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        setPriority(999);
        updateDescription();
    }

    public int initialMax = 6;
    public int extraMax = 0;

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer){
            AbstractPower hope = this.owner.getPower(HopePower.POWER_ID);
            if(hope != null && this.amount > hope.amount) {
                this.flash();
                if(hope.amount<=1)
                    addToBot(new RemoveSpecificPowerAction(owner, owner, HopePower.POWER_ID));
                else
                    addToBot(new ReducePowerAction(owner, owner, HopePower.POWER_ID, 1));
                addToBot(new ApplyPowerAction(owner,owner,this,1));
            }
        }
    }

    @Override
    public void atStartOfTurn() {
        if(this.owner instanceof AbstractMonster){
            AbstractPower hope = this.owner.getPower(HopePower.POWER_ID);
            if(hope != null && this.amount > hope.amount) {
                this.flash();
                if(hope.amount<=1)
                    addToBot(new RemoveSpecificPowerAction(owner, owner, HopePower.POWER_ID));
                else
                    addToBot(new ReducePowerAction(owner, owner, HopePower.POWER_ID, 1));
                addToBot(new ApplyPowerAction(owner,owner,this,1));
            }
        }
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        singleUpdate();
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        singleUpdate();
        int amt = Math.min(initialMax+extraMax, amount);
        if(this.owner.isPlayer){
            addToBot(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(amt,true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE,true));
        }
        else {
            addToBot(new HateAction(this,amt));
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        singleUpdate();
        this.flashWithoutSound();
        int amt = Math.min(initialMax+extraMax, amount);
        if(this.owner.isPlayer){
            addToBot(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(amt,true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE,true));
        }
        else {
            addToBot(new HateAction(this,amt));
        }
    }

    private void singleUpdate(){
        if(this.owner==null || !this.owner.isPlayer){
            int ex = extraMax;
            extraMax = 0;
            if(ex!=extraMax){
                updateDescription();
            }
            return;
        }
        //ArrayList<AbstractCard> group = new ArrayList<>(AbstractDungeon.player.hand.group);
        ArrayList<AbstractCard> group = new ArrayList<>();
        group.addAll(DustPatch.dustManager.dustCards);
        int ex = 0;
        for(AbstractCard c : group) {
            AbstractSilk silk = SilkPatch.SilkCardField.silk.get(c);
            if(silk instanceof MindSilk)
                ex+=silk.amount;
        }
        if(ex>MindSilk.getTotalAmount()){
            ex = MindSilk.getTotalAmount();
        }
        if(AbstractDungeon.player.hasRelic(TheRecall.ID))
            ex+=2;
        if(ex!=extraMax){
            extraMax = ex;
            updateDescription();
        }
    }

    @Override
    public void updateDescription() {
        int tmp = initialMax+extraMax;
        int tmp2 = extraMax+amount;
        this.description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1] + tmp + powerStrings.DESCRIPTIONS[2];
    }
}
