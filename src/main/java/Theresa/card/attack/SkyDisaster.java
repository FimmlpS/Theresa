package Theresa.card.attack;

import Theresa.card.AbstractTheresaCard;
import Theresa.power.debuff.DyingPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SkyDisaster extends AbstractTheresaCard {
    public static final String ID = "theresa:SkyDisaster";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public SkyDisaster() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);
        baseDamage = damage = 4;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn),attackEffect));
        addToBot(new ApplyPowerAction(abstractMonster,abstractPlayer,new DyingPower(abstractMonster,damage),damage));
        if(!abstractMonster.hasPower(DyingPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(abstractMonster,abstractPlayer,new DyingPower(abstractMonster,damage),damage));
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(2);
        }
    }
}


