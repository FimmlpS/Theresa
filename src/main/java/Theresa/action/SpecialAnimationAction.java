package Theresa.action;

import Theresa.helper.AttackHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class SpecialAnimationAction extends AbstractGameAction {
    public SpecialAnimationAction(AttackHelper.ForCharacter attacker, boolean allow) {
        this.attacker = attacker;
        this.allow = allow;
        this.actionType = ActionType.WAIT;
        this.duration = startDuration = 0.2F;
    }

    public SpecialAnimationAction(AttackHelper.ForCharacter attacker) {
        this(attacker, true);
    }

    public void update() {
        if(duration == startDuration) {
            AttackHelper.Instance.addCharacter(attacker,allow);
        }
        this.tickDuration();
    }

    AttackHelper.ForCharacter attacker;
    boolean allow;
}
