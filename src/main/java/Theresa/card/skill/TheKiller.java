package Theresa.card.skill;

import Theresa.action.SpecialAnimationAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.helper.AttackHelper;
import Theresa.power.debuff.SlowBurnPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SlowPower;

public class TheKiller extends AbstractTheresaCard {
    public static final String ID = "theresa:TheKiller";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public TheKiller() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SpecialAnimationAction(AttackHelper.ForCharacter.Killer,false));
        for(AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            addToBot(new ApplyPowerAction(mo,abstractPlayer,new SlowPower(mo,0)));
            addToBot(new ApplyPowerAction(mo,abstractPlayer,new SlowBurnPower(mo,magicNumber),magicNumber));
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            exhaust = false;
        }
    }
}






