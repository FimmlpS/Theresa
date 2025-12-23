package Theresa.silk;

import Theresa.action.SilkFlashAction;
import Theresa.helper.ImageHelper;
import Theresa.helper.TheresaHelper;
import Theresa.power.buff.MindPower;
import Theresa.power.buff.SilkPower;
import Theresa.relic.IreShyWord;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MindSilk extends AbstractSilk {
    //2025/12/22 更新：意志总和提供的上限最多为6
    public static final int ABSOLUTELY_TOP_LIMIT = 6;
    public static final String ID = "theresa:MindSilk";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    int totalAmount;

    public static int paddingRemains = 3;
    public static void triggerMindSilk() {
        if(paddingRemains>0)
            paddingRemains--;
        if(AbstractDungeon.player != null && !AbstractDungeon.player.hasPower(MindPower.POWER_ID)){
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new MindPower(AbstractDungeon.player)));
        }
    }
    public static void resetMindSilk() {
        paddingRemains = 3;
    }

    public boolean atOnce = false;

    public MindSilk() {
       this(false);
    }

    public MindSilk(boolean atOnce){
        super(ID);
        this.name = powerStrings.NAME;
        this.baseAmount = 1;
        this.amount = 1;
        this.totalAmount = ABSOLUTELY_TOP_LIMIT;
        this.img = ImageHelper.imgD;
        this.updateDescription();
    }

    public static int getTotalAmount(){
        int total = ABSOLUTELY_TOP_LIMIT;
        if(AbstractDungeon.player!=null){
            AbstractPower sp = AbstractDungeon.player.getPower(SilkPower.POWER_ID);
            if(sp!=null){
                total += sp.amount;
            }
        }
        return total;
    }

    @Override
    public void applyPowers() {
        if(AbstractDungeon.player==null)
            return;
        AbstractPower sp = AbstractDungeon.player.getPower(SilkPower.POWER_ID);
        if(sp!=null){
            this.amount = baseAmount + sp.amount;
            this.totalAmount = ABSOLUTELY_TOP_LIMIT + sp.amount;
        }
        else{
            this.amount = baseAmount;
            this.totalAmount = ABSOLUTELY_TOP_LIMIT;
        }
        updateDescription();
    }

    @Override
    public void onCopied() {
        if(paddingRemains<=0)
            return;
        if(atOnce){
            atOnce = false;
            AbstractDungeon.actionManager.addToTop(new SilkFlashAction(this));
        }
        else
            AbstractDungeon.actionManager.addToBottom(new SilkFlashAction(this));
        triggeredOnce();
    }

    @Override
    public boolean canReplace(AbstractSilk silkToReplace) {
        if(silkToReplace.silkID==null)
            return false;
        if(AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(IreShyWord.ID))
            return false;
        return silkToReplace.silkID.equals(NormalSilk.ID) || silkToReplace.silkID.equals(TearSilk.ID);
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1] + this.totalAmount + powerStrings.DESCRIPTIONS[2];
    }

    @Override
    public AbstractSilk makeCopy() {
        return new MindSilk();
    }
}
