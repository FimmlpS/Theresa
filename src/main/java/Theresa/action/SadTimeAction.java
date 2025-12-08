package Theresa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SadTimeAction extends AbstractGameAction {
    public SadTimeAction(AbstractCard except, int amount) {
        this.except = except;
        this.amount = amount;
    }

    AbstractCard except;

    @Override
    public void update() {
        int attack = amount;
        int skill = amount;
        int power = amount;
        int status = amount;
        int curse = amount;
        for(AbstractCard c: AbstractDungeon.player.hand.group) {
            if(c==except){
                continue;
            }
            if(c.type== AbstractCard.CardType.ATTACK)
                attack--;
            else if(c.type== AbstractCard.CardType.SKILL)
                skill--;
            else if(c.type== AbstractCard.CardType.POWER)
                power--;
            else if(c.type==AbstractCard.CardType.STATUS)
                status--;
            else if(c.type== AbstractCard.CardType.CURSE)
                curse--;
        }
        if(curse>0){
            addToTop(new DrawTypeAction(AbstractCard.CardType.CURSE,1));
        }
        if(status>0){
            addToTop(new DrawTypeAction(AbstractCard.CardType.STATUS,1));
        }
        if(power>0){
            addToTop(new DrawTypeAction(AbstractCard.CardType.POWER,1));
        }
        if(skill>0){
            addToTop(new DrawTypeAction(AbstractCard.CardType.SKILL,1));
        }
        if(attack>0){
            addToTop(new DrawTypeAction(AbstractCard.CardType.ATTACK,1));
        }

        this.isDone = true;
    }
}
