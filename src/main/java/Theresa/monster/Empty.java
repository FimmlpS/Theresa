package Theresa.monster;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Empty extends AbstractMonster {
    public static final String ID = "theresa:Empty";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);

    public Empty() {
        super(monsterStrings.NAME, ID,1,0,0,1,1,null,1500,0);
        type = EnemyType.NORMAL;
        setMove((byte) 0,Intent.UNKNOWN);
        this.halfDead = true;
    }

    boolean canDie =false;

    public void setCanDie(){
        canDie = true;
    }

    @Override
    public void damage(DamageInfo info) {
        //no damage
    }

    @Override
    protected void updateHealthBar() {
        if(!canDie){
            currentHealth = maxHealth = 1;
        }
        super.updateHealthBar();
    }

    @Override
    public void render(SpriteBatch sb) {
        //no render
    }

    @Override
    public void takeTurn() {

    }

    @Override
    public void changeState(String stateName) {
        super.changeState(stateName);
    }

    @Override
    protected void getMove(int i) {
        setMove((byte) 0,Intent.UNKNOWN);
    }

    @Override
    public void die() {
        if(!canDie)
            return;
        die(false);
    }
}
