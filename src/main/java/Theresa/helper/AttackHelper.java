package Theresa.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class AttackHelper {

    public static AttackHelper Instance = new AttackHelper();

    public AttackHelper(){
        baseUrl.put(ForCharacter.Transformer,"TheresaResources/img/characters/transformer/enemy_1542_wdslm");
        actTime.put(ForCharacter.Transformer,2.67f);
        idleName.put(ForCharacter.Transformer,"2_Idle");
        attackName.put(ForCharacter.Transformer,"2_Attack");
        endName.put(ForCharacter.Transformer,"2_Idle");

        baseUrl.put(ForCharacter.Banshee,"TheresaResources/img/characters/banshee/enemy_2071_skzdny");
        actTime.put(ForCharacter.Banshee,3f);
        idleName.put(ForCharacter.Banshee,"Idle");
        attackName.put(ForCharacter.Banshee,"Attack");
        endName.put(ForCharacter.Banshee,"Idle");

        baseUrl.put(ForCharacter.Blooder,"TheresaResources/img/characters/blooder/enemy_1547_blord");
        actTime.put(ForCharacter.Blooder,2f);
        idleName.put(ForCharacter.Blooder,"A_Idle");
        attackName.put(ForCharacter.Blooder,"A_Attack");
        endName.put(ForCharacter.Blooder,"A_Idle");

        baseUrl.put(ForCharacter.War,"TheresaResources/img/characters/war/enemy_1555_lrking");
        actTime.put(ForCharacter.War,2f);
        idleName.put(ForCharacter.War,"Idle");
        attackName.put(ForCharacter.War,"Idle");
        endName.put(ForCharacter.War,"Idle");

        baseUrl.put(ForCharacter.Answer,"TheresaResources/img/characters/answer/enemy_2080_skzlwy");
        actTime.put(ForCharacter.Answer,1.6f);
        idleName.put(ForCharacter.Answer,"Idle");
        attackName.put(ForCharacter.Answer,"Attack");
        endName.put(ForCharacter.Answer,"Idle");

        baseUrl.put(ForCharacter.Swordsman,"TheresaResources/img/characters/swordsman/trap_761_skzthx");
        actTime.put(ForCharacter.Swordsman,4.17f);
        idleName.put(ForCharacter.Swordsman,"Skill_1_Loop");
        attackName.put(ForCharacter.Swordsman,"Skill_1_End");
        endName.put(ForCharacter.Swordsman,"Idle");

        baseUrl.put(ForCharacter.Strategist,"TheresaResources/img/characters/strategist/enemy_1528_manfri");
        actTime.put(ForCharacter.Strategist,1.84f);
        idleName.put(ForCharacter.Strategist,"Idle_2");
        attackName.put(ForCharacter.Strategist,"Attack_2");
        endName.put(ForCharacter.Strategist,"Idle_2");

        baseUrl.put(ForCharacter.Killer,"TheresaResources/img/characters/killer/enemy_1566_mpascl");
        actTime.put(ForCharacter.Killer,2.34f);
        idleName.put(ForCharacter.Killer,"Skill_Loop");
        attackName.put(ForCharacter.Killer,"Skill_End");
        endName.put(ForCharacter.Killer,"Skill_Loop");

        baseUrl.put(ForCharacter.Dale,"TheresaResources/img/characters/dale/char_1035_wisdel");
        actTime.put(ForCharacter.Dale,5f);
        idleName.put(ForCharacter.Dale,"Skill_3_Idle");
        attackName.put(ForCharacter.Dale,"Skill_3_Loop");
        endName.put(ForCharacter.Dale,"Skill_3_Idle");
    }

    public HashMap<ForCharacter, String> baseUrl = new HashMap<ForCharacter, String>();
    public HashMap<ForCharacter, Float> actTime =  new HashMap<ForCharacter, Float>();
    public HashMap<ForCharacter, String> idleName = new HashMap<ForCharacter, String>();
    public HashMap<ForCharacter, String> attackName = new HashMap<ForCharacter, String>();
    public HashMap<ForCharacter, String> endName = new HashMap<ForCharacter, String>();

    //至多4个动画同时行动
    public AttackInstance[] attacks = new AttackInstance[4];
    public ArrayList<WaitAttackInstance> attackQueue = new ArrayList<>();

    public void addCharacter(ForCharacter character,boolean allowRemain){
        attackQueue.add(new WaitAttackInstance(character,allowRemain));
    }

    public void clear(){
        Arrays.fill(attacks, null);
        attackQueue.clear();
    }

    //可能会饥饿
    public void update(){
        Iterator<WaitAttackInstance> var1 = attackQueue.iterator();
        while(var1.hasNext()){
            WaitAttackInstance wai = var1.next();
            if(addAttack(wai.character,wai.allowRemain)){
                var1.remove();
            }
        }

        for(AttackInstance ai:attacks){
            if(ai!=null)
                ai.update();
        }
    }

    public void render(SpriteBatch sb){
        for(AttackInstance ai:attacks){
            if(ai!=null)
                ai.render(sb);
        }
    }

    //动画添加逻辑 allowRemain=true 时会查找场上同角色动画，若其处于Appear或Act阶段则将actRemains++
    private boolean addAttack(ForCharacter attacker, boolean allowRemain){
        if(allowRemain){
            for (AttackInstance ai : attacks) {
                if (ai != null && ai.character == attacker) {
                    if (ai.currentPhase == InstancePhase.Appear || ai.currentPhase == InstancePhase.Act) {
                        ai.actRemains++;
                        return true;
                    }
                }
            }
        }
        for(int i =0;i<attacks.length;i++){
            AttackInstance ai = attacks[i];
            if(ai == null || ai.currentPhase==InstancePhase.End){
                AttackInstance newAI = new AttackInstance(attacker,i);
                attacks[i] = newAI;
                return true;
            }
        }
        return false;
    }

    public static class WaitAttackInstance{
        public ForCharacter character;
        public boolean allowRemain;

        public WaitAttackInstance(ForCharacter character, boolean allowRemain){
            this.character = character;
            this.allowRemain = allowRemain;
        }
    }

    public static class AttackInstance {
        public static final float START_PADDING_X = 180F* Settings.scale;
        public static final float PADDING_X = 150F* Settings.scale;
        public static final float FINAL_ALPHA = 0.75F;

        public int index;
        public ForCharacter character;
        public InstancePhase currentPhase = InstancePhase.Default;
        public float appearTime = 0.4F;
        public float disappearTime = 0.4F;
        public float actTime;
        public float phaseTimer;
        public int actRemains;
        public Color renderColor = Color.WHITE.cpy();
        public float renderAlpha = 0F;
        public float drawX;
        public float drawY;
        public boolean flipX = false;
        public boolean flipY = false;
        private String idleName;
        private String attackName;
        private String endName;

        protected TextureAtlas atlas;
        protected Skeleton skeleton;
        public AnimationState state;
        protected AnimationStateData stateData;

        public AttackInstance(ForCharacter character, int index) {
            this.index = index;
            this.character = character;
            this.currentPhase = InstancePhase.Appear;
            loadAnimation(Instance.baseUrl.get(character)+".atlas",Instance.baseUrl.get(character)+".json",1.75F);
            this.actTime = Instance.actTime.get(character);
            this.phaseTimer = 0F;
            this.actRemains = 1;
            idleName = Instance.idleName.get(character);
            attackName = Instance.attackName.get(character);
            endName = Instance.endName.get(character);
            state.setAnimation(0,idleName,true);
        }

        public void loadAnimation(String atlasUrl, String skeletonUrl, float scale){
            this.atlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
            SkeletonJson json = new SkeletonJson(this.atlas);
            json.setScale(Settings.renderScale / scale);
            SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonUrl));
            this.skeleton = new Skeleton(skeletonData);
            this.skeleton.setColor(Color.WHITE);
            this.stateData = new AnimationStateData(skeletonData);
            this.state = new AnimationState(this.stateData);
        }

        //位置计算
        private void updatePosition(){
            int leftAmt = 0;
            float startX = Settings.WIDTH * 0.2F;
            float startY = AbstractDungeon.floorY;
            if(AbstractDungeon.player != null){
                startX = AbstractDungeon.player.drawX;
                if(AbstractDungeon.player.drawX<0.4F*Settings.WIDTH){
                    leftAmt = 0;
                }
                else if(AbstractDungeon.player.drawX>0.6F*Settings.WIDTH){
                    leftAmt = 4;
                }
                else {
                    leftAmt = 2;
                }
                startY = AbstractDungeon.player.drawY + 2F * Settings.scale;
            }
            if(leftAmt == 0){
                drawX = startX + START_PADDING_X + index*PADDING_X;
                flipX = false;
            }
            else if(leftAmt == 4){
                drawX = startX - START_PADDING_X - index*PADDING_X;
                flipX = true;
            }
            else {
                if(index==0){
                    drawX = startX - START_PADDING_X;
                    flipX = true;
                }
                else if(index==1){
                    drawX = startX + START_PADDING_X;
                    flipX = false;
                }
                else if(index==2){
                    drawX = startX - START_PADDING_X - PADDING_X;
                    flipX = true;
                }
                else if(index==3){
                    drawX = startX + START_PADDING_X + PADDING_X;
                    flipX = false;
                }
            }
            drawY = startY;
        }

        public void update(){
            updatePosition();

            phaseTimer += Gdx.graphics.getDeltaTime();
            if(currentPhase==InstancePhase.Appear){
                renderAlpha = mix(0F,FINAL_ALPHA,phaseTimer/appearTime);
                if(phaseTimer >= appearTime){
                    renderAlpha = FINAL_ALPHA;
                    phaseTimer = 0F;
                    currentPhase = InstancePhase.Act;
                    attackOnce();
                }
            }
            else if(currentPhase==InstancePhase.Act){
                renderAlpha = FINAL_ALPHA;
                if(phaseTimer >= actTime){
                    phaseTimer = 0F;
                    actRemains--;
                    if(actRemains<=0){
                        currentPhase = InstancePhase.Disappear;
                    }
                    else {
                        attackOnce();
                    }
                }
            }
            else if(currentPhase==InstancePhase.Disappear){
                renderAlpha = mix(FINAL_ALPHA,0F,phaseTimer/disappearTime);
                if(phaseTimer >= disappearTime){
                    renderAlpha = 0F;
                    phaseTimer = 0F;
                    currentPhase = InstancePhase.End;
                }
            }
            renderColor.a = renderAlpha;
        }

        private float mix(float start, float end,float degree){
            return start + (end - start) * degree;
        }

        private void attackOnce(){
            state.setAnimation(0,attackName,false);
            if(actRemains>1)
                state.addAnimation(0,idleName,true,0F);
            else
                state.addAnimation(0,endName,true,0F);
        }

        public void render(SpriteBatch sb){
            if(atlas != null){
                state.update(Gdx.graphics.getDeltaTime());
                state.apply(skeleton);
                skeleton.updateWorldTransform();
                skeleton.setPosition(drawX,drawY);
                skeleton.setColor(renderColor);
                skeleton.setFlip(flipX, flipY);
                sb.end();
                CardCrawlGame.psb.begin();
                AbstractCreature.sr.draw(CardCrawlGame.psb,skeleton);
                CardCrawlGame.psb.end();
                sb.begin();
            }
        }
    }

    public enum ForCharacter {
        Transformer,
        Banshee,
        Blooder,
        War,
        Answer,
        Swordsman,
        Strategist,
        Killer,
        Dale
    }

    public enum InstancePhase{
        Default,
        Appear,
        Act,
        Disappear,
        End
    }
}
