package Theresa.action;

import Theresa.patch.CardPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PlayCardAction extends AbstractGameAction {
    private boolean purge = false;

    AbstractCard card;

    boolean freeToPlay=false;

    int index = -1;
    public static AbstractCard[] actionIndex = new AbstractCard[11];

    public static void clear(){
        actionIndex = new AbstractCard[11];
    }

    public PlayCardAction(AbstractCard card, AbstractCreature target, boolean purge) {
        this.card = card;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.purge = purge;

    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (card==null) {
                this.isDone = true;
                return;
            }

            card.purgeOnUse = this.purge;
            AbstractDungeon.player.limbo.group.add(card);
            CardPatch.LingerField.isLingeredCard.set(card,true);
            CardPatch.LingerField.lingerPlayed.set(card,false);

            //确定位置
            for(int i =0;i<actionIndex.length;i++){
                if(actionIndex[i]==null|| CardPatch.LingerField.lingerPlayed.get(actionIndex[i])){
                    actionIndex[i] = card;
                    this.index = i;
                    break;
                }
            }

            int dif = 0;
            if(index>=0){
                if(index%2==0)
                    dif = index/2;
                else
                    dif = -(index+1)/2;
            }
            card.current_x =(float)Settings.WIDTH / 2.0F + 180 * dif * Settings.xScale;;
            card.current_y = (float)Settings.HEIGHT / 2.0F + 300F * Settings.scale;
            card.target_x = (float)Settings.WIDTH / 2.0F + 180 * dif * Settings.xScale;
            card.target_y = (float)Settings.HEIGHT / 2.0F + 300F * Settings.scale;
            card.targetAngle = 0.0F;
            card.lighten(false);
            card.drawScale = 0.32F;
            card.targetDrawScale = 0.75F;

            card.isInAutoplay = true;
            card.applyPowers();
            if(target instanceof AbstractMonster && !target.isDeadOrEscaped()){
                card.calculateCardDamage((AbstractMonster) target);
                this.addToTop(new NewQueueCardAction(card, this.target, false, true));
            }
            else {
                this.addToTop(new NewQueueCardAction(card,true,false,true));
            }


            this.addToTop(new WaitAction(0.1F));
            if (!Settings.FAST_MODE) {
                this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
            } else {
                this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
            }

            //addToBot(new DelayActionAction(new UnlimboAction(card)));

            this.isDone = true;
        }

    }
}
