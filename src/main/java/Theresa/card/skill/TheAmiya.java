package Theresa.card.skill;

import Theresa.card.AbstractTheresaCard;
import Theresa.patch.SilkPatch;
import Theresa.power.buff.HopePower;
import Theresa.silk.NormalSilk;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

public class TheAmiya extends AbstractTheresaCard {
    public static final String ID = "theresa:TheAmiya";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public TheAmiya() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        this.baseMagicNumber = magicNumber = 2;
        this.isInnate = true;
        this.exhaust = true;
        shouldLocked = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new BlurPower(abstractPlayer,1),1));
    }

    @Override
    public void triggerWhenBecomeDust() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new HopePower(AbstractDungeon.player,magicNumber),magicNumber));
    }

    @Override
    public void triggerWhenNoLongerDust() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new HopePower(AbstractDungeon.player,magicNumber),magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            SilkPatch.setSilkWithoutTrigger(this,new NormalSilk());
        }
    }
}




