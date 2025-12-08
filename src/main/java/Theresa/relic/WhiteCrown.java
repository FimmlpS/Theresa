package Theresa.relic;

import Theresa.card.attack.TheBlooder;
import Theresa.helper.StringHelper;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class WhiteCrown extends CustomRelic {
    public static final String ID = "theresa:WhiteCrown";

    public WhiteCrown() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.MAGICAL);
        this.counter = -1;
    }

    @Override
    public void onEquip() {
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        AbstractCard blooder = new TheBlooder();
        blooder.upgrade();
        UnlockTracker.markCardAsSeen(blooder.cardID);
        group.addToBottom(blooder.makeStatEquivalentCopy());
        AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, this.DESCRIPTIONS[1]);
        CardCrawlGame.sound.playA("BLOOD_SWISH", MathUtils.random(-0.2F, -0.3F));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}


