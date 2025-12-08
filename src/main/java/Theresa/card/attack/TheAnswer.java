package Theresa.card.attack;

import Theresa.action.AnswerAction;
import Theresa.action.SpecialAnimationAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.helper.AttackHelper;
import Theresa.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TheAnswer extends AbstractTheresaCard {
    public static final String ID = "theresa:TheAnswer";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public TheAnswer() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.RARE,CardTarget.ENEMY);
        baseDamage = damage = 15;
        this.tags.add(OtherEnum.Theresa_Darkness);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SpecialAnimationAction(AttackHelper.ForCharacter.Answer));
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new AnswerAction(abstractMonster,this.upgraded));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(-5);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            this.exhaust = true;
        }
    }
}


