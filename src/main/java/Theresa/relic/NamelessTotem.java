package Theresa.relic;

import Theresa.card.attack.SarkazSee;
import Theresa.card.attack.TheBlooder;
import Theresa.card.skill.AStory;
import Theresa.helper.StringHelper;
import Theresa.interfaces.LockedIt;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.ArrayList;

public class NamelessTotem extends CustomRelic {
    public static final String ID = "theresa:NamelessTotem";

    public NamelessTotem() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.MAGICAL);
        this.counter = -1;
    }

    private boolean cardsSelected = true;

    @Override
    public void onEquip() {
        this.cardsSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for(AbstractCard card : AbstractDungeon.player.masterDeck.getPurgeableCards().group) {
            tmp.addToTop(card);
        }

        if (tmp.group.isEmpty()) {
            this.cardsSelected = true;
        } else {
            if (tmp.group.size() <= 3) {
                this.deleteCards(tmp.group);
            } else {
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 3, this.DESCRIPTIONS[1], false, false, false, true);
            }

        }
    }

    public void update() {
        super.update();
        if (!this.cardsSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == 3) {
            this.deleteCards(AbstractDungeon.gridSelectScreen.selectedCards);
        }

    }

    public void deleteCards(ArrayList<AbstractCard> group) {
        this.cardsSelected = true;
        float displayCount = 0.0F;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for(AbstractCard card : group) {
            card.untip();
            card.unhover();
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, (float) Settings.WIDTH / 3.0F + displayCount, (float)Settings.HEIGHT / 2.0F));
            displayCount += (float)Settings.WIDTH / 6.0F;
            AbstractDungeon.player.masterDeck.removeCard(card);

            if(card.type == AbstractCard.CardType.ATTACK){
                AbstractCard a = new SarkazSee();
                UnlockTracker.markRelicAsSeen(a.cardID);
                tmp.addToBottom(a.makeCopy());
            }
            else if(card.type == AbstractCard.CardType.SKILL){
                AbstractCard s = new AStory();
                UnlockTracker.markRelicAsSeen(s.cardID);
                tmp.addToBottom(s.makeCopy());
            }
        }

        if(!tmp.isEmpty()) {
            AbstractDungeon.gridSelectScreen.openConfirmationGrid(tmp, this.DESCRIPTIONS[1]);
        }

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}



