package Theresa.action;

import Theresa.helper.TheresaHelper;
import Theresa.patch.DustPatch;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ManyLightAction extends AbstractGameAction {
    public static final float TIME_PAD = 0.08F;

    public ManyLightAction() {
        this.duration = startDuration = Settings.ACTION_DUR_FAST;
    }
    ArrayList<AbstractCard> drawRemains = new ArrayList<>();
    ArrayList<AbstractCard> discardRemains = new ArrayList<>();

    boolean initial = false;

    private void workOne(){
        if(!drawRemains.isEmpty()){
            AbstractCard c = drawRemains.remove(0);
            AbstractDungeon.player.drawPile.removeCard(c);
            c.current_x = CardGroup.DRAW_PILE_X;
            c.current_y = CardGroup.DRAW_PILE_Y;
            c.drawScale = 0.5F;
            DustPatch.dustManager.addCard(c);
            duration += Gdx.graphics.getDeltaTime()+TIME_PAD;
        }
        else if(!discardRemains.isEmpty()){
            AbstractCard c = discardRemains.remove(0);
            AbstractDungeon.player.discardPile.removeCard(c);
            c.current_x = CardGroup.DISCARD_PILE_X;
            c.current_y = CardGroup.DISCARD_PILE_Y;
            c.drawScale = 0.5F;
            DustPatch.dustManager.addCard(c);
            duration += Gdx.graphics.getDeltaTime()+TIME_PAD;
        }
    }

    @Override
    public void update() {
        if (!initial) {
            for(AbstractCard c : new ArrayList<>(AbstractDungeon.player.drawPile.group)) {
                if(TheresaHelper.canBecomeDust(c,true)){
                    drawRemains.add(c);
                }
            }
//            for(AbstractCard c : new ArrayList<>(AbstractDungeon.player.discardPile.group)) {
//                if(TheresaHelper.canBecomeDust(c,true)){
//                    discardRemains.add(c);
//                }
//            }
            initial = true;
        }
        if(duration<=startDuration){
            workOne();
        }
        this.tickDuration();
    }
}
