package Theresa.action;

import Theresa.modifier.ConnectivityMod;
import Theresa.patch.DustPatch;
import Theresa.patch.OtherEnum;
import Theresa.patch.SilkPatch;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ConnectivityAction extends AbstractGameAction {
    public ConnectivityAction(){

    }

    @Override
    public void update() {
        for(AbstractCard c : DrawCardAction.drawnCards){
            c.flash();
            CardModifierManager.addModifier(c,new ConnectivityMod());
            boolean dust = DustPatch.dustManager.containsCard(c);
            SilkPatch.triggerSilk(SilkPatch.TriggerType.ALL,c,dust? OtherEnum.Theresa_Dust: CardGroup.CardGroupType.HAND);
        }
        this.isDone = true;
    }
}
