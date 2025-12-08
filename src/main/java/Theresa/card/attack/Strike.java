package Theresa.card.attack;

import Theresa.action.StartShaderAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.effect.FinaleEffect;
import Theresa.relic.QuiZaTueStaWord;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Strike extends AbstractTheresaCard {
    public static final String ID = "theresa:Strike";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Strike(){
        this(0);
    }

    public Strike(int upgrades) {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.BASIC,CardTarget.ENEMY);
        baseDamage = damage = 6;
        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
        this.timesUpgraded = upgrades;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn),attackEffect));
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            upgradeName();
            upgradeDamage(3);
        }
        else if(QuiZaTueStaWord.canUpgrade()){
            this.upgradeDamage(3);
            ++this.timesUpgraded;
            this.upgraded = true;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            this.initializeTitle();
        }
    }

    @Override
    public boolean canUpgrade() {
        return !upgraded || QuiZaTueStaWord.canUpgrade();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Strike(timesUpgraded);
    }
}
