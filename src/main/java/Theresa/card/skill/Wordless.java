package Theresa.card.skill;

import Theresa.card.AbstractTheresaCard;
import Theresa.patch.OtherEnum;
import Theresa.power.buff.WordlessPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Wordless extends AbstractTheresaCard {
    public static final String ID = "theresa:Wordless";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Wordless() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        tags.add(OtherEnum.Theresa_Darkness);
        this.baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        //addToBot(new GainEnergyAction(1));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer, new WordlessPower(abstractPlayer,1),1));
        addToBot(new DrawCardAction(magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}






