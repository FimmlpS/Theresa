package Theresa.card.attack;

import Theresa.action.SpecialAnimationAction;
import Theresa.action.WarAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.card.status.TheGift;
import Theresa.helper.AttackHelper;
import Theresa.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TheBlooder extends AbstractTheresaCard {
    public static final String ID = "theresa:TheBlooder";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public TheBlooder() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.RARE,CardTarget.ENEMY);
        baseDamage = damage = 10;
        this.tags.add(OtherEnum.Theresa_Darkness);
        cardsToPreview = new TheGift();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SpecialAnimationAction(AttackHelper.ForCharacter.Blooder));
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn), attackEffect));
        if(cardsToPreview != null) {
            addToBot(new MakeTempCardInDrawPileAction(cardsToPreview,1,true,true));
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(2);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            if(cardsToPreview != null) {
                cardsToPreview.upgrade();
            }
        }
    }
}


