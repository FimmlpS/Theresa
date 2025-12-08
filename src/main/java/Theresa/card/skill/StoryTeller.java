package Theresa.card.skill;

import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StoryTeller extends AbstractTheresaCard {
    public static final String ID = "theresa:StoryTeller";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public StoryTeller() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DrawCardAction(magicNumber));
    }

    @Override
    public void applyPowers() {
        int tmp = baseMagicNumber;
        for(AbstractCard c: AbstractDungeon.player.drawPile.group) {
            if(c.type == CardType.POWER)
                baseMagicNumber++;
            else if(upgraded && c.type == CardType.CURSE)
                baseMagicNumber++;
        }
        for(AbstractCard c: AbstractDungeon.player.hand.group) {
            if(c.type == CardType.POWER)
                baseMagicNumber++;
            else if(upgraded && c.type == CardType.CURSE)
                baseMagicNumber++;
        }
        for(AbstractCard c: AbstractDungeon.player.discardPile.group) {
            if(c.type == CardType.POWER)
                baseMagicNumber++;
            else if(upgraded && c.type == CardType.CURSE)
                baseMagicNumber++;
        }
        super.applyPowers();
        magicNumber = baseMagicNumber;
        baseMagicNumber = tmp;
        isMagicNumberModified = magicNumber != baseMagicNumber;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}







