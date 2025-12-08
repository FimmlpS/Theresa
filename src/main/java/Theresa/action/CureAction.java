package Theresa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class CureAction extends AbstractGameAction {
    public CureAction(AbstractCreature target, int amt) {
        setValues(target,target,amt);
        actionType = ActionType.HEAL;
    }

    @Override
    public void update() {
        if(amount<=0)
            amount = 4;
        int tmp = (target.maxHealth-target.currentHealth)/amount;
        if(tmp<0)
            tmp = 0;
        addToTop(new HealAction(target,source,tmp));
        this.isDone = true;
    }
}
