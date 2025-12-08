package Theresa.room;

import Theresa.action.RemainDeadAction;
import Theresa.action.SummonDeadmanAction;
import Theresa.dungeon.TheBabel;
import Theresa.modcore.TheresaMod;
import Theresa.monster.Deadman;
import Theresa.monster.Empty;
import Theresa.patch.BabelPatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.vfx.TextCenteredEffect;

import java.util.ArrayList;
import java.util.Collections;

public class DeadBossRoom extends MonsterRoomBoss {
    public static DeadBossRoom getCurrent() {

        if(AbstractDungeon.getCurrMapNode() != null ){
            TheresaMod.logSomething("=== Current Map Node : " + AbstractDungeon.getCurrRoom().getClass().getName() + " ===");
            if(AbstractDungeon.getCurrRoom() instanceof DeadBossRoom)
                return (DeadBossRoom) AbstractDungeon.getCurrRoom();
        }
        return null;
    }

    ArrayList<Integer> realIndexes = new ArrayList<>();

    public int remainingAmount;
    public int unsummonedAmount;
    int decreaseTurns;
    boolean changedBGM;
    boolean talked;
    boolean hasEnd;

    public boolean dieOne(){
        remainingAmount--;
        if(remainingAmount <= 4 && !changedBGM){
            changedBGM = true;
        }
        return remainingAmount == 0;
    }

    public boolean canTalk(){
        if(changedBGM && !talked){
            talked = true;
            return true;
        }
        return false;
    }

    public void endWithoutWinning(){
        if(!hasEnd){
            BabelPatch.NoKilledDead = true;
            hasEnd = true;
            remainingAmount = 0;
            unsummonedAmount = 0;
            for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                if(m instanceof Empty){
                    ((Empty) m).setCanDie();
                    m.die();
                }
                AbstractDungeon.actionManager.addToBottom(new InstantKillAction(m));
            }
            CardCrawlGame.screenShake.rumble(4.0F);
            (AbstractDungeon.getCurrRoom()).rewardAllowed = false;
            CardCrawlGame.music.silenceTempBgmInstantly();
            CardCrawlGame.music.silenceBGMInstantly();
            AbstractMonster.playBossStinger();
            CardCrawlGame.stopClock = true;
            AbstractDungeon.getCurrRoom().cannotLose = false;

            UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(TheBabel.ID);
            TextCenteredEffect e = new TextCenteredEffect(uiStrings.TEXT[0]);
            e.startingDuration = e.duration = 10F;
            AbstractDungeon.topLevelEffectsQueue.add(e);
        }
    }

    private static final int[] spawnIndexes = {2,3,1,4,0};

    public void spawnDeadmen(){
        int maxAlive = 1;
        if(GameActionManager.turn+1>=2)
            maxAlive = 2;
        if(GameActionManager.turn+1>=3)
            maxAlive = 3;
        if(GameActionManager.turn+1>=5)
            maxAlive = 4;
//        if(GameActionManager.turn+1>=9)
//            maxAlive = 5;
        int currentAlive = 0;
        ArrayList<Integer> alreadyIndexes = new ArrayList<>();
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
            if(m.currentHealth>0 && !m.isDeadOrEscaped() && m instanceof Deadman){
                currentAlive++;
                alreadyIndexes.add(((Deadman) m).placeIndex);
            }
            if(currentAlive >= maxAlive)
                break;
        }
        int decrease = 0;
        //最后4个一起出现
        if(maxAlive == 4 && unsummonedAmount == 4 && currentAlive == 0){

        }
        else if(maxAlive > (unsummonedAmount-4) + currentAlive){
            decrease = maxAlive - (unsummonedAmount-4) - currentAlive;
            maxAlive = (unsummonedAmount-4) + currentAlive;
        }
        if(unsummonedAmount==0){
            decreaseTurns++;
            decrease = decreaseTurns;
            if(decrease>3)
                decrease = 3;
        }

        float lastTimer = 0.6F;
        TheresaMod.logSomething("=== SPAWN DEADMAN START ===");
        ArrayList<Integer> indexes = new ArrayList<>();
        for(int i : spawnIndexes){
            if(!alreadyIndexes.contains(i))
                indexes.add(i);
        }
        for(int i=0; i<maxAlive-currentAlive; i++){
            TheresaMod.logSomething("=== SPAWN ONE ===");
            unsummonedAmount--;
            int realIndex = 12-unsummonedAmount;
            if(!realIndexes.isEmpty())
                realIndex = realIndexes.remove(0);
            AbstractDungeon.actionManager.addToBottom(new SummonDeadmanAction(indexes.get(i), lastTimer, realIndex,12-unsummonedAmount));
        }
        TheresaMod.logSomething("=== SPAWN DEADMAN END ===");
        AbstractDungeon.actionManager.addToBottom(new RemainDeadAction(decrease));
    }

    public DeadBossRoom() {
        super();
        remainingAmount = 13;
        unsummonedAmount = 12;
        decreaseTurns = 0;
        changedBGM = false;
        talked = false;
        hasEnd = false;
        for(int i = 1; i < 9; i++){
            realIndexes.add(i);
        }
        Random rand;
        if(CardCrawlGame.saveFile!=null){
            rand = new Random(CardCrawlGame.saveFile.seed);
        }
        else {
            rand = new Random(Settings.seed);
        }
        Collections.shuffle(realIndexes,rand.random);
    }

    @Override
    public void renderAboveTopPanel(SpriteBatch sb) {
        super.renderAboveTopPanel(sb);
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(TheBabel.ID);
        String msg = uiStrings.TEXT[1] + unsummonedAmount;
        FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, msg, Settings.WIDTH * 0.85F, Settings.HEIGHT * 0.82F, Color.WHITE.cpy());
    }
}
