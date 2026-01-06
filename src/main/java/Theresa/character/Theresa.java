package Theresa.character;

import Theresa.card.skill.ADust;
import Theresa.helper.RegisterHelper;
import Theresa.modcore.TheresaMod;
import Theresa.orb.TheresaEnergyOrb;
import Theresa.patch.ClassEnum;
import Theresa.patch.ColorEnum;
import Theresa.patch.DustPatch;
import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.G3DJAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;
import java.util.List;

public class Theresa extends CustomPlayer {
    public static final String ID = "theresa:Theresa";

    private static final CharacterStrings characterStrings;

    public static final String[] orbTextures = new String[]{
            "TheresaResources/img/orbs/2.png",
            "TheresaResources/img/orbs/3.png",
            "TheresaResources/img/orbs/4.png",
            "TheresaResources/img/orbs/5.png",
            "TheresaResources/img/orbs/6.png",
            "TheresaResources/img/orbs/7.png",
            "TheresaResources/img/orbs/1.png",
            "TheresaResources/img/orbs/2.png",
            "TheresaResources/img/orbs/3.png",
            "TheresaResources/img/orbs/4.png",
            "TheresaResources/img/orbs/5.png",
            "TheresaResources/img/orbs/6.png",
            "TheresaResources/img/orbs/7.png",
    };

    public static final String[] orbTexturesTheresa = new String[]{
            "TheresaResources/img/orbs/m1.png",
            "TheresaResources/img/orbs/m2.png",
            "TheresaResources/img/orbs/m3.png",
            "TheresaResources/img/orbs/m4.png",
            "TheresaResources/img/orbs/m5.png",
            "TheresaResources/img/orbs/mBASE.png",
            "TheresaResources/img/orbs/m1.png",
            "TheresaResources/img/orbs/m2.png",
            "TheresaResources/img/orbs/m3.png",
            "TheresaResources/img/orbs/m4.png",
            "TheresaResources/img/orbs/m5.png",
    };

    public static final String VFX = "TheresaResources/img/orbs/vfx.png";
    public static final String IMG_SHOULDER = "TheresaResources/img/charSelect/char_shoulder.png";
    public static final float[] LAYER_SPEED = new float[] {
            0.2F,
            0.4F,
            0.9F,
            0.95F,
            1.7F,
            2.45F
    };

    public static final float[] LAYER_SCALE = new float[] {
            0.9F,
            0.55F,
            0.55F,
            0.9F,
            0.9F,
            0.9F
    };

    public static final float[] LAYER_SPEEDTheresa = new float[] {
            -32.0F,
            0F,
            -16.0F,
            -32F,
            0F,
            -32.0F,
            0F,
            -16.0F,
            -32F,
            0F,
    };

    private static final int ENERGY_PER_TURN =3;
    private static final int STARTING_HP = 48;
    private static final int MAX_HP = 48;
    private static final int STARTING_GOLD = 99;
    private static final int HAND_SIZE = 0;
    private static final int ASCENSION_MAX_HP_LOSS = 4;

    private String idleName = "C1_Idle";
    private String attackName = "C1_Skill_3";
    private String skillName = "C1_Skill_4";

    public Theresa(String name){
        super(name, ClassEnum.Theresa_CLASS,new TheresaEnergyOrb(orbTextures,VFX,LAYER_SPEED,LAYER_SCALE),new G3DJAnimation(null, null));

        this.dialogX = this.drawX+ 0.0F* Settings.scale;
        this.dialogY = this.drawY+ 240.0F*Settings.scale;
        this.initializeClass(null,IMG_SHOULDER,IMG_SHOULDER,null,this.getLoadout(),0,-5.0F,260.0F,240.0F,new EnergyManager(ENERGY_PER_TURN));
        this.refreshSkin();
    }

    public void refreshSkin(){
        int type = 0;
        String pathName = "TheresaResources/img/characters/";
        String fileName = "";
        if(type==0){
            fileName = "default/enemy_1554_lrtsia";
        }
        this.loadAnimation(pathName+fileName+".atlas",pathName+fileName+".json",1.75F);

        if(type==0){
            idleName = "C1_Idle";
            attackName = "C1_Skill_3";
            skillName = "C1_Skill_4";
        }
        this.state.setAnimation(0,idleName,true);
    }

    public static void onAttack(boolean skilled){
        AbstractPlayer pl = AbstractDungeon.player;
        if(pl instanceof Theresa){
            ((Theresa)pl).attackAnimation(skilled);
        }
    }

    public void attackAnimation(boolean skilled){
        if(!skilled){
            this.state.setAnimation(0,attackName,false);
        }
        else{
            this.state.setAnimation(0,skillName,false);
        }
        this.state.addAnimation(0,idleName,true,0F);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        return RegisterHelper.startDeck;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        return RegisterHelper.startRelic;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(characterStrings.NAMES[0],characterStrings.TEXT[0], STARTING_HP,MAX_HP,HAND_SIZE,STARTING_GOLD,5,this,getStartingRelics(),getStartingDeck(),false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return ColorEnum.Theresa_COLOR;
    }

    @Override
    public Color getCardRenderColor() {
        return TheresaMod.tColor.cpy();
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new ADust();
    }

    @Override
    public Color getCardTrailColor() {
        return Color.SKY.cpy();
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return ASCENSION_MAX_HP_LOSS;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
    }

    @Override
    public void updateOrb(int energyCount) {
        this.energyOrb.updateOrb(energyCount);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return ID;
    }

    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Theresa(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[2];
    }

    @Override
    public Color getSlashAttackColor() {
        return TheresaMod.tColor.cpy();
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList();
        panels.add(new CutscenePanel("TheresaResources/img/charSelect/01.png","HEAL_1"));
        panels.add(new CutscenePanel("TheresaResources/img/charSelect/02.png","HEAL_3"));
        return panels;
    }

    @Override
    public Texture getCutsceneBg() {
        return super.getCutsceneBg();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.FIRE,AbstractGameAction.AttackEffect.FIRE,AbstractGameAction.AttackEffect.FIRE,AbstractGameAction.AttackEffect.FIRE,AbstractGameAction.AttackEffect.FIRE,AbstractGameAction.AttackEffect.FIRE};
    }

    @Override
    public String getVampireText() {
        return characterStrings.TEXT[1];
    }

    @Override
    public void playDeathAnimation() {
        //this.state.setAnimation(0,"C3_Revive",false);
    }

    @Override
    public void damage(DamageInfo info) {
        int thisHealth = currentHealth;
        super.damage(info);
        int trueDamage = currentHealth - thisHealth;
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && trueDamage > 0 && this.isDead) {
            this.playDeathAnimation();
        }
    }

    //展示微尘数量


    @Override
    public void renderPowerTips(SpriteBatch sb) {
        ArrayList<PowerTip> tips = new ArrayList<>();
        if(DustPatch.dustManager.dustUpLimit>0){
            tips.add(new PowerTip(characterStrings.TEXT[3],characterStrings.TEXT[4]+DustPatch.dustManager.dustUpLimit+characterStrings.TEXT[5]));
        }

        if (!this.stance.ID.equals("Neutral")) {
            tips.add(new PowerTip(this.stance.name, this.stance.description));
        }

        for(AbstractPower p : this.powers) {
            if (p.region48 != null) {
                tips.add(new PowerTip(p.name, p.description, p.region48));
            } else {
                tips.add(new PowerTip(p.name, p.description, p.img));
            }
        }

        if (!tips.isEmpty()) {
            if (this.hb.cX + this.hb.width / 2.0F < TIP_X_THRESHOLD) {
                TipHelper.queuePowerTips(this.hb.cX + this.hb.width / 2.0F + TIP_OFFSET_R_X, this.hb.cY + TipHelper.calculateAdditionalOffset(tips, this.hb.cY), tips);
            } else {
                TipHelper.queuePowerTips(this.hb.cX - this.hb.width / 2.0F + TIP_OFFSET_L_X, this.hb.cY + TipHelper.calculateAdditionalOffset(tips, this.hb.cY), tips);
            }
        }
    }

    static {
        characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    }
}
