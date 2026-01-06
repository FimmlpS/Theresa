package Theresa.action;

import Theresa.card.skill.IdealReflection;
import Theresa.patch.DustPatch;
import Theresa.power.buff.EchoismPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class DustAction extends AbstractGameAction {

    public DustAction(int amount){
        this(amount,false);
    }

    public DustAction(int amount,boolean addToTop){
        this.amount = amount;
        this.addToTop = addToTop;
    }

    public DustAction setExhaust(){
        this.exhaustIt = true;
        return this;
    }

    boolean exhaustIt = false;
    boolean addToTop = false;

    @Override
    public void update() {
        for(int i =0; i<this.amount; i++)
            DustPatch.dustManager.dustIt(addToTop,exhaustIt);
        this.isDone = true;
    }
}
