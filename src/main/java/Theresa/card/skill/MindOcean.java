package Theresa.card.skill;

import Theresa.card.AbstractTheresaCard;
import Theresa.patch.OtherEnum;
import Theresa.power.buff.DiscordPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MindOcean extends AbstractTheresaCard {
    public static final String ID = "theresa:MindOcean";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public MindOcean() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.RARE,CardTarget.NONE);
        baseMagicNumber = magicNumber = 12;
        //CardModifierManager.addModifier(this,new EternalMod());
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        //addToBot(new MindOceanAction(magicNumber));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new DiscordPower(abstractPlayer,magicNumber),magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(4);
        }
    }
}




