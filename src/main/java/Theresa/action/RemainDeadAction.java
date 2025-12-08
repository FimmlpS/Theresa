package Theresa.action;

import Theresa.monster.Deadman;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class RemainDeadAction extends AbstractGameAction {
    public RemainDeadAction(int decrease) {
        this.amount = decrease;
    }

    @Override
    public void update() {
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if(m instanceof Deadman && m.halfDead){
                m.halfDead = false;
            }
        }
        if(amount>0){
            for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                if(!m.isDeadOrEscaped()){
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new StrengthPower(m,4*amount)));
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m,m,12*amount));
                }
            }
        }
        this.isDone = true;
    }
}
