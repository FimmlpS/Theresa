package Theresa.action;

import Theresa.patch.DustPatch;
import Theresa.power.buff.EchoismPower;
import Theresa.relic.TenRings;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class IncreaseMaxDustAction extends AbstractGameAction {
    public IncreaseMaxDustAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        DustPatch.dustManager.dustUpLimit += this.amount;
        if(DustPatch.dustManager.dustUpLimit > 10) {
            DustPatch.dustManager.dustUpLimit = 10;
            addToTop(new TalkAction(true,"...",1F,2F));
        }
        else if(DustPatch.dustManager.dustUpLimit < 0) {
            DustPatch.dustManager.dustUpLimit = 0;
            addToTop(new TalkAction(true,"...",1F,2F));
        }
//        if(AbstractDungeon.player.hasRelic(TenRings.ID) && DustPatch.dustManager.dustUpLimit == 10){
//            AbstractDungeon.player.getRelic(TenRings.ID).onTrigger();
//        }
        this.isDone = true;
    }
}
