package Theresa.card.skill;

import Theresa.card.AbstractTheresaCard;
import Theresa.power.buff.HopePower;
import Theresa.screen.TypeSelectScreen;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Babel extends AbstractTheresaCard {
    public static final String ID = "theresa:Babel";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Babel() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.ALL);
        baseMagicNumber = magicNumber = 1;
        baseBlock = block = 8;
        if(TypeSelectScreen.getType()==2){
            baseMagicNumber = magicNumber = 3;
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, block));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new HopePower(abstractPlayer,magicNumber),magicNumber));
        for(AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if(!mo.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(mo,abstractPlayer,new HopePower(mo,magicNumber),magicNumber));
            }
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }
}




