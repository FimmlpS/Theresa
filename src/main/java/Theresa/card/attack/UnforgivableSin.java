package Theresa.card.attack;

import Theresa.action.TheresaAttackAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.helper.CivilightHelper;
import Theresa.modifier.EternalMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class UnforgivableSin extends AbstractTheresaCard {
    public static final String ID = "theresa:UnforgivableSin";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public UnforgivableSin() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        baseDamage = damage = 7;
        baseMagicNumber = magicNumber = 2;
        cardsToPreview = new SarkazSee();
        CardModifierManager.addModifier(this,new EternalMod());
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new TheresaAttackAction(true));
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, damage, damageTypeForTurn), skillEffect));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int tmp = baseDamage;
        baseDamage += CivilightHelper.cardSaveList.size() * baseMagicNumber;
        super.calculateCardDamage(mo);
        baseDamage = tmp;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void applyPowers() {
        int tmp = baseDamage;
        baseDamage += CivilightHelper.cardSaveList.size() * baseMagicNumber;
        super.applyPowers();
        baseDamage = tmp;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void onRemoveFromMasterDeck() {
        AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new SarkazSee(), Settings.WIDTH/2F, Settings.HEIGHT/2F));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}


