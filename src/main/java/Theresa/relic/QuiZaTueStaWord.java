package Theresa.relic;

import Theresa.card.skill.CivilightEterna;
import Theresa.helper.CivilightHelper;
import Theresa.helper.StringHelper;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.TextCenteredEffect;

public class QuiZaTueStaWord extends CustomRelic {
    public static final String ID = "theresa:QuiZaTueStaWord";

    public static boolean canUpgrade(){
        return CivilightHelper.ownQuiZa || (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(QuiZaTueStaWord.ID)) || (CardCrawlGame.saveFile!=null && CardCrawlGame.saveFile.relics!=null && CardCrawlGame.saveFile.relics.contains(QuiZaTueStaWord.ID));
    }

    public QuiZaTueStaWord() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.MAGICAL);
        this.counter = -1;
    }

    @Override
    public void onEquip() {
        CivilightHelper.ownQuiZa = true;
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        AbstractCard c = new CivilightEterna();
        c.upgrade();
        UnlockTracker.markCardAsSeen(c.cardID);
        group.addToBottom(c.makeStatEquivalentCopy());
        AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, this.DESCRIPTIONS[1]);
        CardCrawlGame.sound.playA("RELIC_DROP_MAGICAL", MathUtils.random(-0.2F, -0.3F));
        StringBuilder text = new StringBuilder(this.DESCRIPTIONS[2]);
        for(int i =0;i<5;i++){
            AbstractCard tmp = AbstractDungeon.returnTrulyRandomCard().makeCopy();
            CivilightHelper.cardSaveList.add(tmp);
            text.append(tmp.name).append(" ");
        }
        TextCenteredEffect e = new TextCenteredEffect(text.toString());
        e.startingDuration = e.duration = 5F;
        AbstractDungeon.topLevelEffectsQueue.add(e);
    }

    @Override
    public void onUnequip() {
        boolean deQuiZa = true;
        for(AbstractRelic r : AbstractDungeon.player.relics) {
            if(r != this && r instanceof QuiZaTueStaWord) {
                deQuiZa = false;
                break;
            }
        }
        if(deQuiZa) {
            CivilightHelper.ownQuiZa = false;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}





