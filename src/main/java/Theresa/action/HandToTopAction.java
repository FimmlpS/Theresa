package Theresa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HandToTopAction extends AbstractGameAction {
    private AbstractCard targetCard;
    private CardGroup group;

    public HandToTopAction(AbstractCard targetCard) {
        this.targetCard = targetCard;
        this.actionType = ActionType.DISCARD;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.group == null) {
                this.group = AbstractDungeon.player.hand;
            }

            if (this.group.contains(this.targetCard)) {
                this.group.moveToDeck(this.targetCard,false);
            }
        }

        this.tickDuration();
    }
}
