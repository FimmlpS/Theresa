package Theresa.card.skill;

import Theresa.action.MakeTempCardInDustAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.OtherEnum;
import Theresa.patch.SilkPatch;
import Theresa.silk.NormalSilk;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SadDust extends AbstractTheresaCard {
    public static final String ID = "theresa:SadDust";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public SadDust() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
        tags.add(OtherEnum.Theresa_Darkness);
        AbstractCard c = new ADust();
        CardModifierManager.addModifier(c,new ExhaustMod());
        cardsToPreview = c;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if(cardsToPreview!=null)
            addToBot(new MakeTempCardInDustAction(cardsToPreview,magicNumber).setOverMake());
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            selfRetain = true;
        }
    }
}






